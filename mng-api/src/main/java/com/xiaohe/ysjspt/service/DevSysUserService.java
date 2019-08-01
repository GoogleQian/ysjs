package com.xiaohe.ysjspt.service;


import com.xiaohe.ysjspt.entity.SysUser;
import com.xiaohe.ysjspt.jpa.DevSysUserRepository;
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
public class DevSysUserService {

    @Autowired
    DevSysUserRepository devSysUserRepository;

    public SysUser loadUserByUsername(String username, String password, String brandname){
        List<SysUser> list= (List<SysUser>) devSysUserRepository.finduser(username, password,brandname);
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
        return  devSysUserRepository.getReadStatusById(userId);
    }

    public void setReadStatus(Long userId, Integer isRead){
        devSysUserRepository.setReadStatusById(userId, isRead);
    }

    public void setIsRead(Long userId, Integer isRead, Date latestReadTime){
        devSysUserRepository.setIsReadById(userId, isRead, latestReadTime);
    }

    public void saveToken(String username, String token){
        devSysUserRepository.setToken(username, token);
    }

    public String fineTokenByUsername(String username){
        return devSysUserRepository.findByName(username).getToken();
    }

    public SysUser fineUser(String username){
        return devSysUserRepository.findByName(username);
    }

    public void updatePwd(String userName, String newPassword) {
        devSysUserRepository.updatePwdByUserName(userName,newPassword);
    }

    public void save(SysUser sysUser) {
        devSysUserRepository.save(sysUser);
    }

    public void deleteById(Long id) {

        devSysUserRepository.delete(id);
    }

    public SysUser findBySubId(Integer agentname) {
        return devSysUserRepository.findTopBySubIdOrderByIdDesc(agentname);
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


        return  devSysUserRepository.findAll(spec,pageable);

    }

    public SysUser findOne(Long id) {
     return devSysUserRepository.findOne(id);
    }

    public List<SysUser> findByParentId(Integer parentId) {
         return devSysUserRepository.findByParentId(parentId);
    }

    public SysUser findByBrandname(String brandname) {
        return devSysUserRepository.findTopByBrandnameOrderByIdDesc(brandname);
    }

    public Integer findMostId() {
          return devSysUserRepository.findMaxId();
    }

    public List<SysUser> findAll(){
        return devSysUserRepository.findAll();
    }
}
