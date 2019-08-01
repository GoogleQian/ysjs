package com.he.water.jpa.ad;


import com.he.water.entity.ad.AdvertisingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 广告表 jpa 接口
 *
 * @author wzq
 * @since 2018-11-19
 */

@Transactional(rollbackFor = Exception.class)
public interface AdvertisingRepository extends JpaRepository<AdvertisingEntity, Integer> {
    /**
     * 通过用户id获取广告类表
     *
     * @param userId 用户id
     * @return 用户下的广告
     */
    List<AdvertisingEntity> findByUserId(Integer userId);

}
