package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-15
 * 公司：COMS
 */

public class HuanweiListEntity {

    /**
     * hoperid : 4440
     * board : [{"ID":"1","JCNR":"日常保洁","JCDD":"1","QKMS":"1","Shstate":"2"},{"ID":"2","JCNR":"环卫设施","JCDD":"2","QKMS":"2","Shstate":"3"},{"ID":"3","JCNR":"公共厕所","JCDD":"3","QKMS":"3","Shstate":"1"},{"ID":"4","JCNR":"垃圾房","JCDD":"4","QKMS":"4","Shstate":"1"},{"ID":"5","JCNR":"公共区域与村庄道路保洁","JCDD":"长平路","QKMS":"hx","Shstate":"1"},{"ID":"6","JCNR":"中转站","JCDD":"长宁路","QKMS":"shd","Shstate":"1"},{"ID":"7","JCNR":"日常保洁","JCDD":"长平路","QKMS":"xnxn","Shstate":"1"},{"ID":"8","JCNR":"日常保洁","JCDD":"1","QKMS":"1","Shstate":"1"},{"ID":"9","JCNR":"公共区域与村庄道路保洁","JCDD":"长邮路","QKMS":"shx","Shstate":"1"},{"ID":"10","JCNR":"垃圾房","JCDD":"长宁路","QKMS":"sz","Shstate":"1"}]
     */

    public String hoperid;
    public List<BoardBean> board;

    public List<BoardBean> getBoard() {
        return board;
    }

    public String getHoperid() {
        return hoperid;
    }

    public static class BoardBean {
        /**
         * ID : 1
         * JCNR : 日常保洁
         * JCDD : 1
         * QKMS : 1
         * Shstate : 2
         */

        public String ID;
        public String JCNR;
        public String JCDD;
        public String QKMS;
        public String Shstate;
        public String TOWNID;

        public String getTOWNID() {
            return TOWNID;
        }

        public void setTOWNID(String TOWNID) {
            this.TOWNID = TOWNID;
        }

        public String getID() {
            return ID;
        }

        public String getJCDD() {
            return JCDD;
        }

        public String getJCNR() {
            return JCNR;
        }

        public String getQKMS() {
            return QKMS;
        }

        public String getShstate() {
            return Shstate;
        }
    }
}
