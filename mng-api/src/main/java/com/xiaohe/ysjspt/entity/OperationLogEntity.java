package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
/**
 * @author
 * wzq
 */
@Entity
@Table(name="tb_operation_log")

public class OperationLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value="id")
    private Long id;

    @JsonProperty("user_name")
    String userName;

    @JsonProperty("description")
    String description;

    @JsonProperty("param")
    String param;

    @JsonProperty("creat_date")
    String creatDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }
}
