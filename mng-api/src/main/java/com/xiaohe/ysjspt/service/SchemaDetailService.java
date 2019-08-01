package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.SchemaDetail;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  SchemaDetail服务类
 * </p>
 *
 * @author gmq
 * @since 2018-11-12
 */
 
public interface SchemaDetailService extends IBaseService<SchemaDetail,Integer>{


    /**
     * 根据方案ID集合查询
     * @param integers 方案ID
     * @return List<SchemaDetail>
     */
    List<SchemaDetail> findAllBySchemaIdIn(List<Integer>  integers);

    /**
     * 批量删除 根据方案ID
     * @param integers 方案id
     */
    void deleteAllBySchemaIdIn(List<Integer> integers);

}


