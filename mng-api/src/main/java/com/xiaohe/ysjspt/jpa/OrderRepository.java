package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * jpa 接口
 *
 * @author wzq
 * @since 2018-08-27
 */

@Transactional(rollbackFor = Exception.class)
public interface OrderRepository extends JpaRepository<Order, Integer> {
    /**
     * findOders
     *
     * @param devId
     * @param startRow
     * @param pageSize
     * @return
     */
    @Query(value = "select * from tb_orders WHERE 1=1  ORDER BY create_time DESC", nativeQuery = true)
    List<Order> findOders(String devId, Integer startRow, Integer pageSize);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Order findById(Integer id);

    /**
     * 过滤用户下的数据
     *
     * @param specification
     * @param pageable
     * @return
     */
    Page<Order> findAll(Specification specification, Pageable pageable);
}