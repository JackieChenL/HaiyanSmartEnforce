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
     * ErrorCode : -1
     * ErrorMsg :
     * Rtn : [{"ID":6,"Title":"为世界杯呐喊助威，但有件事千万不要忘记！","BroadcastPicture":"UploadImage/NewsAndNotice/NewsImg/1/20180720084718/1531961550355.jpg|","Text":"四年一度的世界杯足球赛正在如火如荼的举行，也吸引了全球无数的观众不分昼夜追看。这个球迷的节日，对于一些非球迷来说似乎已经成为了一场噪音灾难。球迷的加油呐喊、啤酒碰杯声，真可谓热闹非凡，这可让周边居民夜不能眠、不堪其扰。而个别球迷在家彻夜看球，声音很大，也引发邻居不满甚至报警。\n那么，你家附近有因世界杯产生噪音吗？这种噪音是否已经影响到了你的生活？相关部门有什么工作举措吗？\n\n下面来介绍一下今天的主角：噪音检测器\n\n大家好！我叫噪音检测器，我需要面朝太阳才能发挥我的功效，世界杯比赛前夕被安装到了海盐县城区。\n在你娱乐、经营活动的过程中，不要发出过大的声音哦，不然我可是会发火的哟！一旦我发火，就会把相关的数据传输到海盐县智慧城市管理运行中心，执法人员立刻会来找你哦。\n\n近期，县综合执法局为有效缓解县城区经营场所的噪音扰民现象，在智慧城管项目中自主创新研发了噪音检测系统，在海盐县主城区选取了6个点进行噪音污染实时监测试点。\n当检测器周围声音分贝值超过预先设定的最高值时，相关数据就会通过物联网传输到海盐县智慧城市管理运行中心进行报警，工作人员立刻通知附近的执法队员赶赴现场进行教育劝导。\n\n下一步，县综合执法局将在9个镇（街道）各试点安装1套噪音检测设备，相关数据会传输到县智慧城市运行管理平台镇（街道）分平台，进一步加强对噪音扰民的管理，为海盐县争创全国文明城市、浙江省文明县添砖加瓦。","TextPicture":"","ToExamine":1,"Status":1,"CreateTime":"2018-07-20 08:19:21","UpdateTime":"2018-07-20 08:20:23","CreateUserInfoID":1,"NameEmp":"管理员"},{"ID":7,"Title":"浙江电视台聚焦海盐\u201c智慧城管\u201d","BroadcastPicture":"UploadImage/NewsAndNotice/NewsImg/1/20180720085820/1532046236084.jpg|","Text":"昨晚，浙江台《地市头条》栏目报道了由我台全媒体新闻中心上送的《大数据打造智慧城管 科技助力文明创建》，肯定了我县综合行政执法局用科技手段打造的智慧综合执法平台，满足改革深化依法行政、提升执法效能的需求，促进我县综合执法实现高效执法、阳光执法。\n我县于2017年底正式启动\u201c智慧城管\u201d建设，主要涉及十大硬件和十大软件，包括综合执法办案子系统、环卫运行考核子系统、路面停车管理子系统、防违控违综合管理子系统等，接下来还将尝试建立城乡管理信用系统。\n噪声污染在线监测系统，是县综合行政执法局\u201c智慧城管\u201d近期启用的新设备，目前县城区共设有六处，分别位于枣园路、勤俭路、董家弄步行街等噪音污染较严重的路段。系统的启用，在一定程度上可以缓解噪音扰民现象，提高了管理效率。与之同期启用的还有井盖在线监测系统，目前在县城枣园路上有十个进行试点，都是\u201c智慧城管\u201d的组成部分之一。\n设备采集到的噪音数据会通过物联网，直接传输到智慧城市管理运行平台上，噪音检测增强城市文明。\n信息化建设、规范化工作，\u201c智慧城管\u201d利用科技手段加强城市管理，为城市陋习把脉，为城市文明创建添砖加瓦。","TextPicture":"","ToExamine":1,"Status":1,"CreateTime":"2018-07-20 08:22:18","UpdateTime":"2018-07-20 08:21:58","CreateUserInfoID":1,"NameEmp":"管理员"}]
     * total : 2
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
         * ID : 6
         * Title : 为世界杯呐喊助威，但有件事千万不要忘记！
         * BroadcastPicture : UploadImage/NewsAndNotice/NewsImg/1/20180720084718/1531961550355.jpg|
         * Text : 四年一度的世界杯足球赛正在如火如荼的举行，也吸引了全球无数的观众不分昼夜追看。这个球迷的节日，对于一些非球迷来说似乎已经成为了一场噪音灾难。球迷的加油呐喊、啤酒碰杯声，真可谓热闹非凡，这可让周边居民夜不能眠、不堪其扰。而个别球迷在家彻夜看球，声音很大，也引发邻居不满甚至报警。
         那么，你家附近有因世界杯产生噪音吗？这种噪音是否已经影响到了你的生活？相关部门有什么工作举措吗？

         下面来介绍一下今天的主角：噪音检测器

         大家好！我叫噪音检测器，我需要面朝太阳才能发挥我的功效，世界杯比赛前夕被安装到了海盐县城区。
         在你娱乐、经营活动的过程中，不要发出过大的声音哦，不然我可是会发火的哟！一旦我发火，就会把相关的数据传输到海盐县智慧城市管理运行中心，执法人员立刻会来找你哦。

         近期，县综合执法局为有效缓解县城区经营场所的噪音扰民现象，在智慧城管项目中自主创新研发了噪音检测系统，在海盐县主城区选取了6个点进行噪音污染实时监测试点。
         当检测器周围声音分贝值超过预先设定的最高值时，相关数据就会通过物联网传输到海盐县智慧城市管理运行中心进行报警，工作人员立刻通知附近的执法队员赶赴现场进行教育劝导。

         下一步，县综合执法局将在9个镇（街道）各试点安装1套噪音检测设备，相关数据会传输到县智慧城市运行管理平台镇（街道）分平台，进一步加强对噪音扰民的管理，为海盐县争创全国文明城市、浙江省文明县添砖加瓦。
         * TextPicture :
         * ToExamine : 1
         * Status : 1
         * CreateTime : 2018-07-20 08:19:21
         * UpdateTime : 2018-07-20 08:20:23
         * CreateUserInfoID : 1
         * NameEmp : 管理员
         */

        private int ID;
        private String Title;
        private String BroadcastPicture;
        private String Text;
        private String TextPicture;
        private int ToExamine;
        private int Status;
        private String CreateTime;
        private String UpdateTime;
        private int CreateUserInfoID;
        private String NameEmp;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getBroadcastPicture() {
            return BroadcastPicture;
        }

        public void setBroadcastPicture(String BroadcastPicture) {
            this.BroadcastPicture = BroadcastPicture;
        }

        public String getText() {
            return Text;
        }

        public void setText(String Text) {
            this.Text = Text;
        }

        public String getTextPicture() {
            return TextPicture;
        }

        public void setTextPicture(String TextPicture) {
            this.TextPicture = TextPicture;
        }

        public int getToExamine() {
            return ToExamine;
        }

        public void setToExamine(int ToExamine) {
            this.ToExamine = ToExamine;
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

        public int getCreateUserInfoID() {
            return CreateUserInfoID;
        }

        public void setCreateUserInfoID(int CreateUserInfoID) {
            this.CreateUserInfoID = CreateUserInfoID;
        }

        public String getNameEmp() {
            return NameEmp;
        }

        public void setNameEmp(String NameEmp) {
            this.NameEmp = NameEmp;
        }
    }
}
