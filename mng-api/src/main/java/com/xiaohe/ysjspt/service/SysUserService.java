package com.xiaohe.ysjspt.service;


import com.xiaohe.ysjspt.entity.SysUser;
import com.xiaohe.ysjspt.jpa.SysUserRepository;
import com.xiaohe.ysjspt.modules.sys.dao.SysUserDao;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
/**
 * @author
 * Administrator
 */
@Service
public class SysUserService {

    @Autowired
    SysUserRepository sysUserRepository;
    @Autowired
    private SysUserDao sysUserDao;

    public SysUser loadUserByUsername(String username, String password, String brandname){
        List<SysUser> list= (List<SysUser>) sysUserRepository.finduser(username, password,brandname);
        if(list!=null&&list.size()!=0){
            return list.get(0);
        }
        return null;
    }

    /*
    public void updatePwd(String username, String password){
        sysUserRepository.updatePwd(username, password);
    }
    */

    public Integer getReadStatus(Long userId){
        return  sysUserRepository.getReadStatusById(userId);
    }

    public void setReadStatus(Long userId, Integer isRead){
        sysUserRepository.setReadStatusById(userId, isRead);
    }

    public void setIsRead(Long userId, Integer isRead, Date latestReadTime){
        sysUserRepository.setIsReadById(userId, isRead, latestReadTime);
    }

    public void saveToken(String username, String token){
        sysUserRepository.setToken(username, token);
    }

    public String fineTokenByUsername(String username){
        return sysUserRepository.findByName(username).getToken();
    }

    public SysUser fineUser(String username){
        return sysUserRepository.findByName(username);
    }

    public void updatePwd(String userName, String newPassword) {
        sysUserRepository.updatePwdByUserName(userName,newPassword);
    }

    public void save(SysUser sysUser) {
      sysUserRepository.save(sysUser);
    }

    public void deleteById(Long id) {

     sysUserRepository.delete(id);
    }

    public SysUser findBySubId(Integer agentname) {
        return sysUserRepository.findTopBySubIdOrderByIdDesc(agentname);
    }

    public Page<SysUser> findAll(Integer page, Integer pageSize) {

        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        Specification<SysUser> spec = new Specification<SysUser>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Integer> parentId = root.get("parentId");
                Path<Integer> subId = root.get("subId");
                Predicate p = cb.equal(parentId, subId);
                return p;
            }
        };


        return  sysUserRepository.findAll(spec,pageable);

    }

    public SysUser findOne(Long id) {
     return sysUserRepository.findOne(id);
    }

    public List<SysUser> findByParentId(Integer parentId) {
         return sysUserRepository.findByParentId(parentId);
    }

    public SysUser findByBrandname(String brandname) {
        return sysUserRepository.findTopByBrandnameOrderByIdDesc(brandname);
    }

    public Integer findMostId() {
          return sysUserRepository.findMaxId();
    }


}
