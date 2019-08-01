package com.xiaohe.ysjspt.modules.agency.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.*;

import com.xiaohe.ysjspt.entity.SysUser;
import com.xiaohe.ysjspt.modules.sys.entity.SysUserEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 经销商表
 * </p>
 *
 * @author gmq
 * @since 2019-01-09
 */

@Entity
@Table(name = "tb_agency")
@DynamicInsert
@DynamicUpdate
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 上级代理ID
     */
    private Long superAgentId;

    /**
     * 扩展对象
     */
    @Transient
    private SysUserEntity sysUserEntity;

    public SysUserEntity getSysUserEntity() {
        return sysUserEntity;
    }

    public void setSysUserEntity(SysUserEntity sysUserEntity) {
        this.sysUserEntity = sysUserEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSuperAgentId() {
        return superAgentId;
    }

    public void setSuperAgentId(Long superAgentId) {
        this.superAgentId = superAgentId;
    }


}