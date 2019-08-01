package com.xiaohe.ysjspt.modules.repairer.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author gmq
 * @since 2018-12-10
 */


public class RepairerVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 管理员ID
     */
    private Integer managerId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户名
     */
    private String loginName;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * token
     */
    @Transient
    private String token;

    @Transient
    private String devNo;
    @Transient
    private Integer page = 1;
    @Transient
    private Integer pageSize = 10;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDevNo() {
        return devNo;
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo;
    }
}