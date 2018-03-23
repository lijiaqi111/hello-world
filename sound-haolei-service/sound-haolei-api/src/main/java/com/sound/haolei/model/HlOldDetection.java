package com.sound.haolei.model;

import java.math.BigDecimal;
import java.util.Date;

public class HlOldDetection extends BaseModel{
    private Integer id;

    private String inspectionNo;

    private String orderNo;

    private Date inspectionDate;

    private BigDecimal settlePrice;

    private String productAttrs;

    private String partnerId;

    private String sign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInspectionNo() {
        return inspectionNo;
    }

    public void setInspectionNo(String inspectionNo) {
        this.inspectionNo = inspectionNo == null ? null : inspectionNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public BigDecimal getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(BigDecimal settlePrice) {
        this.settlePrice = settlePrice;
    }

    public String getProductAttrs() {
        return productAttrs;
    }

    public void setProductAttrs(String productAttrs) {
        this.productAttrs = productAttrs == null ? null : productAttrs.trim();
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId == null ? null : partnerId.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }
}