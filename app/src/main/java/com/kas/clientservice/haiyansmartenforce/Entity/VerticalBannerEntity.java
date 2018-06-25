package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-06-25
 * 公司：COMS
 */

public class VerticalBannerEntity {

    /**
     * State : true
     * ErrorCode : 200
     * ErrorMsg : 成功
     * Page : 0
     * total : 1
     * Rtn : [{"ID":"1","Title":"通知","Content":"通知测试","ContentImg":[{"img":"http://hywx.hnzhzf.top/MVersion/20180622044029@pocar_green.png"},{"img":"http://hywx.hnzhzf.top/MVersion/20180622044029@pocar_red.png"}],"Addtime":"2018/6/22 16:40:29","operrealname":"moresec"}]
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
         * Title : 通知
         * Content : 通知测试
         * ContentImg : [{"img":"http://hywx.hnzhzf.top/MVersion/20180622044029@pocar_green.png"},{"img":"http://hywx.hnzhzf.top/MVersion/20180622044029@pocar_red.png"}]
         * Addtime : 2018/6/22 16:40:29
         * operrealname : moresec
         */

        private String ID;
        private String Title;
        private String Content;
        private String Addtime;
        private String operrealname;
        private List<ContentImgBean> ContentImg;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getAddtime() {
            return Addtime;
        }

        public void setAddtime(String Addtime) {
            this.Addtime = Addtime;
        }

        public String getOperrealname() {
            return operrealname;
        }

        public void setOperrealname(String operrealname) {
            this.operrealname = operrealname;
        }

        public List<ContentImgBean> getContentImg() {
            return ContentImg;
        }

        public void setContentImg(List<ContentImgBean> ContentImg) {
            this.ContentImg = ContentImg;
        }

        public static class ContentImgBean {
            /**
             * img : http://hywx.hnzhzf.top/MVersion/20180622044029@pocar_green.png
             */

            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
