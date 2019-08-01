package com.xiaohe.ysjspt.jpa;



import com.xiaohe.ysjspt.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author
 * wzq
 */

public interface DevSysUserRepository extends JpaRepository<SysUser,Long> {
   /*
   @Modifying
   @Query("update sys_user n set n.username =:username , n.password=:password")
   void updatePwd(@Param("username")String username, @Param("password") String password);
   */

   @Query(value = "select warning_read from sys_user t where t.id = :user_id", nativeQuery = true)
   Integer getReadStatusById(@Param("user_id") Long userId);


   @Modifying
   @Query(value = "update tb_user t SET t.warning_read = :warning_read where t.id = :user_id", nativeQuery = true)
   void setReadStatusById(@Param("user_id") Long userId, @Param("warning_read") Integer isRead);


   @Modifying
   @Query(value = "update tb_user t SET t.warning_read = :warning_read, t.latest_read_time = :latest_read_time where t.id = :user_id", nativeQuery = true)
   void setIsReadById(@Param("user_id") Long userId, @Param("warning_read") Integer isRead, @Param("latest_read_time") Date latestReadTime);

   SysUser findByName(String username);

   @Transactional(rollbackFor = { RuntimeException.class })
   @Modifying
   @Query(value = "update tb_user t SET t.token = :token where t.name = :name", nativeQuery = true)
   void setToken(@Param("name") String username, @Param("token") String token);


   @Modifying
   @Query(value = "update tb_user t  SET t.pwd = :pwd where t.name = :name", nativeQuery = true)
   void updatePwdByUserName(@Param("name") String userName, @Param("pwd") String password);

   SysUser findTopBySubIdOrderByIdDesc(Integer agentname);

   Page<SysUser> findAll(Specification<SysUser> spec, Pageable pageable);

   @Query(value = "select * from tb_user t where t.brandname=:brandname and t.name = :name and t.pwd=:pwd", nativeQuery = true)
   List<SysUser> finduser(@Param("name") String username, @Param("pwd") String password, @Param("brandname") String brandname);


   @Query(value = "select * from tb_user t where t.parent_id=:parentId", nativeQuery = true)
   List<SysUser> findByParentId(@Param("parentId")Integer parentId);

   SysUser findTopByBrandnameOrderByIdDesc(String brandname);

   @Query(value = "select MAX(id) from tb_user", nativeQuery = true)
   Integer findMaxId();
}
