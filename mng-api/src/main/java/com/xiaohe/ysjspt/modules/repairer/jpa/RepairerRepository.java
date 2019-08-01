package com.xiaohe.ysjspt.modules.repairer.jpa;

import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * jpa 接口
 *
 * @author gmq
 * @since 2018-12-10
 */

@Transactional(rollbackFor = Exception.class)
public interface RepairerRepository extends JpaRepository<Repairer, Integer> {

    /**
     * 按条件查询方案
     *
     * @param spec     spec
     * @param pageable 分页
     * @return page
     */
    Page<Repairer> findAll(Specification<Repairer> spec, Pageable pageable);

    /**
     * 查询所有维修工用户名
     *
     * @return
     */
    @Query(value = "select DISTINCT login_name from tb_repairer", nativeQuery = true)
    List<String> findAllName();

    /**
     * 查询维修工
     *
     * @param name 账号
     * @param pwd  密码
     * @return
     */
    Repairer findByLoginNameAndPwd(String name, String pwd);

    /**
     * 查询商户的维修工
     *
     * @param integer
     * @return
     */
    List<Repairer> findAllByManagerId(Integer integer);

}