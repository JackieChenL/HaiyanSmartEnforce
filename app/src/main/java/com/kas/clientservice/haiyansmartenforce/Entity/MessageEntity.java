package com.kas.clientservice.haiyansmartenforce.Entity;

/**
 * 描述：
 * 时间：2018-06-22
 * 公司：COMS
 */

public class MessageEntity {
    //{"addSerial":"111","apId":"301583","content":”你好","ecName":"集团客户”,"mac":"e8c5c17a57182ea3aa27833b9b1a001f","mobiles":"18137828983","secretKey":"301583","sign":"lsign001"}
    private String ecName;
    private String apId;
    private String secretKey;
    private String mobiles;
    private String content;
    private String sign;
    private String addSerial;
    private String mac;

    public MessageEntity(String ecName, String apId, String secretKey, String mobiles, String content, String sigh, String addSerial, String mac) {
        this.ecName = ecName;
        this.apId = apId;
        this.secretKey = secretKey;
        this.mobiles = mobiles;
        this.content = content;
        this.sign = sigh;
        this.addSerial = addSerial;
        this.mac = mac;
    }

    public String getEcName() {
        return ecName;
    }

    public void setEcName(String ecName) {
        this.ecName = ecName;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSigh() {
        return sign;
    }

    public void setSigh(String sigh) {
        this.sign = sigh;
    }

    public String getAddSerial() {
        return addSerial;
    }

    public void setAddSerial(String addSerial) {
        this.addSerial = addSerial;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
