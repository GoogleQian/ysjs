package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.ysjspt.entity.SchemaDetail;
import com.xiaohe.ysjspt.jpa.SchemaDetailRepository;
import com.xiaohe.ysjspt.service.SchemaDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ysjsApi
 * @description: 方案详情
 * @author: Gmq
 * @date: 2018-11-12 14:29
 **/
@Service
public class SchemaDetailServiceImpl implements SchemaDetailService {
    @Autowired
    private SchemaDetailRepository repository;
    @Override
    public SchemaDetail save(SchemaDetail schemaDetail) {
        return repository.save(schemaDetail);
    }

    @Override
    public SchemaDetail findByDevId(Long devId) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
        repository.delete(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return repository.exists(integer);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public SchemaDetail findById(Integer integer) {
        return repository.findOne(integer);
    }

    @Override
    public Page<SchemaDetail> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize);
        return repository.findAll(pageable);
    }

    @Override
    public List<SchemaDetail> findAllBySchemaIdIn(List<Integer> integers) {
        return repository.findAllBySchemaIdIn(integers);
    }

    @Override
    public void deleteAllBySchemaIdIn(List<Integer> integers) {
        repository.deleteAllBySchemaIdIn(integers);
    }
}
