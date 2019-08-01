package com.xiaohe.entity;

import java.io.Serializable;
import java.util.Date;


public class PassageEntitys implements Serializable{
	 private static final long serialVersionUID = 1L;
	
	    private Integer id;
	    
	    /**
	     * 设备ID
	     */	   
	    private Long devId;
	    
	    /**
	     * 商家名称
	     */	   
	    private String merchantName;
	    
	    /**
	     * 通道类型(1：开水，2：热水，3,：常温水，4：冷水)
	     */	   
	    private Integer passageType;
	    
	    /**
	     * 通道名称
	     */	    
	    private String passageName;
	    
	    /**
	     * 通道号
	     */	    
	    private Integer passageNo;
	    
	    /**
	     * 脉冲数
	     */	    
	    private Integer pulseNum;
	   
	    private Integer soluId;
	    
	    /**
	     * 费用设置（元/L）
	     */	  
	    private Integer price;
	    
	    /**
	     * 费用设置（元/L）
	     */	    
	    private Integer amount;
	   
	    private Date createTime;
	   
	    private String strPrice;


	   public Integer getId() {
	      return id;
	   }

	   public void setId(Integer id) {
	      this.id = id;
	   }

	   public String getMerchantName() {
	      return merchantName;
	   }

	   public void setMerchantName(String merchantName) {
	      this.merchantName = merchantName;
	   }

	   public Integer getPassageType() {
	      return passageType;
	   }

	   public void setPassageType(Integer passageType) {
	      this.passageType = passageType;
	   }

	   public String getPassageName() {
	      return passageName;
	   }

	   public void setPassageName(String passageName) {
	      this.passageName = passageName;
	   }

	   public Integer getPassageNo() {
	      return passageNo;
	   }

	   public void setPassageNo(Integer passageNo) {
	      this.passageNo = passageNo;
	   }

	   public Integer getPrice() {
	      return price;
	   }

	   public void setPrice(Integer price) {
	      this.price = price;
	   }

	   public Date getCreateTime() {
	      return createTime;
	   }

	   public void setCreateTime(Date createTime) {
	      this.createTime = createTime;
	   }


	    public Long getDevId() {
	        return devId;
	    }

	    public void setDevId(Long devId) {
	        this.devId = devId;
	    }

	    public Integer getPulseNum() {
	        return pulseNum;
	    }

	    public void setPulseNum(Integer pulseNum) {
	        this.pulseNum = pulseNum;
	    }

	    public Integer getSoluId() {
	        return soluId;
	    }

	    public void setSoluId(Integer soluId) {
	        this.soluId = soluId;
	    }

	    public Integer getAmount() {
	        return amount;
	    }

	    public void setAmount(Integer amount) {
	        this.amount = amount;
	    }

	    public String getStrPrice() {
	        return strPrice;
	    }

	    public void setStrPrice(String strPrice) {
	        this.strPrice = strPrice;
	    }
}
