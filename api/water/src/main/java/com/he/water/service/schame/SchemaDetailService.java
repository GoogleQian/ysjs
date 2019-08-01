package com.he.water.service.schame;

import com.he.water.entity.schema.SchemaDetail;
import com.he.water.service.base.IBaseService;

import java.util.List;

/**
 * <p>
 * SchemaDetail接口
 * </p>
 *
 * @author hzh
 * @since 2018-11-13
 */

public interface SchemaDetailService extends IBaseService<SchemaDetail, Integer> {
    /**
     * 通过售水方案id查询
     *
     * @param schemaId
     * @return
     */
    List<SchemaDetail> findBySchemaId(Integer schemaId);
}


