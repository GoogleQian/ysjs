package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *  jpa 接口
 *
 * @author wzq
 * @since 2018-10-15
 */

@Transactional(rollbackFor = Exception.class)
public interface MerchantRepository extends JpaRepository<MerchantEntity, Integer> {

}