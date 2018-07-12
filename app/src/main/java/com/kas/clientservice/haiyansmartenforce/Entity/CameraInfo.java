package com.kas.clientservice.haiyansmartenforce.Entity;

import com.hikvision.vmsnetsdk.netLayer.msp.cameraInfo.CameraDetailInfo;

import java.util.ArrayList;
import java.util.List;

public class CameraInfo extends Camera {
    private boolean isPTZControl;
    private String deviceID;
    private int channelNo;
    private List<Integer> recordPos = new ArrayList();
    private String acsIP;
    private int acsPort;
    private String deviceIndexCode;
    private int audioEncodeType;
    private String guid;

    public CameraInfo() {
    }

    public String getDeviceID() {
        return this.deviceID;
    }

    public List<Integer> getRecordPos() {
        return this.recordPos;
    }

    public void setRecordPos(List<Integer> recordPos) {
        this.recordPos = recordPos;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public int getChannelNo() {
        return this.channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public void setCameraInfo(Camera camera) {
        this.setCascadeFlag(camera.getCascadeFlag());
        this.setCollectedFlag(camera.getCollectedFlag());
        this.setGroupID(camera.getGroupID());
        this.setId(camera.getId());
        this.setName(camera.getName());
        this.setOnline(camera.isOnline());
        this.setType(camera.getType());
        this.setUserCapability(camera.getUserCapability());
        this.recordPos.add(Integer.valueOf(2));
        if(camera.getUserCapability() != null) {
            this.isPTZControl = camera.getUserCapability().contains(CameraDetailInfo.USER_CAPABILITY_PTZ_CONTROL);
        }

        this.deviceID = null;
        this.channelNo = 0;
        this.acsIP = null;
        this.acsPort = 0;
    }

    public boolean isPTZControl() {
        return this.isPTZControl;
    }

    public void setPTZControl(boolean isPTZControl) {
        this.isPTZControl = isPTZControl;
    }

    public String getAcsIP() {
        return this.acsIP;
    }

    public void setAcsIP(String acsIP) {
        this.acsIP = acsIP;
    }

    public int getAcsPort() {
        return this.acsPort;
    }

    public void setAcsPort(int acsPort) {
        this.acsPort = acsPort;
    }

    public String getDeviceIndexCode() {
        return this.deviceIndexCode;
    }

    public void setDeviceIndexCode(String deviceIndexCode) {
        this.deviceIndexCode = deviceIndexCode;
    }

    public int getAudioEncodeType() {
        return this.audioEncodeType;
    }

    public void setAudioEncodeType(int audioEncodeType) {
        this.audioEncodeType = audioEncodeType;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return this.guid;
    }
}