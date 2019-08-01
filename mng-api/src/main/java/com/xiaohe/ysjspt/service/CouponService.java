package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.Coupon;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigInteger;
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
     * 更新优惠券
     * @param couponList couponList
     * @param list list
     * @return
     */
    boolean updateCoupon(List<Coupon> couponList,List<Long> list);
    /**
     * * 查询有此devID的优惠券
     * @param list
     * @return
     */
    List<Coupon> findAllByDevIdIn(List<Long> list);

    /**
     * 新增优惠券
     * @param list
     * @return
     */
    List<Coupon> saveAll(List<Coupon> list);

    /**
     * 查询所有存在的设备ID
     * @return
     */
    List<BigInteger> findDevIdList();

    /**
     * 按条件查询
     * @param page 页
     * @param pageSize  数量
     * @param devNo 编号
     * @return Page<Coupon>
     */
    Page<Coupon> findAll(Integer page,Integer pageSize,String devNo);

    /**
     * 按条件查询
     * @param page 页
     * @param pageSize 数量
     * @param list list
     * @return Page<Coupon>
     */
    Page<Coupon> findAll(Integer page,Integer pageSize,List<DeviceEntity> list);


}


