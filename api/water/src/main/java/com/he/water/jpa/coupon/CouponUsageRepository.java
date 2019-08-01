package com.he.water.jpa.coupon;

import com.he.water.entity.coupon.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * jpa 接口
 *
 * @author hzh
 * @since 2018-11-14
 */

@Transactional(rollbackFor = Exception.class)
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {
    /**
     * 通过优惠券表id查询
     *
     * @param couponId
     * @return 查询返回对象
     */
    CouponUsage findByCouponId(Integer couponId);

    /**
     * 查询没有使用的优惠券
     *
     * @param ids
     * @return 查询返回对象
     */
    @Query(value = "SELECT * FROM tb_coupon_usage where status =1 and coupon_id in (?1) and modify_time<date_sub(now(), interval 2 minute) order by modify_time", nativeQuery = true)
    List<CouponUsage> findByNotUsedCouponAndCouponIdIn(List<Integer> ids);

    /**
     * 修改优惠券使用情况
     *
     * @param id
     * @param status
     */
    @Modifying
    @Query(value = "update tb_coupon_usage set status=?2 ,modify_time=now() where id=?1", nativeQuery = true)
    void updateStatus(Long id, int status);
}