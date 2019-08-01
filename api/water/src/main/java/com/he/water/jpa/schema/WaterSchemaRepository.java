package com.he.water.jpa.schema;

import com.he.water.entity.schema.WaterSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * jpa 接口
 *
 * @author hzh
 * @since 2018-11-13
 */

@Transactional(rollbackFor = Exception.class)
public interface WaterSchemaRepository extends JpaRepository<WaterSchema, Integer> {


}