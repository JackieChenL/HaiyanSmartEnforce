package com.kas.clientservice.haiyansmartenforce.User;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-03
 * 公司：COMS
 */

public class UserInfo {

    /**
     * ZFRYID : 2
     * type : 2
     * board : [{"url":"http://hywx.hnzhzf.top/xxhdpi/tingchejiaofei.jpg","typeid":1,"title":"停车收费"}]
     */

    public String ZFRYID;
    public String type;
    public List<BoardBean> board;
    public String road;

    public String getRoad() {
        return road;
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

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public List<BoardBean> getBoard() {
        return board;
    }

    public void setBoard(List<BoardBean> board) {
        this.board = board;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZFRYID() {
        return ZFRYID;
    }

    public void setZFRYID(String ZFRYID) {
        this.ZFRYID = ZFRYID;
    }
}
