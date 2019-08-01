package com.he.water.service.coupon.impl;

import com.he.water.entity.coupon.Coupon;
import com.he.water.entity.coupon.CouponUsage;
import com.he.water.jpa.coupon.CouponUsageRepository;
import com.he.water.service.coupon.CouponUsageService;
import com.he.water.utils.ConsantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * CouponUsage服务类
 * </p>
 *
 * @author hzh
 * @since 2018-11-14
 */

@Service
public class CouponUsageServiceImpl implements CouponUsageService {

    @Autowired
    private CouponUsageRepository couponUsageRepository;

    /**
     * 保存对象
     *
     * @param couponUsage 对象
     *                    持久对象，或者对象集合
     */
    @Override
    public CouponUsage save(CouponUsage couponUsage) {
        return couponUsageRepository.save(couponUsage);
    }


    @Override
    public void delete(Long aLong) {

    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public CouponUsage findById(Long aLong) {
        return null;
    }

    @Override
    public Page<CouponUsage> findAll(int page, int pageSize) {
        return null;
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return couponUsageRepository.count();
    }


    @Override
    public CouponUsage findByCouponId(Integer couponId) {
        return couponUsageRepository.findByCouponId(couponId);
    }

    @Override
    public List<CouponUsage> findByNotUsedCouponAndCouponIdIn(List<Integer> ids) {
        return couponUsageRepository.findByNotUsedCouponAndCouponIdIn(ids);
    }

    @Override
    public void updateStatus(Long id, int status) {

        couponUsageRepository.updateStatus(id, status);

    }

    @Override
    public int findByNotUsedNumByCouponId(int couponId) {
        List<Integer> ids = new ArrayList<>();
        ids.add(couponId);
        List<CouponUsage> couponUsages = findByNotUsedCouponAndCouponIdIn(ids);
        if (CollectionUtils.isEmpty(couponUsages)) {
            return 0;
        }
        //更新couponUsages使用状态
        for (CouponUsage couponUsage : couponUsages) {
            couponUsage.setModifyTime(new Date());
            couponUsage.setStatus(ConsantUtil.COUPUN_CANCEL);
        }
        couponUsageRepository.save(couponUsages);
        return couponUsages.size();
    }

    @Override
    public CouponUsage saveCouponUsage(Coupon coupon) {
        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setCouponId(coupon.getId());
        couponUsage.setModifyTime(new Date());
        //调用优惠券，而没有付款状态
        couponUsage.setStatus(ConsantUtil.COUPUN_GET);
        ;
        return save(couponUsage);
    }

}


