package com.xiaohe.ysjspt.jpa;



import com.xiaohe.ysjspt.entity.FilterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author
 * wzq
 */
public interface FilterRepository extends JpaRepository<FilterEntity,Long> {
    /**
     * 获取安装日期
     * @return
     */
    @Modifying
    @Query(value = "select device_id,set_date from tb_device where set_date is not null", nativeQuery = true)
    List<FilterEntity> findSetDate();


    /**
     * 查询有无更换
     * @param devId
     * @return
     */
    @Modifying
    @Query(value = "select count(id) from tb_filter_list d where d.device_id = :devId", nativeQuery = true)
    int selectDevice(@Param("devId")String devId);

    /**
     * 更新安装日期
     * @param setDate
     * @param deviceId
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_device d  SET d.set_date = ?1 where d.device_id = ?2", nativeQuery = true)
    void updateSetDateByDeviceId(String setDate,String deviceId);


    /**
     * 更新最新更换信息
     * @param repairer
     * @param repairerPhoneNumber
     * @param remark
     * @param devId
     * @param planReplaceTime
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_change_list d  SET d.repairer = ?1,d.repairer_phone_number = ?2,d.remark = ?3,replace_finshed = 1 where d.device_id = ?4 and plan_replace_time =?5", nativeQuery = true)
    void updateChangeList(String repairer, String repairerPhoneNumber,String remark,String devId,String planReplaceTime);


    /**
     * 插入设备换芯信息
     * @param deviceId
     * @param firstFilter
     * @param secondFilter
     * @param thirdFilter
     * @param fourthFilter
     * @param customer
     * @param address
     * @param phoneNumber
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into tb_filter_list (device_id,first_filter,second_filter,third_filter,fourth_filter,cust,address,tel) values(?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    int insertFilterList(String deviceId,String firstFilter,String secondFilter,String thirdFilter,String fourthFilter,String customer,String address,String phoneNumber);


    /**
     * 插入设备更换滤芯信息
     * @param deviceId
     * @param firstFilter
     * @param secondFilter
     * @param thirdFilter
     * @param fourthFilter
     * @param customer
     * @param address
     * @param tel
     * @param needTime
     * @param status
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into tb_change_list (device_id,customer,phone_number,replace_first_filter,replace_second_filter,replace_third_filter,replace_fouth_filter,plan_replace_time,address,replace_finshed) values(?1,?6,?8,?2,?3,?4,?5,?9,?7,?10)",nativeQuery = true)
    int insertChangeList(String deviceId,int firstFilter,int secondFilter,int thirdFilter,int fourthFilter,String customer,String address,String tel,String needTime,int status);


    /**
     * 获取设备滤芯信息表
     * @return
     */
    @Query(value = "select * from tb_filter_list", nativeQuery = true)
    List<FilterEntity> findFilter();


    /**
     * 给设备换芯
     * @param deviceId
     * @param firstFilter
     * @param secondFilter
     * @param thirdFilter
     * @param fourthFilter
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_filter_list d  SET d.first_filter = ?2,d.second_filter = ?3,d.third_filter = ?4,d.fourth_filter = ?5 where d.device_id = ?1", nativeQuery = true)
    void updateFilterByDevId(String deviceId,String firstFilter,String secondFilter,String thirdFilter,String fourthFilter);


    /**
     * 只换第一根滤芯
     * @param deviceId
     * @param firstFilter
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_filter_list d  SET d.first_filter = ?2 where d.device_id = ?1", nativeQuery = true)
    void updateFirstFilterByDevId(String deviceId,String firstFilter);


    /**
     * 只换第二根滤芯
     * @param deviceId
     * @param secondFilter
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_filter_list d  SET d.second_filter = ?2 where d.device_id = ?1", nativeQuery = true)
    void updateSecondFilterByDevId(String deviceId,String secondFilter);


    /**
     * 只换第三根滤芯
     * @param deviceId
     * @param thirdFilter
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_filter_list d  SET d.third_filter = ?2 where d.device_id = ?1", nativeQuery = true)
    void updateThirdFilterByDevId(String deviceId,String thirdFilter);


    /**
     * 只换第四跟滤芯
     * @param deviceId
     * @param fourthFilter
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_filter_list d  SET d.fourth_filter = ?2 where d.device_id = ?1", nativeQuery = true)
    void updateFourthFilterByDevId(String deviceId,String fourthFilter);


    /**
     * 更新维修人信息
     * @param repairer
     * @param repairerPhoneNumber
     * @param deviceId
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_filter_list d  SET d.repairer = ?1,d.repairer_phone_number=?2 where d.device_id = ?3", nativeQuery = true)
    void updateRepairerInfo(String repairer,String repairerPhoneNumber,String deviceId);

}
