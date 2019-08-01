package com.xiaohe.ysjspt.modules.errorLog.service.impl;

import com.xiaohe.ysjspt.modules.errorLog.entity.ErrorLog;
import com.xiaohe.ysjspt.modules.errorLog.entity.QueryErrorLog;
import com.xiaohe.ysjspt.modules.errorLog.jpa.ErrorLogRepository;
import com.xiaohe.ysjspt.modules.errorLog.service.ErrorLogService;
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

/**
 * <p>
 * ErrorLog服务类
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    @Autowired
    private ErrorLogRepository errorLogRepository;

    /**
     * 保存对象
     *
     * @param errorLog 对象
     *                 持久对象，或者对象集合
     */
    @Override
    public ErrorLog save(ErrorLog errorLog) {
        return errorLogRepository.save(errorLog);
    }

    @Override
    public ErrorLog findByDevId(Long devId) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
        errorLogRepository.delete(integer);
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean exists(Integer id) {
        return errorLogRepository.exists(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return errorLogRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return ErrorLog对象
     */
    @Override
    public ErrorLog findById(Integer id) {
        return errorLogRepository.findOne(id);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<ErrorLog>对象
     */
    @Override
    public Page<ErrorLog> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return errorLogRepository.findAll(pageable);
    }

    @Override
    public Page<ErrorLog> findAll(int page, int pageSize, QueryErrorLog queryErrorLog) {
        if (queryErrorLog == null) {
            return findAll(page, pageSize);
        }
        //过滤自己的广告
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<ErrorLog> spec = new Specification<ErrorLog>() {
            @Override
            public Predicate toPredicate(Root<ErrorLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(queryErrorLog.getReqUrl())) {
                    predicate.getExpressions().add(cb.like(root.get("reqUrl").as(String.class), "%" + queryErrorLog.getReqUrl() + "%"));
                }
                if (StringUtils.isNotBlank(queryErrorLog.getReqType())) {
                    predicate.getExpressions().add(cb.like(root.get("reqType").as(String.class), "%" + queryErrorLog.getReqType() + "%"));
                }
                if (StringUtils.isNotBlank(queryErrorLog.getReqParam())) {
                    predicate.getExpressions().add(cb.like(root.get("reqParam").as(String.class), "%" + queryErrorLog.getReqParam() + "%"));
                }
                if (StringUtils.isNotBlank(queryErrorLog.getIp())) {
                    predicate.getExpressions().add(cb.like(root.get("ip").as(String.class), "%" + queryErrorLog.getIp() + "%"));
                }
                if (StringUtils.isNotBlank(queryErrorLog.getUserAgent())) {
                    predicate.getExpressions().add(cb.like(root.get("userAgent").as(String.class), "%" + queryErrorLog.getUserAgent() + "%"));
                }
                if (StringUtils.isNotBlank(queryErrorLog.getErrorInfo())) {
                    predicate.getExpressions().add(cb.like(root.get("errorInfo").as(String.class), "%" + queryErrorLog.getErrorInfo() + "%"));
                }
                if (StringUtils.isNotBlank(queryErrorLog.getStartTime())) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), queryErrorLog.getStartTime()));
                }
                if (StringUtils.isNotBlank(queryErrorLog.getEndTime())) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), queryErrorLog.getEndTime()));
                }
                return predicate;
            }

        };
        return errorLogRepository.findAll(spec, pageable);
    }

}


