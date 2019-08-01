package com.he.water.service.schame.impl;

import com.he.water.entity.schema.SchemaDetail;
import com.he.water.jpa.schema.SchemaDetailRepository;
import com.he.water.service.schame.SchemaDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * SchemaDetail服务类
 * </p>
 *
 * @author hzh
 * @since 2018-11-13
 */

@Service
public class SchemaDetailServiceImpl implements SchemaDetailService {

    @Autowired
    private SchemaDetailRepository schemaDetailRepository;

    /**
     * 保存对象
     *
     * @param schemaDetail 对象
     *                     持久对象，或者对象集合
     */
    @Override
    public SchemaDetail save(SchemaDetail schemaDetail) {
        return schemaDetailRepository.save(schemaDetail);
    }



    @Override
    public void delete(Integer integer) {

    }

    @Override
    public boolean exists(Integer integer) {
        return false;
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return schemaDetailRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return SchemaDetail对象
     */
    @Override
    public SchemaDetail findById(Integer id) {
        return schemaDetailRepository.findOne(id);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<SchemaDetail>对象
     */
    @Override
    public Page<SchemaDetail> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return schemaDetailRepository.findAll(pageable);
    }

    @Override
    public List<SchemaDetail> findBySchemaId(Integer schemaId) {
        return schemaDetailRepository.findBySchemaId(schemaId);
    }
}


