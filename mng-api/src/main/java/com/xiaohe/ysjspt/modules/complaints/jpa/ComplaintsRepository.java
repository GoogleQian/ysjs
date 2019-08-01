package com.xiaohe.ysjspt.modules.complaints.jpa;

import com.xiaohe.ysjspt.modules.complaints.entity.Complaints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 投诉建议表 jpa 接口
 *
 * @author gmq
 * @since 2018-12-10
 */

@Transactional(rollbackFor = Exception.class)
public interface ComplaintsRepository extends JpaRepository<Complaints, Integer> {

    /**
     * 按条件查询方案
     *
     * @param spec     spec
     * @param pageable 分页
     * @return page
     */
    Page<Complaints> findAll(Specification<Complaints> spec, Pageable pageable);

    /**
     * 修改上报建议的处理状态
     *
     * @param id     设备code
     * @param date   更新时间
     * @param status 处理结果
     */
    @Modifying
    @Query(value = "update  Complaints set status=?2 , updateTime =?3  where id=?1")
    void updateStatusById(Integer id, Integer status, Date date);

    /**
     * 获取最新的设备
     *
     * @param devCode 设备编号
     * @return 查询结果
     */
    @Query(value = "select * from  tb_complaints  where dev_code=?1 ORDER BY report_time DESC LIMIT 1", nativeQuery = true)
    Complaints findLatestByDevCode(String devCode);

    boolean existsByDevCode(String devCode);
}