package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.WaterMngEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *  jpa 接口
 *
 * @author wzq
 * @since 2018-10-16
 */

@Transactional(rollbackFor = Exception.class)
public interface WaterMngRepository extends JpaRepository<WaterMngEntity, Integer> {
		
}