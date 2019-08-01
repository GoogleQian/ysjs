package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * 设备状态表 jpa 接口
 *
 * @author gmq
 * @since 2018-11-26
 */

@Transactional(rollbackFor = Exception.class)
public interface StatusRepository extends JpaRepository<Status, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<Status> findAll(Specification<Status> spec, Pageable pageable);

    /**
     * 根据Imei查询
     *
     * @return
     */
    @Query(value = "SELECT * FROM tb_status A JOIN ( SELECT max( id ) id FROM tb_status GROUP BY imei ) B ON A.id = B.id where imei in(?1)",nativeQuery = true)
    List<Status> findGroupByImeiIn(List<String> strings);
}