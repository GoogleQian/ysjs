package com.he.water.jpa.coupon;

import com.he.water.entity.coupon.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * jpa 接口
 *
 * @author gmq
 * @since 2018-11-07
 */

@Transactional(rollbackFor = Exception.class)
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    /**
     * * 查询有此devID的优惠券
     *
     * @param list
     * @return
     */
    List<Coupon> findAllByDevIdIn(List<Long> list);

    /**
     * 查询存在的设备ID
     *
     * @return
     */
    @Query(value = "select DISTINCT dev_id from tb_coupon ", nativeQuery = true)
    List<BigInteger> findDevIdList();


    /**
     * 获取有效时间的优惠券
     *
     * @param devId
     * @param startTime
     * @param endTime
     * @return
     */
    Coupon findByDevIdAndStartTimeBeforeAndEndTimeAfter(Long devId, Date startTime, Date endTime);

    /**
     * 获取所有设备有效时间的优惠券
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Coupon> findByStartTimeBeforeAndEndTimeAfter(Date startTime, Date endTime);
}