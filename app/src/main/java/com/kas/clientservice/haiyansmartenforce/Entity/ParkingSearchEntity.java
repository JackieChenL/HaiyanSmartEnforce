package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-07
 * 公司：COMS
 */

public class ParkingSearchEntity {

    /**
     * ZFRYID : 4170
     * board : [{"ID":"1","CarNum":"浙B12345","WFtime":"2018-02-12 11:47:43","WFaddress":"中山东路","WFAddressZB":"5","State":"1","WFimg":[{"img":""}]},{"ID":"2","CarNum":"浙F12345","WFtime":"2018-02-09 13:47:43","WFaddress":"少年路","WFAddressZB":"4","State":"0","WFimg":[{"img":""}]}]
     */

    public String ZFRYID;
    public List<BoardBean> board;

    public List<BoardBean> getBoard() {
        return board;
    }

    public void setBoard(List<BoardBean> board) {
        this.board = board;
    }

    public String getZFRYID() {
        return ZFRYID;
    }

    public void setZFRYID(String ZFRYID) {
        this.ZFRYID = ZFRYID;
    }

    public static class BoardBean {
        /**
         * ID : 1
         * CarNum : 浙B12345
         * WFtime : 2018-02-12 11:47:43
         * WFaddress : 中山东路
         * WFAddressZB : 5
         * State : 1
         * WFimg : [{"img":""}]
         */

        public String ID;
        public String CarNum;
        public String WFtime;
        public String WFaddress;
        public String WFAddressZB;
        public String State;
        public List<WFimgBean> WFimg;

        public static class WFimgBean {
            /**
             * img :
             */

            public String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public String getCarNum() {
            return CarNum;
        }

        public void setCarNum(String carNum) {
            CarNum = carNum;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getWFaddress() {
            return WFaddress;
        }

        public void setWFaddress(String WFaddress) {
            this.WFaddress = WFaddress;
        }

        public String getWFAddressZB() {
            return WFAddressZB;
        }

        public void setWFAddressZB(String WFAddressZB) {
            this.WFAddressZB = WFAddressZB;
        }

        public List<WFimgBean> getWFimg() {
            return WFimg;
        }

        public void setWFimg(List<WFimgBean> WFimg) {
            this.WFimg = WFimg;
        }

        public String getWFtime() {
            return WFtime;
        }

        public void setWFtime(String WFtime) {
            this.WFtime = WFtime;
        }
    }
}
