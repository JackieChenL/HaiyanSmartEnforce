package com.kas.clientservice.haiyansmartenforce.Entity;

import com.hikvision.vmsnetsdk.CNetSDKLog;

import java.io.Serializable;
import java.util.List;

public class Camera implements Serializable {
    private String id;
    private String name;
    private int type;
    private boolean isOnline;
    private List<Integer> userCapability;
    private int collectedFlag;
    private int groupID;
    private int cascadeFlag;
    private static final int ONLINE = 1;
    private static final int OFFLINE = 0;
    private static final String TAG = "Camera";
    public static final int COLLECTED_FLAG_ADDED = 1;
    public static final int COLLECTED_FLAG_UNADDED = 0;
    public static final int CAMERA_TYPE_UNKNOWN = -1;
    public static final int CAMERA_TYPE_BOX_CAMERA = 0;
    public static final int CAMERA_TYPE_DOME_CAMERA = 1;
    public static final int CAMERA_TYPE_FAST_CAMERA = 2;
    public static final int CAMERA_TYPE_PTZ_CAMERA = 3;
    private String pictureUrl;
    private double longitude;
    private double latitude;

    public Camera() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    public boolean setOnline(int isOnline) {
        if(isOnline == 1) {
            this.isOnline = true;
            return true;
        } else if(isOnline == 0) {
            this.isOnline = false;
            return true;
        } else {
            CNetSDKLog.e("Camera", "setOnline,isOnline can not be other value.");
            return false;
        }
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public List<Integer> getUserCapability() {
        return this.userCapability;
    }

    public void setUserCapability(List<Integer> userCapability) {
        this.userCapability = userCapability;
    }

    public int getCollectedFlag() {
        return this.collectedFlag;
    }

    public void setCollectedFlag(int collectedFlag) {
        this.collectedFlag = collectedFlag;
    }

    public int getGroupID() {
        return this.groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getCascadeFlag() {
        return this.cascadeFlag;
    }

    public void setCascadeFlag(int cascadeFlag) {
        this.cascadeFlag = cascadeFlag;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
