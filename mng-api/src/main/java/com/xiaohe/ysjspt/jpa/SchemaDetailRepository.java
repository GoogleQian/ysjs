package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.SchemaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author gmq
 * @since 2018-11-12
 */

@Transactional(rollbackFor = Exception.class)
public interface SchemaDetailRepository extends JpaRepository<SchemaDetail, Integer> {
    /**
     * 根据方案ID集合查询
     * @param integers integers
     * @return List<SchemaDetail>
     */
    List<SchemaDetail> findAllBySchemaIdIn(List<Integer> integers);

    /**
     * 批量删除 根据方案ID
     * @param integers 方案id
     */
    void deleteAllBySchemaIdIn(List<Integer> integers);
		
}