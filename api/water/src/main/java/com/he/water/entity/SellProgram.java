package com.he.water.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wzq
 * @since 2018-08-28
 */
public class SellProgram {

    private Integer id;
    /**
     * 水量
     */
    @JsonProperty(value = "sell_amount")
    @NotNull(message = "售水量不能为空")
    private Integer sellAmount;
    /**
     * 金额
     */
    @JsonProperty(value = "sell_money")
    @NotNull(message = "售水金额不能为空")
    private Integer sellMoney;
    /**
     * 售水类型(1热水、2温水、3直饮水、4冰水)
     */
    @JsonProperty(value = "sell_type")
    @NotNull(message = "售水类型不能为空")
    private Integer sellType;
    @JsonProperty(value = "create_time")
    private Date createTime;
    /**
     * 方案类型  （直饮水）
     */
    @JsonProperty(value = "program_type")
    @NotNull(message = "方案类型不能为空")
    private String programType;
    /**
     * 商品名
     */
    @JsonProperty(value = "sell_name")
    @NotBlank(message = "商品名不能为空")
    private String sellName;
    /**
     * 售水温度
     */
    @JsonProperty(value = "water_temp")
    private Integer waterTemp;

    public Integer getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(Integer waterTemp) {
        this.waterTemp = waterTemp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(Integer sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Integer getSellMoney() {
        return sellMoney;
    }

    public void setSellMoney(Integer sellMoney) {
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
}