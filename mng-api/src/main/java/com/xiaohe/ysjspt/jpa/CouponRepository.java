package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author gmq
 * @since 2018-11-07
 */

@Transactional(rollbackFor = Exception.class)
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    /**
     * * 查询有此devID的优惠券
     * @param list
     * @return
     */
     List<Coupon> findAllByDevIdIn(List<Long> list);

    /**
     * 查询存在的设备ID
     * @return
     */
    @Query(value = "select DISTINCT dev_id from tb_coupon ",nativeQuery = true)
     List<BigInteger> findDevIdList();

    /**
     * 按条件分页查询
     * @param specification 条件
     * @param pageable 分页
     * @return Page<Coupon>
     */
    Page<Coupon> findAll(Specification<Coupon> specification, Pageable pageable);

}