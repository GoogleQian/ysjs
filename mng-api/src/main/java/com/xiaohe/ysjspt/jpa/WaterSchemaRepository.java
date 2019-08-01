package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.WaterSchema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * jpa 接口
 *
 * @author gmq
 * @since 2018-11-12
 */

@Transactional(rollbackFor = Exception.class)
public interface WaterSchemaRepository extends JpaRepository<WaterSchema, Integer> {
    /**
     * 根据用户ID查询方案
     *
     * @param integer
     * @return
     */
    List<WaterSchema> findAllByUserId(Integer integer);


    /**
     * * 判断是否存在方案名
     * @param s 名称
     * @return boolean
     */
    boolean existsByProNameEquals(String s);

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<WaterSchema> findAll(Specification<WaterSchema> spec, Pageable pageable);

}