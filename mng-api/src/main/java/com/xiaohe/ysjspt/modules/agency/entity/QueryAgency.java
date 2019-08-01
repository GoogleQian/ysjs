package com.xiaohe.ysjspt.modules.agency.entity;

import java.io.Serializable;

/**
 * <p>
 * 经销商表
 * </p>
 *
 * @author gmq
 * @since 2019-01-09
 */


public class QueryAgency implements Serializable {


    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 上级代理ID
     */
    private Long superAgentId;

    private Integer page = 1;

    private Integer pageSize = 10;

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