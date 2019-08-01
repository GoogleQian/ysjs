package com.xiaohe.ysjspt.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author
 * wuziqi
 */

@Entity
@Table(name="tb_change")
public class FilterChangeEntity implements Serializable {
//    private static final long serialVersionUID = 2446493944464378L;

    /***/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;


    private String deviceId;


    private String customer;


    private String phoneNumber;


    private int replaceFirstFilter;


    private int replaceSecondFilter;


    private int replaceThirdFilter;


    private int replaceFouthFilter;


    private String planReplaceTime="";


    private String realReplaceTime="";


    private String address;


    private Integer replaceFinshed;

    private String repairer;

    private String repairerPhoneNumber;

    private String remark;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer(){return customer;}
    public void setCustomer(String customer){this.customer = customer;}

    public String getDeviceId(){return deviceId;}
    public void setDeviceId(String deviceId){this.deviceId = deviceId;}

    public String getPhoneNumber(){return phoneNumber;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public int getReplaceFirstFilter(){return replaceFirstFilter;}
    public void setReplaceFirstFilter(int replaceFirstFilter){this.replaceFirstFilter = replaceFirstFilter;}

    public int getReplaceSecondFilter(){return replaceSecondFilter;}
    public void setReplaceSecondFilter(int replaceSecondFilter){this.replaceSecondFilter = replaceSecondFilter;}

    public int getReplaceThirdFilter(){return replaceThirdFilter;}
    public void setReplaceThirdFilter(int replaceThirdFilter){this.replaceThirdFilter = replaceThirdFilter;}

    public int getReplaceFouthFilter(){return replaceFouthFilter;}
    public void setReplaceFouthFilter(int replaceFouthFilter){this.replaceFouthFilter = replaceFouthFilter;}

    public String getPlanReplaceTime(){return planReplaceTime;}
    public void setPlanReplaceTime(String planReplaceTime){this.planReplaceTime = planReplaceTime;}

    public String getRealReplaceTime(){return realReplaceTime;}
    public void setRealReplaceTime(String realReplaceTime){this.realReplaceTime = realReplaceTime;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}

    public Integer getReplaceFinshed(){return replaceFinshed;}
    public void setReplaceFinshed(Integer replaceFinshed){this.replaceFinshed = replaceFinshed;}

    public String getRepairer(){return repairer;}
    public void setRepairer(String repairer){this.repairer = repairer;}

    public String getRemark(){return remark;}
    public void setRemark(String remark){this.remark = remark;}

    public String getRepairerPhoneNumber(){return repairerPhoneNumber;}
    public void setRepairerPhoneNumber(String repairerPhoneNumber){this.repairerPhoneNumber = repairerPhoneNumber;}


}
