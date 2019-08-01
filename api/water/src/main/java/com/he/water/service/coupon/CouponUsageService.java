package com.he.water.service.coupon;

import com.he.water.entity.coupon.Coupon;
import com.he.water.entity.coupon.CouponUsage;
import com.he.water.service.base.IBaseService;

import java.util.List;

/**
 * <p>
 * CouponUsage接口
 * </p>
 *
 * @author hzh
 * @since 2018-11-14
 */

public interface CouponUsageService extends IBaseService<CouponUsage, Long> {
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
     * @return 查询返回对象
     */
    List<CouponUsage> findByNotUsedCouponAndCouponIdIn(List<Integer> ids);

    /**
     * 根据id 更新优惠券使用状态
     *
     * @param
     */
    void updateStatus(Long id, int status);

    int findByNotUsedNumByCouponId(int couponId);

    CouponUsage saveCouponUsage(Coupon coupon);
}


