package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.WaterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *  jpa 接口
 *
 * @author gmq
 * @since 2018-11-08
 */

@Transactional(rollbackFor = Exception.class)
public interface WaterTypeRepository extends JpaRepository<WaterType, Integer> {

}