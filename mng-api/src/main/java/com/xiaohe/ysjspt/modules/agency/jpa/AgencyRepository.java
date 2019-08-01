package com.xiaohe.ysjspt.modules.agency.jpa;

import com.xiaohe.ysjspt.modules.agency.entity.Agency;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * 
 * 经销商表 jpa 接口
 *
 * @author gmq
 * @since 2019-01-09
 */

@Transactional(rollbackFor = Exception.class)
public interface AgencyRepository extends JpaRepository<Agency, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<Agency> findAll(Specification<Agency> spec, Pageable pageable);

    /**
     * 根据Id查询list
     * @return
     */
    List<Agency> findAllById(List<Integer> ids);

    @Query(value = "select user_id from tb_agency where super_agent_id =?1",nativeQuery = true)
    List<BigInteger> findUserIdList(Long userId);

}