package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gmq
 * @since 2018-11-23
 */

@Entity
@Table(name="tb_adv_rich")
public class AdvRich implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户ID
     */
    @JsonProperty(value = "user_id")
    private Integer userId;
    /**
     * 图片路径
     */
    @JsonProperty(value = "img_url")
    private String imgUrl;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public String getImgUrl() {
      return imgUrl;
   }

   public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
   }


}