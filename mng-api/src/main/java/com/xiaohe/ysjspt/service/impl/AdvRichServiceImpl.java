package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.ysjspt.entity.AdvRich;
import com.xiaohe.ysjspt.jpa.AdvRichRepository;
import com.xiaohe.ysjspt.service.AdvRichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ysjsApi
 * @description: 富文本图片
 * @author: Gmq
 * @date: 2018-11-23 09:59
 **/
@Service
public class AdvRichServiceImpl implements AdvRichService {

    @Autowired
    private AdvRichRepository advRichRepository;

    @Override
    public AdvRich save(AdvRich advRich) {
        return advRichRepository.save(advRich);
    }

    @Override
    public AdvRich findByDevId(Long devId) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
        advRichRepository.delete(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return advRichRepository.exists(integer);
    }

    @Override
    public long count() {
        return advRichRepository.count();
    }

    @Override
    public AdvRich findById(Integer integer) {
        return advRichRepository.findOne(integer);
    }

    @Override
    public Page<AdvRich> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return advRichRepository.findAll(pageable);
    }

    @Override
    public List<AdvRich> saveList(List<AdvRich> list) {
        return advRichRepository.save(list);
    }
}
