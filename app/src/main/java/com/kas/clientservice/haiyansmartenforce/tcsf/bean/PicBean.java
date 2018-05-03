package com.kas.clientservice.haiyansmartenforce.tcsf.bean;


import android.graphics.Bitmap;

public class PicBean {
    private boolean isPicFromSm;
    private Bitmap bmp;


    public PicBean(boolean isPicFromSm, Bitmap bmp) {
        this.isPicFromSm = isPicFromSm;
        this.bmp = bmp;
    }

    public boolean isPicFromSm() {
        return isPicFromSm;
    }

    public void setPicFromSm(boolean picFromSm) {
        isPicFromSm = picFromSm;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
