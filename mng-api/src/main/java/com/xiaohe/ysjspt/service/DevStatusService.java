package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.DevStatus;
import com.xiaohe.ysjspt.jpa.DevStatusRepository;
import com.xiaohe.ysjspt.modules.sys.service.SysUserService;
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
 * DevStatus服务类
 * </p>
 *
 * @author wzq
 * @since 2018-08-27
 */

@Service
public class DevStatusService {

    @Autowired
    private DevStatusRepository devstatusRepository;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存对象
     *
     * @param devstatus 持久对象，或者对象集合
     * @throws Exception
     */
    public void save(DevStatus devstatus) {
        devstatusRepository.save(devstatus);
    }

    /**
     * 删除对象
     *
     * @param devstatus
     * @throws Exception
     */

    public void delete(DevStatus devstatus) {
        devstatusRepository.delete(devstatus);
    }


    /**
     * 返回可用实体的数量
     *
     * @throws Exception
     */
    public long count() {
        return devstatusRepository.count();
    }


    /**
     * 分页查询设备运行状态
     *
     * @param page
     * @param pageSize
     * @return Page<DevStatus>
     * @throws Exception properties 字符串为需要排序的字段，可以传多个
     */
    public Page<DevStatus> findAll(int page, int pageSize, String deviceId, String startTime, String endTime) {
        Pageable pageable = new PageRequest(page - 1, pageSize, Sort.Direction.DESC, "id");

        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //增加筛选条件

                Predicate predicate = cb.conjunction();
                if (!"".equals(deviceId)) {
                    predicate.getExpressions().add(cb.like(root.get("deviceId").as(String.class), "%" + deviceId + "%"));
                }

                //起始日期
                if (!"".equals(startTime)) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("updateTime").as(String.class), startTime));
                }
                //结束日期
                if (!"".equals(endTime)) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("updateTime").as(String.class), endTime));
                }
                return predicate;
            }
        };

        return devstatusRepository.findAll(specification, pageable);
    }

}


