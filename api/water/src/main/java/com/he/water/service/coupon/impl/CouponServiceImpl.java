package com.he.water.service.coupon.impl;

import com.alibaba.fastjson.JSONObject;
import com.he.water.entity.coupon.Coupon;
import com.he.water.entity.coupon.CouponUsage;
import com.he.water.jpa.coupon.CouponRepository;
import com.he.water.service.DeviceService;
import com.he.water.service.coupon.CouponService;
import com.he.water.service.coupon.CouponUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: ysjsApi
 * @description: 优惠券实现类
 * @author: Gmq
 * @date: 2018-11-07 11:07
 **/
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponUsageService couponUsageService;

    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }


    @Override
    public void delete(Integer integer) {
        couponRepository.delete(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return couponRepository.exists(integer);
    }

    @Override
    public long count() {
        return couponRepository.count();
    }

    @Override
    public Coupon findById(Integer integer) {
        return couponRepository.findOne(integer);
    }

    @Override
    public Page<Coupon> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize);
        return couponRepository.findAll(pageable);
    }

    @Override
    public List<Coupon> findAllByDevIdIn(List<Long> list) {
        return couponRepository.findAllByDevIdIn(list);
    }

    @Override
    public Coupon findByDevIdAndStartTimeBeforeAndEndTimeAfter(Long devId, Date startTime, Date endTime) {
        Coupon coupon = couponRepository.findByDevIdAndStartTimeBeforeAndEndTimeAfter(devId, startTime, endTime);
        return coupon;
    }

    @Override
    public List<Coupon> findByStartTimeBeforeAndEndTimeAfter() {
        return couponRepository.findByStartTimeBeforeAndEndTimeAfter(new Date(), new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCouponNum(Coupon coupon) {
        couponRepository.save(coupon);
    }
}