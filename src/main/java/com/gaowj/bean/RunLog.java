package com.gaowj.bean;

/**
 * created by gaowj.
 * created on 2020-04-22.
 * function:
 */
public class RunLog {
    private String operation = null;
    private String province = null;
    private String city = null;
    private String netStatus = null;
    private String gv = null;
    private String os = null;
    private String publishid = null;
    private String loginid = null;
    private int pullNum = 0;
    private String userId = null;
    private int size = 0;
    private String reason = null;
    private String ref = null;
    private String kind = null;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getGv() {
        return gv;
    }

    public void setGv(String gv) {
        this.gv = gv;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getPublishid() {
        return publishid;
    }

    public void setPublishid(String publishid) {
        this.publishid = publishid;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public int getPullNum() {
        return pullNum;
    }

    public void setPullNum(int pullNum) {
        this.pullNum = pullNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "RunLog{" +
                "operation='" + operation + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", netStatus='" + netStatus + '\'' +
                ", gv='" + gv + '\'' +
                ", os='" + os + '\'' +
                ", publishid='" + publishid + '\'' +
                ", loginid='" + loginid + '\'' +
                ", pullNum=" + pullNum +
                ", userId='" + userId + '\'' +
                ", size=" + size +
                ", reason='" + reason + '\'' +
                ", ref='" + ref + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
}
