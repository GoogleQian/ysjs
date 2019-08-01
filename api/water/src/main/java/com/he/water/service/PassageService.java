package com.he.water.service;

import com.he.water.entity.PassageEntity;
import com.he.water.jpa.PassageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * TbPassage服务类
 * </p>
 *
 * @author wzq
 * @since 2018-10-15
 */

@Service
public class PassageService {

    @Autowired
    private PassageRepository passageRepository;


    /**
     * 根据设备Id获取通道列表
     *
     * @param devId
     * @return
     */
    public List<PassageEntity> findByDevId(Long devId) {
        return passageRepository.findAllByDevId(devId);
    }

    /**
     * 同设备和通道编号查询
     *
     * @param devId     设备id
     * @param passageNo 通道编号
     * @return
     */
    public PassageEntity findByDevIdAndPassageNo(Long devId, Integer passageNo) {
        return passageRepository.findByDevIdAndPassageNo(devId, passageNo);
    }
}


