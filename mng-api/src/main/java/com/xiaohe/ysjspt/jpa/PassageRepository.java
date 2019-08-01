package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.PassageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author wzq
 * @since 2018-10-15
 */

@Transactional(rollbackFor = Exception.class)
public interface PassageRepository extends JpaRepository<PassageEntity, Integer> {
    /**
     * findAll
     * @param specification
     * @param pageable
     * @return
     */
    Page<PassageEntity> findAll(Specification specification, Pageable pageable);

    /**
     * 根据设备ID查询通道列表
     * @param aLong devId
     * @return
     */
    List<PassageEntity> findAllByDevId(Long aLong);

    /**
     * 根据设备ID查询通道列表
     * @param list list
     * @return List<PassageEntity>
     */
    List<PassageEntity> findAllByDevIdIn(List<Long> list);
}