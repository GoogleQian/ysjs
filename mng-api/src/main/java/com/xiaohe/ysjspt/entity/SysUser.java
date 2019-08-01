package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * @author
 * Administrator
 */
@Entity
@Table(name="tb_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("username")
    String name;

    @JsonProperty("password")
    String pwd;

    @JsonIgnore
    String token;

    /*@JsonIgnore
    private boolean enabled;*/

    @JsonProperty("parent_id")
    private Integer parentId;

    @JsonProperty("sub_id")
    private Integer subId;

    @JsonProperty("brandname")
    private String brandname;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

/*    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", password='" + pwd + '\'' +
                ", token='" + token + '\'' +
               /* ", enabled=" + enabled +*/
                '}';
    }
}
