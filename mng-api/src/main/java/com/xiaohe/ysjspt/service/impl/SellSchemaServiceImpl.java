package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.ysjspt.entity.SellSchema;
import com.xiaohe.ysjspt.jpa.SellSchemaRepository;
import com.xiaohe.ysjspt.service.SellSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ysjsApi
 * @description: 售水方案实现类
 * @author: Gmq
 * @date: 2018-11-08 15:20
 **/
@Service
public class SellSchemaServiceImpl implements SellSchemaService {
    @Autowired
    private SellSchemaRepository sellSchemaRepository;
    @Override
    public SellSchema save(SellSchema schema) {
        return sellSchemaRepository.save(schema);
    }

    @Override
    public SellSchema findByDevId(Long devId) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
        sellSchemaRepository.delete(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return sellSchemaRepository.exists(integer);
    }

    @Override
    public long count() {
        return sellSchemaRepository.count();
    }

    @Override
    public SellSchema findById(Integer integer) {
        return sellSchemaRepository.findOne(integer);
    }

    @Override
    public Page<SellSchema> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize);
        return sellSchemaRepository.findAll(pageable);
    }

    @Override
    public List<SellSchema> findAllByUserId(Integer integer) {
        return sellSchemaRepository.findAllByUserId(integer);
    }
}
