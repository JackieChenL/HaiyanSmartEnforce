package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-16
 * 公司：COMS
 */

public class HuanweiCheckDetailEntity {


    /**
     * hoperid : 4440
     * ID : 3
     * Opername : jcry
     * XM : 集镇、工业园区保洁
     * JCNR : 公共厕所
     * JCDD : 3
     * JCDDZB :
     * QKMS : 3
     * starttime : 2018-05-13 17:33
     * Img : [{"img":"http://hywx.hnzhzf.top/MVersion/201805131733390.jpg"}]
     * Shstate : 1
     * town : [{"town":"武原街道","townid":"1"},{"town":"秦山街道","townid":"2"},{"town":"元通街道","townid":"3"},{"town":"西塘桥街道","townid":"4"},{"town":"沈荡镇","townid":"5"},{"town":"百步镇","townid":"6"},{"town":"于城镇","townid":"7"},{"town":"澉浦镇","townid":"8"},{"town":"通元镇","townid":"9"}]
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
    public String town;
    public String townid;
    public String Checktown;
    public String Checktownid;
    public String DeScore;
    public List<ImgBean> Img;
    public List<TownBean> townlist;

    public String getTownid() {
        return townid;
    }

    public String getChecktown() {
        return Checktown;
    }

    public String getChecktownid() {
        return Checktownid;
    }

    public String getDeScore() {
        return DeScore;
    }

    public List<TownBean> getTownlist() {
        return townlist;
    }

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

    public List<TownBean> getTown() {
        return townlist;
    }

    public String getXM() {
        return XM;
    }

    public static class ImgBean {
        /**
         * img : http://hywx.hnzhzf.top/MVersion/201805131733390.jpg
         */

        public String img;

        public String getImg() {
            return img;
        }
    }

    public static class TownBean {
        /**
         * town : 武原街道
         * townid : 1
         */

        public String town;
        public String townid;

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getTownid() {
            return townid;
        }

        public void setTownid(String townid) {
            this.townid = townid;
        }
    }
}
