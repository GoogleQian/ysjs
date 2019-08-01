package com.xiaohe.ysjspt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xiaohe.ysjspt.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 广告表
 * </p>
 *
 * @author wzq
 * @since 2018-11-19
 */

@Entity
@Table(name="tb_advertising")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class AdvertisingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 名称
     */
    @JsonProperty(value = "name")
    private String title;
    /**
     * 图片URL
     */
    @JsonProperty(value = "img_url")
    private String imgUrl;
    /**
     * 上传时间
     */
    @JsonProperty(value = "upload_time")
    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date uploadTime;
    /**
     * 商户ID
     */
    @JsonProperty(value = "user_id")
    private Long userId;
    /**
     * 链接地址
     */
    @JsonProperty(value = "url")
    private String linkUrl;
    /**
     * Hmtl字符串
     */
    @JsonProperty(value = "content")
    private String content;

    /**
     *1使用外链 0自定义
     */
    @JsonProperty(value = "use_hyper_link")
    private Integer useHyperLink;

    public AdvertisingEntity() {
    }

    public AdvertisingEntity(String title, Long userId, String linkUrl, String content,Integer useHyperLink) {
        this.title = title;
        this.userId = userId;
        this.linkUrl = linkUrl;
        this.content = content;
        this.useHyperLink = useHyperLink;
    }

    public Integer getUseHyperLink() {
        return useHyperLink;
    }

    public void setUseHyperLink(Integer useHyperLink) {
        this.useHyperLink = useHyperLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

}
