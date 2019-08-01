package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.entity.WaterSchema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * WaterSchema服务类
 * </p>
 *
 * @author gmq
 * @since 2018-11-12
 */

@Service
public interface WaterSchemaService extends IBaseService<WaterSchema, Integer> {

    /**
     * 根据用户ID查询方案
     *
     * @param integer
     * @return
     */
    List<WaterSchema> findAllByUserId(Integer integer);

    /**
     * 保存对象
     * @param schema
     * @return
     */
    Result saveSchema(WaterSchema schema);

    /**
     * 判断是否存在方案名
     * @param s 名称
     * @return
     */
    boolean existsByProNameEquals(String s);

    /**
     * 修改对象
     * @param schema
     * @return
     */
    Result update(WaterSchema schema);

    /**
     * 按条件查询方案
     * @param page 当前页
     * @param pageSize 页数量
     * @param waterSchema 方案
     * @param userId 用户ID
     * @return Page<WaterSchema>
     */
    Page<WaterSchema> findAll(int page, int pageSize,WaterSchema waterSchema,Long userId);


}


