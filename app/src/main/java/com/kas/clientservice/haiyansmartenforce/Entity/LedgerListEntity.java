package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-08-02
 * 公司：COMS
 */

public class LedgerListEntity {

    /**
     * total : 7
     * rows : [{"ID":7,"Text":"","Phote":"","LedgerType":0,"AreaIDLed":1,"Status":1,"CreateTime":"2018-08-02T09:36:15.693","UpdateTime":"2018-08-02T09:36:15.693","CreateUserID":1290,"NameEmp":"查明峰","NameAre":"武原街道","LedgerTypeName":"台账类型1"},{"ID":6,"Text":"","Phote":"","LedgerType":0,"AreaIDLed":1,"Status":1,"CreateTime":"2018-08-02T09:36:14.773","UpdateTime":"2018-08-02T09:36:14.773","CreateUserID":1290,"NameEmp":"查明峰","NameAre":"武原街道","LedgerTypeName":"台账类型1"},{"ID":5,"Text":"","Phote":"","LedgerType":0,"AreaIDLed":1,"Status":1,"CreateTime":"2018-08-02T09:36:13.93","UpdateTime":"2018-08-02T09:36:13.93","CreateUserID":1290,"NameEmp":"查明峰","NameAre":"武原街道","LedgerTypeName":"台账类型1"},{"ID":4,"Text":"","Phote":"","LedgerType":0,"AreaIDLed":1,"Status":1,"CreateTime":"2018-08-02T09:36:13.057","UpdateTime":"2018-08-02T09:36:13.057","CreateUserID":1290,"NameEmp":"查明峰","NameAre":"武原街道","LedgerTypeName":"台账类型1"},{"ID":3,"Text":"","Phote":"","LedgerType":0,"AreaIDLed":1,"Status":1,"CreateTime":"2018-08-02T09:36:12.09","UpdateTime":"2018-08-02T09:36:12.09","CreateUserID":1290,"NameEmp":"查明峰","NameAre":"武原街道","LedgerTypeName":"台账类型1"},{"ID":2,"Text":"","Phote":"","LedgerType":0,"AreaIDLed":1,"Status":1,"CreateTime":"2018-08-02T09:36:10.263","UpdateTime":"2018-08-02T09:36:10.263","CreateUserID":1290,"NameEmp":"查明峰","NameAre":"武原街道","LedgerTypeName":"台账类型1"},{"ID":1,"Text":"","Phote":"","LedgerType":0,"AreaIDLed":1,"Status":1,"CreateTime":"2018-08-02T09:28:08.77","UpdateTime":"2018-08-02T09:28:08.77","CreateUserID":1290,"NameEmp":"查明峰","NameAre":"秦山街道","LedgerTypeName":"台账类型1"}]
     * msg : 成功
     * status : ok
     */

    private int total;
    private String msg;
    private String status;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * ID : 7
         * Text :
         * Phote :
         * LedgerType : 0
         * AreaIDLed : 1
         * Status : 1
         * CreateTime : 2018-08-02T09:36:15.693
         * UpdateTime : 2018-08-02T09:36:15.693
         * CreateUserID : 1290
         * NameEmp : 查明峰
         * NameAre : 武原街道
         * LedgerTypeName : 台账类型1
         */

        private int ID;
        private String Text;
        private String Phote;
        private int LedgerType;
        private int AreaIDLed;
        private int Status;
        private String CreateTime;
        private String UpdateTime;
        private int CreateUserID;
        private String NameEmp;
        private String NameAre;
        private String LedgerTypeName;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getText() {
            return Text;
        }

        public void setText(String Text) {
            this.Text = Text;
        }

        public String getPhote() {
            return Phote;
        }

        public void setPhote(String Phote) {
            this.Phote = Phote;
        }

        public int getLedgerType() {
            return LedgerType;
        }

        public void setLedgerType(int LedgerType) {
            this.LedgerType = LedgerType;
        }

        public int getAreaIDLed() {
            return AreaIDLed;
        }

        public void setAreaIDLed(int AreaIDLed) {
            this.AreaIDLed = AreaIDLed;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public int getCreateUserID() {
            return CreateUserID;
        }

        public void setCreateUserID(int CreateUserID) {
            this.CreateUserID = CreateUserID;
        }

        public String getNameEmp() {
            return NameEmp;
        }

        public void setNameEmp(String NameEmp) {
            this.NameEmp = NameEmp;
        }

        public String getNameAre() {
            return NameAre;
        }

        public void setNameAre(String NameAre) {
            this.NameAre = NameAre;
        }

        public String getLedgerTypeName() {
            return LedgerTypeName;
        }

        public void setLedgerTypeName(String LedgerTypeName) {
            this.LedgerTypeName = LedgerTypeName;
        }
    }
}
