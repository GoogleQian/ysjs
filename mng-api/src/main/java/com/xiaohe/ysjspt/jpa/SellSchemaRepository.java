package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.SellSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * 售水方案 jpa 接口
 *
 * @author gmq
 * @since 2018-11-08
 */

@Transactional(rollbackFor = Exception.class)
public interface SellSchemaRepository extends JpaRepository<SellSchema, Integer> {
    /**
     * 根据用户ID查询方案
     * @param integer
     * @return
     */
    List<SellSchema> findAllByUserId(Integer integer);
}