package com.he.water.jpa.schema;

import com.he.water.entity.schema.SchemaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * jpa 接口
 *
 * @author hzh
 * @since 2018-11-13
 */

@Transactional(rollbackFor = Exception.class)
public interface SchemaDetailRepository extends JpaRepository<SchemaDetail, Integer> {



    /**
     * 通过方案id查询
     *
     * @param schemaId
     * @return
     */
    List<SchemaDetail> findBySchemaId(Integer schemaId);

}