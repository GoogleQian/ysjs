package com.xiaohe.ysjspt.modules.devconsumeinfo.jpa;

import com.xiaohe.ysjspt.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public interface ConsumeInfoRepository extends JpaRepository<Order, Integer> {

    /**
     * 最近7天消费金额
     *
     * @param devId 设备编号
     * @return
     */
    @Query(value = "SELECT count(id) usageCount,dev_id,water_temp,sell_status,SUM( water_amount )  waterAmount,SUM(money_amount )  moneyAmount FROM `tb_orders` WHERE dev_id=?1 and sell_status= 1 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(pay_notify_time) GROUP BY dev_id", nativeQuery = true)
    List<Object[]> findConsumeByWeek(String devId);

    /**
     * 当天消费金额
     *
     * @param devId 设备编号
     * @return
     */
    @Query(value = "SELECT count(id) usageCount,dev_id,water_temp,sell_status,SUM( water_amount )  waterAmount,SUM(money_amount )  moneyAmount FROM `tb_orders` WHERE dev_id=?1 and sell_status= 1 and to_days(pay_notify_time) = to_days(now()) GROUP BY dev_id", nativeQuery = true)
    List<Object[]> findConsumeByDay(String devId);

    /**
     * 本月消费金额
     *
     * @param devId 设备编号
     * @return
     */
    @Query(value = "SELECT count(id) usageCount,dev_id,water_temp,sell_status ,SUM( water_amount )  waterAmount,SUM(money_amount )  moneyAmount FROM `tb_orders` WHERE dev_id=?1 and sell_status= 1 and DATE_FORMAT( pay_notify_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) GROUP BY dev_id", nativeQuery = true)
    List<Object[]> findConsumeByCurrentMonth(String devId);

    /**
     * 上个月消费金额
     *
     * @param devId 设备编号
     * @return List<Object   [   ]>
     */
    @Query(value = "SELECT count(id) usageCount,dev_id,water_temp,sell_status,SUM( water_amount )  waterAmount,SUM(money_amount )  moneyAmount FROM `tb_orders` WHERE dev_id=?1 and sell_status= 1 and PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( pay_notify_time, '%Y%m' ) ) =1 GROUP BY dev_id", nativeQuery = true)
    List<Object[]> findConsumeByLastMonth(String devId);

    /**
     * 自定义时间段消费金额
     *
     * @param devId 设备编号
     * @return List<Object   [   ]>
     */
    @Query(value = "SELECT count(id) usageCount,dev_id,water_temp,sell_status,SUM( water_amount )  waterAmount,SUM(money_amount )  moneyAmount FROM `tb_orders` WHERE dev_id=?1 and sell_status= 1 and  pay_notify_time >= ?2 and pay_notify_time <= ?3 GROUP BY dev_id", nativeQuery = true)
    List<Object[]> findConsumeByTime(String devId, String startTime, String endTime);

    /**
     * 最近7天消费杯水量情况
     *
     * @param devId  设备编号
     * @param devId2 设备编号
     * @return List<Object   [   ]>
     */
    @Query(value = "select t.*,xx.* from(SELECT d.device_id,w.name,w.water_amount FROM tb_device d LEFT JOIN tb_schema_detail w ON d.schema_id = w.schema_id"
            + " WHERE d.device_id = ?1 GROUP BY w.NAME)  t left join"
            + " (SELECT count(o.id) usageCount,o.dev_id,o.sell_status,o.water_amount water_amount2 FROM tb_orders o WHERE dev_id =?2 and o.sell_status=1"
            + " AND DATE_SUB( CURDATE( ), INTERVAL 7 DAY ) <= date( pay_notify_time )"
            + " GROUP BY dev_id,water_amount2) xx on t.device_id =xx.dev_id and xx.water_amount2=t.water_amount", nativeQuery = true)
    List<Object[]> findWaterAmountByWeek(String devId, String devId2);

    /**
     * 当天消费杯水量情况
     *
     * @param devId  设备编号
     * @param devId2 设备编号
     * @return List<Object   [   ]>
     */
    @Query(value = "select t.*,xx.* from(SELECT d.device_id,w.name,w.water_amount FROM tb_device d LEFT JOIN tb_schema_detail w ON d.schema_id = w.schema_id" +
            " WHERE d.device_id = ?1 GROUP BY w.NAME)  t left join" +
            " (SELECT count(o.id) usageCount,o.dev_id,o.sell_status,o.water_amount water_amount2 FROM tb_orders o WHERE dev_id =?2 and o.sell_status=1" +
            " AND to_days(pay_notify_time) = to_days(now())" +
            " GROUP BY dev_id,water_amount2) xx on t.device_id =xx.dev_id and xx.water_amount2=t.water_amount", nativeQuery = true)
    List<Object[]> findWaterAmountByDay(String devId, String devId2);

    /**
     * 本月消费杯水量情况
     *
     * @param devId  设备编号
     * @param devId2 设备编号
     * @return List<Object   [   ]>
     */
    @Query(value = "select t.*,xx.* from(SELECT d.device_id,w.name,w.water_amount FROM tb_device d LEFT JOIN tb_schema_detail w ON d.schema_id = w.schema_id" +
            " WHERE d.device_id = ?1 GROUP BY w.NAME)  t left join" +
            " (SELECT count(o.id) usageCount,o.dev_id,o.sell_status,o.water_amount water_amount2 FROM tb_orders o WHERE dev_id =?2 and o.sell_status=1" +
            " AND DATE_FORMAT( pay_notify_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )" +
            " GROUP BY dev_id,water_amount2) xx on t.device_id =xx.dev_id and xx.water_amount2=t.water_amount", nativeQuery = true)
    List<Object[]> findWaterAmountByCurrentMonth(String devId, String devId2);

    /**
     * 上个月消费杯水量情况
     *
     * @param devId  设备编号
     * @param devId2 设备编号
     * @return List<Object   [   ]>
     */
    @Query(value = "select t.*,xx.* from(SELECT d.device_id,w.name,w.water_amount FROM tb_device d LEFT JOIN tb_schema_detail w ON d.schema_id = w.schema_id" +
            " WHERE d.device_id = ?1 GROUP BY w.NAME)  t left join" +
            " (SELECT count(o.id) usageCount,o.dev_id,o.sell_status,o.water_amount water_amount2 FROM tb_orders o WHERE dev_id =?2 and o.sell_status=1" +
            " AND PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( pay_notify_time, '%Y%m' ) ) =1" +
            " GROUP BY dev_id,water_amount2) xx on t.device_id =xx.dev_id and xx.water_amount2=t.water_amount", nativeQuery = true)
    List<Object[]> findWaterAmountByLastMonth(String devId, String devId2);

    /**
     * 自定义时间段消费杯水量情况
     *
     * @param devId     设备编号
     * @param devId2    设备编号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List<Object   [   ]>
     */
    @Query(value = "select t.*,xx.* from(SELECT d.device_id,w.name,w.water_amount FROM tb_device d LEFT JOIN tb_schema_detail w ON d.schema_id = w.schema_id" +
            " WHERE d.device_id = ?1 GROUP BY w.NAME)  t left join" +
            " (SELECT count(o.id) usageCount,o.dev_id,o.sell_status,o.water_amount water_amount2 FROM tb_orders o WHERE dev_id =?2 and o.sell_status=1" +
            " AND pay_notify_time >= ?3 and pay_notify_time <= ?4" +
            " GROUP BY dev_id,water_amount2) xx on t.device_id =xx.dev_id and xx.water_amount2=t.water_amount", nativeQuery = true)
    List<Object[]> findWaterAmountByTime(String devId, String devId2, String startTime, String endTime);

    /**
     * 当天消费排行
     * @return
     */
    @Query(value = "SELECT t.device_id, ss.dev_id, ss.usageCount, ss.waterAmount, ss.moneyAmount FROM tb_device t LEFT JOIN (SELECT o.dev_id, COUNT(o.id) AS usageCount, SUM(o.water_amount) AS waterAmount, SUM(o.money_amount) AS moneyAmount FROM tb_orders o  " +
            "WHERE o.sell_status = 1  and to_days(pay_notify_time) = to_days(now()) GROUP BY o.dev_id) ss ON t.device_id = ss.dev_id   WHERE t.user_id=?1 GROUP BY t.device_id ORDER BY ss.dev_id desc", nativeQuery = true)
    List<Object[]> findRankByDay(Integer userId);

    /**
     * 最近7天排行
     * @return
     */
    @Query(value = "SELECT t.device_id, ss.dev_id, ss.usageCount, ss.waterAmount, ss.moneyAmount FROM tb_device t LEFT JOIN (SELECT o.dev_id, COUNT(o.id) AS usageCount, SUM(o.water_amount) AS waterAmount, SUM(o.money_amount) AS moneyAmount FROM tb_orders o " +
            " where o.sell_status= 1 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(pay_notify_time) GROUP BY o.dev_id) ss ON t.device_id = ss.dev_id WHERE t.user_id= ?1 GROUP BY t.device_id ORDER BY ss.dev_id desc", nativeQuery = true)
    List<Object[]> findRankByWeek(Integer userId);
    /**
     * 本月消费排行
     * @return
     */
    @Query(value = "SELECT t.device_id, ss.dev_id, ss.usageCount, ss.waterAmount, ss.moneyAmount FROM tb_device t LEFT JOIN (SELECT o.dev_id, COUNT(o.id) AS usageCount, SUM(o.water_amount) AS waterAmount, SUM(o.money_amount) AS moneyAmount FROM tb_orders o" +
            " where o.sell_status= 1 and DATE_FORMAT( pay_notify_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) GROUP BY o.dev_id) ss ON t.device_id = ss.dev_id   WHERE t.user_id= ?1 GROUP BY t.device_id ORDER BY ss.dev_id desc", nativeQuery = true)
    List<Object[]> findRankByCurrentMonth(Integer userId);

    /**
     * 上个月消费排行
     * @return List<Object[]>
     */
    @Query(value = "SELECT t.device_id, ss.dev_id, ss.usageCount, ss.waterAmount, ss.moneyAmount FROM tb_device t LEFT JOIN (SELECT o.dev_id, COUNT(o.id) AS usageCount, SUM(o.water_amount) AS waterAmount, SUM(o.money_amount) AS moneyAmount FROM tb_orders o " +
            "where o.sell_status= 1 and PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( pay_notify_time, '%Y%m' ) ) =1 GROUP BY o.dev_id) ss ON t.device_id = ss.dev_id  WHERE t.user_id=  ?1 GROUP BY t.device_id ORDER BY ss.dev_id desc", nativeQuery = true)
    List<Object[]> findRankByLastMonth(Integer userId);

    /**
     * 自定义时间段消费排行
     * @return List<Object[]>
     */
    @Query(value = "SELECT t.device_id, ss.dev_id, ss.usageCount, ss.waterAmount, ss.moneyAmount FROM tb_device t LEFT JOIN (SELECT o.dev_id, COUNT(o.id) AS usageCount, SUM(o.water_amount) AS waterAmount, SUM(o.money_amount) AS moneyAmount FROM tb_orders o " +
            "where o.sell_status= 1 and  pay_notify_time >= ?1 and pay_notify_time <= ?2 GROUP BY o.dev_id) ss ON t.device_id = ss.dev_id  WHERE t.user_id= ?3 GROUP BY t.device_id ORDER BY ss.dev_id desc", nativeQuery = true)
    List<Object[]> findRankByTime(String startTime,String endTime,Integer userId );

}
