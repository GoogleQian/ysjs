package com.xiaohe.ysjspt.jpa;

import org.springframework.transaction.annotation.Transactional;
import com.xiaohe.ysjspt.entity.SellProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 *  jpa 接口
 *
 * @author wzq
 * @since 2018-08-28
 */

@Transactional(rollbackFor = Exception.class)
public interface SellProgramRepository extends JpaRepository<SellProgramEntity, Integer> {

}