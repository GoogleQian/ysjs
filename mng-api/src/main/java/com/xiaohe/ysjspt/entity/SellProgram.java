package com.xiaohe.ysjspt.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author wzq
 * @since 2018-08-28
 */

@Entity
@Table(name = "tb_sell_program")
public class SellProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 水量
     */
    @JsonProperty(value = "sell_amount")
    private Double sellAmount;
    /**
     * 金额
     */
    @JsonProperty(value = "sell_money")
    private Double sellMoney;
    /**
     * 售水类型(0:热水，1:冷水)
     */
    @JsonProperty(value = "sell_type")
    private Integer sellType;
    @JsonProperty(value = "create_time")
    private Date createTime;
    /**
     * 方案类型
     */
    @JsonProperty(value = "program_type")
    private String programType;
    /**
     * 商品名
     */
    @JsonProperty(value = "sell_name")
    private String sellName;
    /**
     * 售水温度
     */
    @JsonProperty(value = "water_temp")
    private Integer waterTemp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(Double sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Double getSellMoney() {
        return sellMoney;
    }

    public void setSellMoney(Double sellMoney) {
        this.sellMoney = sellMoney;
    }

    public Integer getSellType() {
        return sellType;
    }

    public void setSellType(Integer sellType) {
        this.sellType = sellType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public String getSellName() {
        return sellName;
    }

    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public Integer getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(Integer waterTemp) {
        this.waterTemp = waterTemp;
    }
}