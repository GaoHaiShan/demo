package model;

import java.util.Map;

public class PayPal implements IPay {

    private String id="";
    private String purchaseUnitReferenceId="";
    private String amount="";
    private String paymentMode="";
    private String state="";
    private String reasonCode="";
    private String protectionEligibility="";
    private String protectionEligibilityType="";
    private String parentPayment="";
    private String fmfDetails="";
    private String createTime="";
    private String updateTime="";
    private String links="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseUnitReferenceId() {
        return purchaseUnitReferenceId;
    }

    public void setPurchaseUnitReferenceId(String purchaseUnitReferenceId) {
        this.purchaseUnitReferenceId = purchaseUnitReferenceId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getProtectionEligibility() {
        return protectionEligibility;
    }

    public void setProtectionEligibility(String protectionEligibility) {
        this.protectionEligibility = protectionEligibility;
    }

    public String getProtectionEligibilityType() {
        return protectionEligibilityType;
    }

    public void setProtectionEligibilityType(String protectionEligibilityType) {
        this.protectionEligibilityType = protectionEligibilityType;
    }

    public String getParentPayment() {
        return parentPayment;
    }

    public void setParentPayment(String parentPayment) {
        this.parentPayment = parentPayment;
    }

    public String getFmfDetails() {
        return fmfDetails;
    }

    public void setFmfDetails(String fmfDetails) {
        this.fmfDetails = fmfDetails;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    @Override
    public void send(Map map) {

        System.out.println("请求跨境接口，发送数据包 ："+map.toString());
    }
}
