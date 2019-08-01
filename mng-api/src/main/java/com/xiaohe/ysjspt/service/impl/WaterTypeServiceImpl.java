package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.ysjspt.entity.WaterType;
import com.xiaohe.ysjspt.jpa.WaterTypeRepository;
import com.xiaohe.ysjspt.service.WaterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @program: ysjsApi
 * @description: 售水类型实现
 * @author: Gmq
 * @date: 2018-11-08 17:12
 **/
@Service
public class WaterTypeServiceImpl implements WaterTypeService {
    @Autowired
    private WaterTypeRepository waterTypeRepository;
    @Override
    public WaterType save(WaterType waterType) {
        return waterTypeRepository.save(waterType);
    }

    @Override
    public WaterType findByDevId(Long devId) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
        waterTypeRepository.delete(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return waterTypeRepository.exists(integer);
    }

    @Override
    public long count() {
        return waterTypeRepository.count();
    }

    @Override
    public WaterType findById(Integer integer) {
        return waterTypeRepository.findOne(integer);
    }

    @Override
    public Page<WaterType> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize,Sort.Direction.DESC, "modifyTime");
        return waterTypeRepository.findAll(pageable);
    }
}
