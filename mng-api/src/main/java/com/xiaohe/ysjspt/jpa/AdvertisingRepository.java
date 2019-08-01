package com.xiaohe.ysjspt.jpa;


import com.xiaohe.ysjspt.entity.AdvertisingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 广告表 jpa 接口
 * @author wzq
 * @since 2018-11-19
 */

@Transactional(rollbackFor = Exception.class)
public interface AdvertisingRepository extends JpaRepository<AdvertisingEntity, Integer> {

    /**
     * 根据标题查询数量
     * @return
     */
    int countAllByTitle(String title);

    @Query(value = "select title from tb_advertising where user_id=?1",nativeQuery = true)
    List<String> findTitleByUserId(Long aLong);

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<AdvertisingEntity> findAll(Specification<AdvertisingEntity> spec, Pageable pageable);
}
