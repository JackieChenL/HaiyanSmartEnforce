package com.kas.clientservice.haiyansmartenforce.User;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-03
 * 公司：COMS
 */

public class UserInfo {


    /**
     * ZFRYID : 3
     * type : 2
     * Road : [{"Berthname":"勤俭路--1号停车位","BerthID":"1"},{"Berthname":"勤俭路--22号停车位","BerthID":"2"},{"Berthname":"城北路--3号停车位","BerthID":"3"},{"Berthname":"城北路--4号停车位","BerthID":"4"},{"Berthname":"勤俭路--1","BerthID":"5"},{"Berthname":"勤俭路--2","BerthID":"6"}]
     * board : [{"url":"http://hywx.hnzhzf.top/xxhdpi/tingchejiaofei.jpg","typeid":1,"title":"停车收费"}]
     */

    public String ZFRYID;
    public String type;
    public List<RoadBean> Road;
    public List<BoardBean> board;

    public List<BoardBean> getBoard() {
        return board;
    }

    public List<RoadBean> getRoad() {
        return Road;
    }

    public String getType() {
        return type;
    }

    public String getZFRYID() {
        return ZFRYID;
    }

    public static class RoadBean {
        /**
         * Berthname : 勤俭路--1号停车位
         * BerthID : 1
         */

        public String Berthname;
        public String BerthID;

        public String getBerthID() {
            return BerthID;
        }

        public String getBerthname() {
            return Berthname;
        }
    }

    public static class BoardBean {
        /**
         * url : http://hywx.hnzhzf.top/xxhdpi/tingchejiaofei.jpg
         * typeid : 1
         * title : 停车收费
         */

        public String url;
        public int typeid;
        public String title;

        public String getTitle() {
            return title;
        }

        public int getTypeid() {
            return typeid;
        }

        public String getUrl() {
            return url;
        }
    }
}
