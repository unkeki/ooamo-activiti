package com.ooamo.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ooamo.common.annotation.Excel;
import com.ooamo.common.core.domain.BaseEntity;

/**
 * 采购对象 purchase
 */
public class Purchase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    @Excel(name = "物品列表")
    private Long formId;

    /**  */
    @Excel(name = "总价")
    private String total;

    /**  */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applytime;

    /**  */
    @Excel(name = "申请人")
    private String applyer;

    // 采购经理
    private String purchasemanager;

    // 财务
    private String finance;

    // 出纳
    private String pay;

    // 总经理
    private String manager;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFormId(Long formId)
    {
        this.formId = formId;
    }

    public Long getFormId()
    {
        return formId;
    }
    public void setTotal(String total) 
    {
        this.total = total;
    }

    public String getTotal() 
    {
        return total;
    }
    public void setApplytime(Date applytime) 
    {
        this.applytime = applytime;
    }

    public Date getApplytime() 
    {
        return applytime;
    }
    public void setApplyer(String applyer) 
    {
        this.applyer = applyer;
    }

    public String getApplyer() 
    {
        return applyer;
    }

    public String getPurchasemanager() {
        return purchasemanager;
    }

    public void setPurchasemanager(String purchasemanager) {
        this.purchasemanager = purchasemanager;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("formId", getFormId())
            .append("total", getTotal())
            .append("applytime", getApplytime())
            .append("applyer", getApplyer())
            .toString();
    }
}
