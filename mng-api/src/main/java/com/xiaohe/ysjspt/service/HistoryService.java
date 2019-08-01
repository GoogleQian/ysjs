package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.DeviceEntity;
import com.xiaohe.ysjspt.entity.HistoryEntity;
import com.xiaohe.ysjspt.jpa.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


/**
 * 历史记录service服务类
 *
 * @version <pre>
 * @Author Version        Date		Changes
 * admin    1.0  2018年05月03日 Created
 *
 * </pre>
 * @since 1.
 */
@Service("historyService")
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private DeviceService deviceService;


    public void saveHistory(HistoryEntity entity) {
        historyRepository.save(entity);
    }

    public void updateHistory(HistoryEntity entity) {
        historyRepository.save(entity);
    }

    public Page<HistoryEntity> findAllHistory(Integer pageIndex, Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex, pageSize, Sort.Direction.DESC, "id");
        return historyRepository.findAll(pageable);
    }


    public Page<HistoryEntity> findAll(Integer page, Integer pageSize, String deviceId, Date startTime, Date endTime) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "historyId");
        List<DeviceEntity> deviceByUser = deviceService.findDeviceByUser();
        if (CollectionUtils.isEmpty(deviceByUser)) {
            return null;
        }
        //查询条件构造
        Specification<HistoryEntity> spec = new Specification<HistoryEntity>() {
            @Override
            public Predicate toPredicate(Root<HistoryEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> name = root.get("deviceId");
                Predicate p1;
                Predicate p = null;
                if (deviceId != null) {
                    p1 = cb.like(name, "%" + deviceId + "%");
                    p = cb.and(p1);
                }
                //获取用户下的水质信息
                CriteriaBuilder.In<Object> in = cb.in(root.get("deviceId").as(String.class));
                for (DeviceEntity deviceEntity : deviceByUser) {
                    in.value(deviceEntity.getDeviceId());
                }
                p = cb.and(in);

                Path<Date> recordTime = root.get("recordTime");
                Predicate p5;
                Predicate p4;
//                if (startTime != null) {
//                    p4 = cb.greaterThanOrEqualTo(recordTime, startTime);
//                    p = cb.and(p4);
//                }
//                if (endTime != null) {
//                    p5 = cb.lessThanOrEqualTo(recordTime, endTime);
//                    p = cb.and(p5);
//                }
                return p;
            }
        };

        return historyRepository.findAll(spec, pageable);

    }
}
