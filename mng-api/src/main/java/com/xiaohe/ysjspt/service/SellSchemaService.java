package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.SellSchema;

import java.util.List;


/**
 * <p>
 * 售水方案 SellSolution服务类
 * </p>
 *
 * @author gmq
 * @since 2018-11-08
 */

public interface SellSchemaService extends IBaseService<SellSchema, Integer> {
    /**
     * 根据用户ID查询方案
     * @param integer
     * @return
     */
    List<SellSchema> findAllByUserId(Integer integer);

}


