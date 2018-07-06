package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import java.util.List;

/**
 * Created by DELL_Zjcoms02 on 2018/6/25.
 */

public class Project_Info {

    /**
     * State : true
     * ErrorCode : null
     * ErrorMsg : null
     * Page : null
     * Rtn : [{"projcode":37017,"probdesc":"砌筑围墙垃圾乱推，破坏现场绿化。","address":"谢家路（金汇奥园南苑东面）","projname":"海盐数管【2018】第37017号","startdate":"2018-06-27 09:05:25","doTime":null,"state":null},{"projcode":36995,"probdesc":"dxx","address":"ddd","projname":"海盐数管【2018】第36995号","startdate":"2018-06-26 09:56:57","doTime":null,"state":null},{"projcode":36989,"probdesc":"测试","address":"测试","projname":"海盐数管【2018】第36989号","startdate":"2018-06-26 09:15:14","doTime":null,"state":null},{"projcode":36976,"probdesc":"dd","address":"sd","projname":"海盐数管【2018】第36976号","startdate":"2018-06-25 20:51:42","doTime":null,"state":null},{"projcode":36975,"probdesc":"dd","address":"sd","projname":"海盐数管【2018】第36975号","startdate":"2018-06-25 20:48:19","doTime":null,"state":null},{"projcode":36974,"probdesc":"dd","address":"sd","projname":"海盐数管【2018】第36974号","startdate":"2018-06-25 20:48:10","doTime":null,"state":null},{"projcode":36956,"probdesc":"垃圾桶","address":"海盐县枣园东路-长宁路","projname":"海盐数管【2018】第36956号","startdate":"2018-06-25 10:38:29","doTime":null,"state":null},{"projcode":36937,"probdesc":"邮电新村西北角电信电话箱破损","address":"海盐县海丰东路-海丰西路","projname":"海盐数管【2018】第36937号","startdate":"2018-06-24 17:19:24","doTime":null,"state":null},{"projcode":36936,"probdesc":"百尺路杭州叉车南绿化带乱晾晒","address":"海盐县百尺北路203阿娘手擀馄饨no.2","projname":"海盐数管【2018】第36936号","startdate":"2018-06-24 17:15:10","doTime":null,"state":null},{"projcode":36935,"probdesc":"指示牌破损","address":"海盐县枣园西路618号海盐县职成教中心理工校区南埭东北169米","projname":"海盐数管【2018】第36935号","startdate":"2018-06-24 16:46:10","doTime":null,"state":null},{"projcode":36934,"probdesc":"海丰农贸市场百姓家小笼越店经营","address":"海盐县海丰西路211-13联海超","projname":"海盐数管【2018】第36934号","startdate":"2018-06-24 06:28:00","doTime":null,"state":null},{"projcode":36762,"probdesc":"曲尺弄附近拆房施工，粉尘非常大，周边居民房内都是灰尘，请关注！","address":"海盐县勤俭北路附近佳养记百味鸡煲海盐店   佳养记百味鸡煲","projname":"海盐数管【2018】第36762号","startdate":"2018-06-14 14:12:43","doTime":null,"state":null}]
     * total : 60
     */

    private boolean State;
    private Object ErrorCode;
    private Object ErrorMsg;
    private Object Page;
    private int total;
    private List<RtnBean> Rtn;

    public boolean isState() {
        return State;
    }

    public void setState(boolean State) {
        this.State = State;
    }

    public Object getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(Object ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public Object getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(Object ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public Object getPage() {
        return Page;
    }

    public void setPage(Object Page) {
        this.Page = Page;
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
         * projcode : 37017
         * probdesc : 砌筑围墙垃圾乱推，破坏现场绿化。
         * address : 谢家路（金汇奥园南苑东面）
         * projname : 海盐数管【2018】第37017号
         * startdate : 2018-06-27 09:05:25
         * doTime : null
         * state : null
         */

        public  int projcode;
        public String probdesc;
        public String address;
        public String projname;
        public String startdate;
        public String doTime;
        public String state;

        public int getProjcode() {
            return projcode;
        }

        public void setProjcode(int projcode) {
            this.projcode = projcode;
        }

        public String getProbdesc() {
            return probdesc;
        }

        public void setProbdesc(String probdesc) {
            this.probdesc = probdesc;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProjname() {
            return projname;
        }

        public void setProjname(String projname) {
            this.projname = projname;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getDoTime() {
            return doTime;
        }

        public void setDoTime(String doTime) {
            this.doTime = doTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
