package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.controller.OrderController;
import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.entity.Order;
import com.xiaohe.ysjspt.jpa.OrderRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.List;


/**
 * <p>
 * TbOrders服务类
 * </p>
 *
 * @author wzq
 * @since 2018-08-27
 */

@Service
public class OrdersService {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderRepository ordersRepository;
    @Autowired
    private DeviceService deviceService;

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<TbOrders>
     * @throws Exception properties 字符串为需要排序的字段，可以传多个
     */
    public Page<Order> findAll(int page, int pageSize, String deviceId) {
        Pageable pageable = new PageRequest(page - 1, pageSize, Sort.Direction.DESC, "id");
        List<DeviceEntity> deviceByUser = deviceService.findDeviceByUser();
        if (CollectionUtils.isEmpty(deviceByUser)) {
            return null;
        }
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(deviceId)) {
                    predicate.getExpressions().add(cb.like(root.get("devId").as(String.class), "%" + deviceId + "%"));
                }
                //获取用户下的扫码记录
                if (!CollectionUtils.isEmpty(deviceByUser)) {
                    CriteriaBuilder.In<Object> in = cb.in(root.get("devId").as(String.class));
                    for (DeviceEntity deviceEntity : deviceByUser) {
                        in.value(deviceEntity.getDeviceId());
                    }
                    predicate.getExpressions().add(cb.and(in));
                }
                return predicate;
            }
        };
        Page<Order> all = ordersRepository.findAll(specification, pageable);

        return all;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Order findById(Integer id) {
        return ordersRepository.findById(id);
    }

    public void save(Order order) {
        ordersRepository.save(order);
    }
}