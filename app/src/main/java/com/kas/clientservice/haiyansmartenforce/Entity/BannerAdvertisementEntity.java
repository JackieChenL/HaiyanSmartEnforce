package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-06-22
 * 公司：COMS
 */

public class BannerAdvertisementEntity {

    /**
     * State : true
     * ErrorCode : 200
     * ErrorMsg : 成功
     * Page : 0
     * total : 2
     * Rtn : [{"ID":"1","LogoImg":"http://hywx.hnzhzf.top/MVersion/20180622033013@pocar_detailpic_1.jpg"},{"ID":"2","LogoImg":"http://hywx.hnzhzf.top/MVersion/20180622033156@indexbananer.jpg"}]
     */

    private String State;
    private String ErrorCode;
    private String ErrorMsg;
    private String Page;
    private String total;
    private List<RtnBean> Rtn;

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String Page) {
        this.Page = Page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RtnBean> getRtn() {
        return Rtn;
    }

    public void setRtn(List<RtnBean> Rtn) {
        this.Rtn = Rtn;
    }

    public static class RtnBean {
        /**
         * ID : 1
         * LogoImg : http://hywx.hnzhzf.top/MVersion/20180622033013@pocar_detailpic_1.jpg
         */

        private String ID;
        private String LogoImg;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getLogoImg() {
            return LogoImg;
        }

        public void setLogoImg(String LogoImg) {
            this.LogoImg = LogoImg;
        }
    }
}
