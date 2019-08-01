package com.he.water.service.schame.impl;

import com.he.water.entity.schema.WaterSchema;
import com.he.water.jpa.schema.WaterSchemaRepository;
import com.he.water.service.schame.WaterSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * <p>
 * WaterSchema服务类
 * </p>
 *
 * @author hzh
 * @since 2018-11-13
 */

@Service
public class WaterSchemaServiceImpl implements WaterSchemaService {

    @Autowired
    private WaterSchemaRepository waterSchemaRepository;

    /**
     * 保存对象
     *
     * @param waterSchema 对象
     *                    持久对象，或者对象集合
     */
    @Override
    public WaterSchema save(WaterSchema waterSchema) {
        return waterSchemaRepository.save(waterSchema);
    }


    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return waterSchemaRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return WaterSchema对象
     */
    @Override
    public WaterSchema findById(Integer id) {
        return waterSchemaRepository.findOne(id);
    }


    @Override
    public void delete(Integer integer) {

    }

    @Override
    public boolean exists(Integer integer) {
        return false;
    }

    @Override
    public Page<WaterSchema> findAll(int page, int pageSize) {
        return null;
    }
}


