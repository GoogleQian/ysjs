package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.ysjspt.entity.Status;
import com.xiaohe.ysjspt.jpa.StatusRepository;
import com.xiaohe.ysjspt.service.StatusService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @program: ysjsApi
 * @description: 水质状态
 * @author: Gmq
 * @date: 2018-11-26 09:56
 **/
@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;
    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status findByDevId(Long devId) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
        statusRepository.delete(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return statusRepository.exists(integer);
    }

    @Override
    public long count() {
        return statusRepository.count();
    }

    @Override
    public Status findById(Integer integer) {
        return statusRepository.findOne(integer);
    }

    @Override
    public Page<Status> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return statusRepository.findAll(pageable);
    }

    @Override
    public Page<Status> findAll(int page, int pageSize, Status status) {
        if(status==null){
            return findAll(page,pageSize);
        }
        //过滤自己的广告
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<Status> spec = new Specification<Status>() {
            @Override
            public Predicate toPredicate(Root<Status> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(status.getImei())) {
                        predicate.getExpressions().add(cb.like(root.get("imei").as(String.class), "%"+status.getImei()+"%"));
                }
                if (StringUtils.isNotBlank(status.getStartTime())) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), status.getStartTime()));
                }
                if (StringUtils.isNotBlank(status.getEndTime())) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), status.getEndTime()));
                }
                return predicate;
            }
        };
        return statusRepository.findAll(spec, pageable);
    }

    @Override
    public List<Status> findAllByImeiIn(List<String> strings) {
        return statusRepository.findGroupByImeiIn(strings);
    }
}
