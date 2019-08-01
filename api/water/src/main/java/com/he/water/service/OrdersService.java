package com.he.water.service;


import com.he.water.entity.Orders;
import com.he.water.jpa.OrdersRepository;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * @author
 */
@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    public Orders findByOrderNo(String orderNo) {
        return ordersRepository.findByOrderNo(orderNo);
    }

    public Orders findById(Integer id) {

        return ordersRepository.findById(id);
    }

    List<Orders> findByPayNotifyTime() {

        return ordersRepository.findByPayNotifyTime();
    }

    List<Orders> findList() {
        return ordersRepository.findAll();
    }

}
