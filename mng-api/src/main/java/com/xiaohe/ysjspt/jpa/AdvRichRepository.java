package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.AdvRich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *  jpa 接口
 *
 * @author gmq
 * @since 2018-11-23
 */

@Transactional(rollbackFor = Exception.class)
public interface AdvRichRepository extends JpaRepository<AdvRich, Integer> {
		
}