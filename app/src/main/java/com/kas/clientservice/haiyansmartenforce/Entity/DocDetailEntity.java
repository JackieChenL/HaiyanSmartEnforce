package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-08-29
 * 公司：COMS
 */

public class DocDetailEntity {

    /**
     * State : true
     * ErrorCode : -1
     * ErrorMsg :
     * Rtn : [{"NumberOff":"W201704191","ReferenceNumberOff":"执法局综治办（2017）第3期","EntryTimeOff":"2017-04-19 16:26:23","TitleOff":"综合治理委员会通知","ContentOff":"","UploadOff":"UploadOffice/63/1297/201704191622/通知各站所.docx|","RemarkOff":""}]
     * total : 1
     */

    private boolean State;
    private int ErrorCode;
    private String ErrorMsg;
    private int total;
    private List<RtnBean> Rtn;

    public boolean isState() {
        return State;
    }

    public void setState(boolean State) {
        this.State = State;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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
         * NumberOff : W201704191
         * ReferenceNumberOff : 执法局综治办（2017）第3期
         * EntryTimeOff : 2017-04-19 16:26:23
         * TitleOff : 综合治理委员会通知
         * ContentOff :
         * UploadOff : UploadOffice/63/1297/201704191622/通知各站所.docx|
         * RemarkOff :
         */

        private String NumberOff;
        private String ReferenceNumberOff;
        private String EntryTimeOff;
        private String TitleOff;
        private String ContentOff;
        private String UploadOff;
        private String RemarkOff;

        public String getNumberOff() {
            return NumberOff;
        }

        public void setNumberOff(String NumberOff) {
            this.NumberOff = NumberOff;
        }

        public String getReferenceNumberOff() {
            return ReferenceNumberOff;
        }

        public void setReferenceNumberOff(String ReferenceNumberOff) {
            this.ReferenceNumberOff = ReferenceNumberOff;
        }

        public String getEntryTimeOff() {
            return EntryTimeOff;
        }

        public void setEntryTimeOff(String EntryTimeOff) {
            this.EntryTimeOff = EntryTimeOff;
        }

        public String getTitleOff() {
            return TitleOff;
        }

        public void setTitleOff(String TitleOff) {
            this.TitleOff = TitleOff;
        }

        public String getContentOff() {
            return ContentOff;
        }

        public void setContentOff(String ContentOff) {
            this.ContentOff = ContentOff;
        }

        public String getUploadOff() {
            return UploadOff;
        }

        public void setUploadOff(String UploadOff) {
            this.UploadOff = UploadOff;
        }

        public String getRemarkOff() {
            return RemarkOff;
        }

        public void setRemarkOff(String RemarkOff) {
            this.RemarkOff = RemarkOff;
        }
    }
}
