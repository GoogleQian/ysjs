package com.xiaohe.ysjspt.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.io.Serializable;
/**
 * @author
 * wzq
 */
@Entity
@Table(name="tb_filter")
public class FilterEntity implements Serializable {
    private static final long serialVersionUID = 2446493944464368L;
    /***/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value="id")
    private Long id;

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("firstFilter")
    private String firstFilter;

    @JsonProperty("secondFilter")
    private String secondFilter;

    @JsonProperty("thirdFilter")
    private String thirdFilter;

    @JsonProperty("fourthFilter")
    private String fourthFilter;

    @JsonProperty("cust")
    private String cust;

    @JsonProperty("address")
    private String address;

    @JsonProperty("tel")
    private String tel;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId(){return deviceId;}
    public void setDeviceId(String deviceId){this.deviceId = deviceId;}

    public String getFirstFilter(){return firstFilter;}
    public void setFirstFilter(String firstFilter){this.firstFilter = firstFilter;}

    public String getSecondFilter(){return secondFilter;}
    public void setSecondFilter(String secondFilter){this.secondFilter = secondFilter;}

    public String getThirdFilter(){return thirdFilter;}
    public void setThirdFilter(String thirdFilter){this.thirdFilter = thirdFilter;}

    public String getFourthFilter(){return fourthFilter;}
    public void setFourthFilter(String fourthFilter){this.fourthFilter = fourthFilter;}

    public String getCust(){return cust;}
    public void setCust(String cust){this.cust = cust;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}

    public String getTel(){return tel;}
    public void setTel(String tel){this.tel = tel;}


}
