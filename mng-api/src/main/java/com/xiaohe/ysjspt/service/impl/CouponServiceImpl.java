package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.ysjspt.entity.Coupon;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.jpa.CouponRepository;
import com.xiaohe.ysjspt.service.CouponService;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.LinkedList;
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
    @Override
    public Coupon save(Coupon coupon) {
        //转化price
        String strPrice = coupon.getStrPrice();
        Number  number=Float.parseFloat(strPrice) * 100;
        int i = number.intValue();
        String s = StringUtil.formatPrice(strPrice);
        coupon.setStrPrice(s);
        coupon.setPrice(i);
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon findByDevId(Long devId) {
        return null;
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
    public boolean updateCoupon(List<Coupon> couponList,List<Long> list) {
        //判断优惠券表存在其中ID 更新 不存在新增
        //查询有devId的优惠券
        List<Coupon> allByDevIdIn = findAllByDevIdIn(list);
        for (Coupon coupon : allByDevIdIn) {
            for (Coupon coupon1 : couponList) {
                if(!coupon1.getDevId().equals(coupon.getDevId())){
                    continue;
                }
                coupon1.setId(coupon.getId());
            }
        }
        List<Coupon> save = couponRepository.save(couponList);
        if(!CollectionUtils.isEmpty(save)){
            return true;
        }
        return false;
    }

    @Override
    public List<Coupon> findAllByDevIdIn(List<Long> list) {
        return couponRepository.findAllByDevIdIn(list);
    }

    @Override
    public List<Coupon> saveAll(List<Coupon> list) {
        return couponRepository.save(list);
    }

    @Override
    public List<BigInteger> findDevIdList() {
        return couponRepository.findDevIdList();
    }

    @Override
    public Page<Coupon> findAll(Integer page, Integer pageSize,String devNo) {
        List<DeviceEntity> allByDeviceIdLike=deviceService.findDeviceByUser();
        List<DeviceEntity> entities = new LinkedList<>();
        if(""!=devNo){
            for (DeviceEntity deviceEntity : allByDeviceIdLike) {
                if(deviceEntity.getDeviceId().contains(devNo)){
                    entities.add(deviceEntity);
                }
            }
        }
        if(!StringUtils.isBlank(devNo)&&CollectionUtils.isEmpty(entities)){
            return null;
        }
        //过滤自己的设备
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<Coupon> spec = new Specification<Coupon>() {
            @Override
            public Predicate toPredicate(Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (!CollectionUtils.isEmpty(entities)) {
                    //模糊查询设备Id
                    CriteriaBuilder.In<Object> in = cb.in(root.get("devId").as(BigInteger.class));
                    for (DeviceEntity entity : entities) {
                        in.value(BigInteger.valueOf(entity.getId()));
                    }
                    predicate.getExpressions().add(cb.and(in));
                }else{
                    //模糊查询设备Id
                    CriteriaBuilder.In<Object> in = cb.in(root.get("devId").as(BigInteger.class));
                    for (DeviceEntity entity : allByDeviceIdLike) {
                        in.value(BigInteger.valueOf(entity.getId()));
                    }
                    predicate.getExpressions().add(cb.and(in));
                }
                return predicate;
            }
        };
        return couponRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Coupon> findAll(Integer page, Integer pageSize, List<DeviceEntity> list) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        if(CollectionUtils.isEmpty(list)){
            return findAll(page,pageSize);
        }
        //查询条件构造
        Specification spec = (Specification) (root, query, cb) ->{
                Predicate predicate = cb.conjunction();
                    //模糊查询设备Id
                    CriteriaBuilder.In<Object> in = cb.in(root.get("devId").as(BigInteger.class));
                    for (DeviceEntity  deviceEntity : list) {
                        in.value(BigInteger.valueOf(deviceEntity.getId()));
                    }
                    predicate.getExpressions().add(cb.and(in));
                return predicate;
        };
        return couponRepository.findAll(spec,pageable);
    }
}