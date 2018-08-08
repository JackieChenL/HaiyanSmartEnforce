package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-08-01
 * 公司：COMS
 */

public class LedgerTypeEntity {

    /**
     * rows : [{"ID":1,"Text":"台账类型1","Status":1,"CreateTime":"2018-08-01T14:21:06.927","UpdateTime":"2018-08-01T14:21:06.927","CreateUserID":1,"NameEmp":null}]
     * msg : 成功
     * status : ok
     */

    private String msg;
    private String status;
    private List<RowsBean> rows;

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
        public RowsBean(String text,int ID) {
            this.ID = ID;
            Text = text;
        }

        /**
         * ID : 1
         * Text : 台账类型1
         * Status : 1
         * CreateTime : 2018-08-01T14:21:06.927
         * UpdateTime : 2018-08-01T14:21:06.927
         * CreateUserID : 1
         * NameEmp : null
         */

        private int ID;
        private String Text;
        private int Status;
        private String CreateTime;
        private String UpdateTime;
        private int CreateUserID;
        private Object NameEmp;

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

        public Object getNameEmp() {
            return NameEmp;
        }

        public void setNameEmp(Object NameEmp) {
            this.NameEmp = NameEmp;
        }

        @Override
        public String toString() {
            return Text;
        }
    }
}
