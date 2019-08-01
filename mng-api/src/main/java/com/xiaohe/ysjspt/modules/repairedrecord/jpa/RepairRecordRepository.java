package com.xiaohe.ysjspt.modules.repairedrecord.jpa;

import com.xiaohe.ysjspt.modules.repairedrecord.entity.RepairRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 维修详情表 jpa 接口
 *
 * @author gmq
 * @since 2018-12-10
 */

@Transactional(rollbackFor = Exception.class)
public interface RepairRecordRepository extends JpaRepository<RepairRecord, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<RepairRecord> findAll(Specification<RepairRecord> spec, Pageable pageable);

}