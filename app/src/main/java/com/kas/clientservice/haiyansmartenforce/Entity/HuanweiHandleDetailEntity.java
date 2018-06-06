package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-17
 * 公司：COMS
 */

public class HuanweiHandleDetailEntity {

    /**
     * hoperid : 4439
     * ID : 1
     * Opername : jcry
     * XM : 集镇、工业园区保洁
     * JCNR : 日常保洁
     * JCDD : 1
     * JCDDZB :
     * QKMS : 测试
     * starttime : 2018-05-15 11:04
     * Img : [{"img":"http://hywx.hnzhzf.top/MVersion/201805151104550.jpg"}]
     * Shstate : 7
     */

    public String hoperid;
    public String ID;
    public String Opername;
    public String XM;
    public String JCNR;
    public String JCDD;
    public String JCDDZB;
    public String QKMS;
    public String starttime;
    public String Shstate;
    public List<ImgBean> Img;

    public String getHoperid() {
        return hoperid;
    }

    public String getID() {
        return ID;
    }

    public List<ImgBean> getImg() {
        return Img;
    }

    public String getJCDD() {
        return JCDD;
    }

    public String getJCDDZB() {
        return JCDDZB;
    }

    public String getJCNR() {
        return JCNR;
    }

    public String getOpername() {
        return Opername;
    }

    public String getQKMS() {
        return QKMS;
    }

    public String getShstate() {
        return Shstate;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getXM() {
        return XM;
    }

    public static class ImgBean {
        /**
         * img : http://hywx.hnzhzf.top/MVersion/201805151104550.jpg
         */

        public String img;

        public String getImg() {
            return img;
        }
    }
}
