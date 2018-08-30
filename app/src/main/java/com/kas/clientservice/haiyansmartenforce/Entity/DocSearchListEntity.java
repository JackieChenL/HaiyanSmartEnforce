package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-08-29
 * 公司：COMS
 */

public class DocSearchListEntity {

    /**
     * State : true
     * ErrorCode : -1
     * ErrorMsg :
     * Rtn : [{"tn":1,"tc":0,"OfficeID":1110,"NumberOff":"W201704191","TitleOff":"综合治理委员会通知","NameEmp":"李建良","EntryTimeOff":"2017-04-19 16:26:23","NameOfF":"办公室归档","OfficeFlowID":32,"HostUserID":1297,"FlowID":32,"DepartmentID":41,"FirstLevelID":"-1","type":"外部文件"}]
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
         * tn : 1
         * tc : 0
         * OfficeID : 1110
         * NumberOff : W201704191
         * TitleOff : 综合治理委员会通知
         * NameEmp : 李建良
         * EntryTimeOff : 2017-04-19 16:26:23
         * NameOfF : 办公室归档
         * OfficeFlowID : 32
         * HostUserID : 1297
         * FlowID : 32
         * DepartmentID : 41
         * FirstLevelID : -1
         * type : 外部文件
         */

        private int tn;
        private int tc;
        private int OfficeID;
        private String NumberOff;
        private String TitleOff;
        private String NameEmp;
        private String EntryTimeOff;
        private String NameOfF;
        private int OfficeFlowID;
        private int HostUserID;
        private int FlowID;
        private int DepartmentID;
        private String FirstLevelID;
        private String type;

        public int getTn() {
            return tn;
        }

        public void setTn(int tn) {
            this.tn = tn;
        }

        public int getTc() {
            return tc;
        }

        public void setTc(int tc) {
            this.tc = tc;
        }

        public int getOfficeID() {
            return OfficeID;
        }

        public void setOfficeID(int OfficeID) {
            this.OfficeID = OfficeID;
        }

        public String getNumberOff() {
            return NumberOff;
        }

        public void setNumberOff(String NumberOff) {
            this.NumberOff = NumberOff;
        }

        public String getTitleOff() {
            return TitleOff;
        }

        public void setTitleOff(String TitleOff) {
            this.TitleOff = TitleOff;
        }

        public String getNameEmp() {
            return NameEmp;
        }

        public void setNameEmp(String NameEmp) {
            this.NameEmp = NameEmp;
        }

        public String getEntryTimeOff() {
            return EntryTimeOff;
        }

        public void setEntryTimeOff(String EntryTimeOff) {
            this.EntryTimeOff = EntryTimeOff;
        }

        public String getNameOfF() {
            return NameOfF;
        }

        public void setNameOfF(String NameOfF) {
            this.NameOfF = NameOfF;
        }

        public int getOfficeFlowID() {
            return OfficeFlowID;
        }

        public void setOfficeFlowID(int OfficeFlowID) {
            this.OfficeFlowID = OfficeFlowID;
        }

        public int getHostUserID() {
            return HostUserID;
        }

        public void setHostUserID(int HostUserID) {
            this.HostUserID = HostUserID;
        }

        public int getFlowID() {
            return FlowID;
        }

        public void setFlowID(int FlowID) {
            this.FlowID = FlowID;
        }

        public int getDepartmentID() {
            return DepartmentID;
        }

        public void setDepartmentID(int DepartmentID) {
            this.DepartmentID = DepartmentID;
        }

        public String getFirstLevelID() {
            return FirstLevelID;
        }

        public void setFirstLevelID(String FirstLevelID) {
            this.FirstLevelID = FirstLevelID;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
