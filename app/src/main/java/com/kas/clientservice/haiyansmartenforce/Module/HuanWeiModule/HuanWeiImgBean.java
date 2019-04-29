package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;


import android.graphics.Bitmap;

import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;

import smartenforce.util.ImgUtil;

public class HuanWeiImgBean {

    private Bitmap imageBmp;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String filePath;
    private String time;

    public String getBase64Bmp() {
        return base64Bmp;
    }

    private String base64Bmp;

    public HuanWeiImgBean(Bitmap imageBmp, String filePath) {
        this.imageBmp = imageBmp;
        this.filePath = filePath;
        this.time = TimeUtils.getFormedTime("MM-dd HH:mm:ss");
        this.base64Bmp = ImgUtil.bitmapToBase64(imageBmp);
    }

    public Bitmap getImageBmp() {
        return imageBmp;
    }

    public void setImageBmp(Bitmap imageBmp) {
        this.imageBmp = imageBmp;
    }



    public String getTime() {
        return time;
    }



    public void setTime(String time) {
        this.time = time;
    }
}
