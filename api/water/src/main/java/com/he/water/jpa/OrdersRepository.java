package com.he.water.jpa;


import com.he.water.entity.Orders;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author
 */
@Transactional(rollbackFor = Exception.class)
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    /**
     * 通过订单号查询
     *
     * @param orderNo
     * @return
     */
    Orders findByOrderNo(String orderNo);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Orders findById(Integer id);

    /**
     * 获取没有出水成功的订单
     *
     * @return
     */
    @Query(value = "SELECT * FROM tb_orders where pay_status=2  AND sell_status!=1  and create_time<date_sub(now(), interval 3 minute) order by create_time", nativeQuery = true)
    List<Orders> findByPayNotifyTime();

}