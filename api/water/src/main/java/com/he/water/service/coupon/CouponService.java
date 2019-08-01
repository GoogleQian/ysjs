package com.he.water.service.coupon;

import com.he.water.entity.coupon.Coupon;
import com.he.water.service.base.IBaseService;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Coupon服务类
 * </p>
 *
 * @author gmq
 * @since 2018-11-07
 */
public interface CouponService extends IBaseService<Coupon, Integer> {

    /**
     * * 查询有此devID的优惠券
     *
     * @param list
     * @return
     */
    List<Coupon> findAllByDevIdIn(List<Long> list);

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
     * @return
     */
    List<Coupon> findByStartTimeBeforeAndEndTimeAfter();

    /**
     * 更新优惠券数量
     *
     * @param coupon
     */
    void updateCouponNum(Coupon coupon);
}


