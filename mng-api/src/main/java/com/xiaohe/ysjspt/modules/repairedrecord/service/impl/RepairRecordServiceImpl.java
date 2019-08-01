package com.xiaohe.ysjspt.modules.repairedrecord.service.impl;

import com.xiaohe.ysjspt.modules.repairedrecord.entity.RepairRecord;
import com.xiaohe.ysjspt.modules.repairedrecord.entity.RepairRecordVo;
import com.xiaohe.ysjspt.modules.repairedrecord.jpa.RepairRecordRepository;
import com.xiaohe.ysjspt.modules.repairedrecord.service.RepairRecordService;
import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import com.xiaohe.ysjspt.modules.repairer.service.RepairerService;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import com.xiaohe.ysjspt.modules.sys.shiro.ShiroUtils;
import com.xiaohe.ysjspt.modules.sys.untils.Constant;
import com.xiaohe.ysjspt.service.DeviceService;
import com.xiaohe.ysjspt.utils.DevUtils;
import com.xiaohe.ysjspt.utils.JavaWebToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 维修详情表 RepairRecord服务类
 * </p>
 *
 * @author gmq
 * @since 2018-12-10
 */

@Service
public class RepairRecordServiceImpl implements RepairRecordService {

    @Autowired
    private RepairRecordRepository repairRecordRepository;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RepairerService repairerService;

    /**
     * 保存对象
     *
     * @param repairRecord 对象
     *                     持久对象，或者对象集合
     */
    @Override
    public RepairRecord save(RepairRecord repairRecord) {
        return repairRecordRepository.save(repairRecord);
    }

    /**
     * 通过id集合删除对象
     *
     * @param integer
     */
    @Override
    public void delete(Integer integer) {
        repairRecordRepository.delete(integer);
    }

    @Override
    public RepairRecord findByDevId(Long devId) {
        return null;
    }


    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean exists(Integer id) {
        return repairRecordRepository.exists(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return repairRecordRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return RepairRecord对象
     */
    @Override
    public RepairRecord findById(Integer id) {
        return repairRecordRepository.findOne(id);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<RepairRecord>对象
     */
    @Override
    public Page<RepairRecord> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return repairRecordRepository.findAll(pageable);
    }

    @Override
    public Page<RepairRecord> findAll(int page, int pageSize, RepairRecordVo repairRecordVo, List<Integer> repairerIds) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "maintenanceTime");
        Specification<RepairRecord> spec = new Specification<RepairRecord>() {

            @Override
            public Predicate toPredicate(Root<RepairRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(repairRecordVo.getDevCode())) {
                    predicate.getExpressions().add(cb.like(root.get("devCode").as(String.class), "%" + repairRecordVo.getDevCode() + "%"));
                }
                if (repairRecordVo.getRepairerId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("repairerId").as(Integer.class), repairRecordVo.getRepairerId()));
                }
                if (StringUtils.isNotBlank(repairRecordVo.getContent())) {
                    predicate.getExpressions().add(cb.like(root.get("content").as(String.class),  "%" + repairRecordVo.getContent() + "%"));
                }
                if (!CollectionUtils.isEmpty(repairerIds)) {
                    CriteriaBuilder.In<Object> in = cb.in(root.get("repairerId").as(Integer.class));
                    for (Integer id : repairerIds) {
                        in.value(id);
                    }
                    predicate.getExpressions().add(cb.and(in));
                }
                return predicate;
            }
        };
        return repairRecordRepository.findAll(spec, pageable);
    }

}


