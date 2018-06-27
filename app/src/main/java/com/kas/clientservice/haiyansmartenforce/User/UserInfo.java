package com.kas.clientservice.haiyansmartenforce.User;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-03
 * 公司：COMS
 */

public class UserInfo {


    /**
     * PublicUsers : {"PublicUsersID":"1","type":"67"}
     * TollCollector : {"TollCollectorID":"1","type":"2","Road":[{"Berthname":"勤俭路--1号停车位","BerthID":"1"},{"Berthname":"勤俭路--22号停车位","BerthID":"2"},{"Berthname":"城北路--3号停车位","BerthID":"3"},{"Berthname":"城北路--4号停车位","BerthID":"4"},{"Berthname":"勤俭路--1","BerthID":"5"},{"Berthname":"勤俭路--2","BerthID":"6"},{"Berthname":"建设银行--1","BerthID":"19"},{"Berthname":"建设银行--2","BerthID":"20"},{"Berthname":"建设银行--3","BerthID":"21"},{"Berthname":"建设银行--4","BerthID":"22"},{"Berthname":"建设银行--5","BerthID":"23"},{"Berthname":"建设银行--6","BerthID":"24"},{"Berthname":"建设银行--7","BerthID":"25"},{"Berthname":"建设银行--8","BerthID":"26"},{"Berthname":"建设银行--9","BerthID":"27"},{"Berthname":"建设银行--10","BerthID":"28"},{"Berthname":"建设银行--11","BerthID":"29"},{"Berthname":"建设银行--12","BerthID":"30"},{"Berthname":"建设银行--13","BerthID":"31"},{"Berthname":"建设银行--14","BerthID":"32"}]}
     * LawEnforcementOfficials : {"LawEnforcementOfficialsId":"1","type":"3"}
     * ReviewName : {"ReviewNameID":"1","type":"5"}
     * CheckName : {"CheckNameID":"1","type":"6"}
     * ChangeName : {"ChangeNameID":"1","type":"7"}
     * ClassifyingRubbish : {"ClassifyingRubbishId":"4","type":"4"}
     * Name : {"NameEmp":"Administrator","UserID":"1","NameDep":"海盐县综合行政执法局","DepartmentID":"1","type":"9"}
     * UI : [{"url":"http://hywx.hnzhzf.top/xxhdpi/zhuomuniao.jpg","typeid":6,"title":"城市啄木鸟"},{"url":"http://hywx.hnzhzf.top/xxhdpi/case_search.jpg","typeid":7,"title":"案件查询"},{"url":"http://hywx.hnzhzf.top/xxhdpi/tingchejiaofei.jpg","typeid":1,"title":"停车收费"},{"url":"http://hywx.hnzhzf.top/xxhdpi/weizhangtingche.png","typeid":2,"title":"违章停车"},{"url":"http://hywx.hnzhzf.top/xxhdpi/case_search.jpg","typeid":3,"title":"案件查询"},{"url":"http://hywx.hnzhzf.top/xxhdpi/hwjg.png","typeid":5,"title":"环卫监管"},{"url":"http://hywx.hnzhzf.top/xxhdpi/lajifenlei.jpg","typeid":4,"title":"垃圾分类"},{"url":"http://hywx.hnzhzf.top/xxhdpi/diaochafaxian.jpg","typeid":8,"title":"巡查发现"},{"url":"http://hywx.hnzhzf.top/xxhdpi/xiansuochuli.jpg","typeid":9,"title":"线索处理"},{"url":"http://hywx.hnzhzf.top/xxhdpi/anjianshangbao.jpg","typeid":10,"title":"案件上报"},{"url":"http://hywx.hnzhzf.top/xxhdpi/fuwuduixiang.jpg","typeid":11,"title":"服务对象"},{"url":"http://hywx.hnzhzf.top/xxhdpi/diaochaquzheng.jpg","typeid":12,"title":"调查取证"},{"url":"http://hywx.hnzhzf.top/xxhdpi/falvfagui.jpg","typeid":13,"title":"法律法规"},{"url":"http://hywx.hnzhzf.top/xxhdpi/weifaxingwei.jpg","typeid":14,"title":"违法行为"},{"url":"http://hywx.hnzhzf.top/xxhdpi/anjianshenpi.jpg","typeid":15,"title":"案件审批"},{"url":"http://hywx.hnzhzf.top/xxhdpi/renwupaiqian.jpg","typeid":16,"title":"任务派遣"},{"url":"http://hywx.hnzhzf.top/xxhdpi/shujutongji.jpg","typeid":17,"title":"数据统计"}]
     */

    public PublicUsersBean PublicUsers;
    public TollCollectorBean TollCollector;
    public LawEnforcementOfficialsBean LawEnforcementOfficials;
    public ReviewNameBean ReviewName;
    public CheckNameBean CheckName;
    public ChangeNameBean ChangeName;
    public ClassifyingRubbishBean ClassifyingRubbish;
    public ParkingSupervisorBean ParkingSupervisor;
    public SzcgBean szcg;
    public NameBean Name;
    public List<UIBean> UI;


    public String getChangeNameID() {
        return getChangeName().getChangeNameID();
    }

    public String getCheckNameID() {
        return getCheckName().getCheckNameID();
    }


    public String getClassifyingRubbishId() {
        return getClassifyingRubbish().getClassifyingRubbishId();
    }

    public String getLawEnforcementOfficialsId() {
        return getLawEnforcementOfficials().getLawEnforcementOfficialsId();
    }

    public String getPublicUsersID() {
        return getPublicUsers().PublicUsersID;
    }

    public String getReviewNameID() {
        return getReviewName().getReviewNameID();
    }

    public String getTollCollectorID() {
        return getTollCollector().getTollCollectorID();
    }


    public String getParkingSupervisorId() {
        return ParkingSupervisor.ParkingSupervisorId;
    }


    public static class PublicUsersBean {
        /**
         * PublicUsersID : 1
         * type : 67
         */

        public String PublicUsersID;
        public String type;

        public String getPublicUsersID() {
            return PublicUsersID;
        }

        public String getType() {
            return type;
        }
    }


    public static class SzcgBean {


        public String usercode;
        public String departcode;
        public String type;


    }

    public static class ParkingSupervisorBean {


        /**
         * ParkingSupervisorId : 1
         * type : 8
         */

        public String ParkingSupervisorId;
        public String type;
    }

    public static class TollCollectorBean {
        /**
         * TollCollectorID : 1
         * type : 2
         * Road : [{"Berthname":"勤俭路--1号停车位","BerthID":"1"},{"Berthname":"勤俭路--22号停车位","BerthID":"2"},{"Berthname":"城北路--3号停车位","BerthID":"3"},{"Berthname":"城北路--4号停车位","BerthID":"4"},{"Berthname":"勤俭路--1","BerthID":"5"},{"Berthname":"勤俭路--2","BerthID":"6"},{"Berthname":"建设银行--1","BerthID":"19"},{"Berthname":"建设银行--2","BerthID":"20"},{"Berthname":"建设银行--3","BerthID":"21"},{"Berthname":"建设银行--4","BerthID":"22"},{"Berthname":"建设银行--5","BerthID":"23"},{"Berthname":"建设银行--6","BerthID":"24"},{"Berthname":"建设银行--7","BerthID":"25"},{"Berthname":"建设银行--8","BerthID":"26"},{"Berthname":"建设银行--9","BerthID":"27"},{"Berthname":"建设银行--10","BerthID":"28"},{"Berthname":"建设银行--11","BerthID":"29"},{"Berthname":"建设银行--12","BerthID":"30"},{"Berthname":"建设银行--13","BerthID":"31"},{"Berthname":"建设银行--14","BerthID":"32"}]
         */

        public String TollCollectorID;
        public String type;
        public List<RoadBean> road;

        public List<RoadBean> getRoad() {
            return road;
        }

        public String getTollCollectorID() {
            return TollCollectorID;
        }

        public String getType() {
            return type;
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
    }

    public static class LawEnforcementOfficialsBean {
        /**
         * LawEnforcementOfficialsId : 1
         * type : 3
         */

        public String LawEnforcementOfficialsId;
        public String type;

        public String getLawEnforcementOfficialsId() {
            return LawEnforcementOfficialsId;
        }

        public String getType() {
            return type;
        }
    }

    public static class ReviewNameBean {
        /**
         * ReviewNameID : 1
         * type : 5
         */

        public String ReviewNameID;
        public String type;

        public String getReviewNameID() {
            return ReviewNameID;
        }

        public String getType() {
            return type;
        }
    }

    public static class CheckNameBean {
        /**
         * CheckNameID : 1
         * type : 6
         */

        public String CheckNameID;
        public String type;

        public String getCheckNameID() {
            return CheckNameID;
        }

        public String getType() {
            return type;
        }
    }

    public static class ChangeNameBean {
        /**
         * ChangeNameID : 1
         * type : 7
         */

        public String ChangeNameID;
        public String type;

        public String getChangeNameID() {
            return ChangeNameID;
        }

        public String getType() {
            return type;
        }
    }

    public static class ClassifyingRubbishBean {
        /**
         * ClassifyingRubbishId : 4
         * type : 4
         */

        public String ClassifyingRubbishId;
        public String type;

        public String getClassifyingRubbishId() {
            return ClassifyingRubbishId;
        }

        public String getType() {
            return type;
        }
    }

    public static class NameBean {
        /**
         * NameEmp : Administrator
         * UserID : 1
         * NameDep : 海盐县综合行政执法局
         * DepartmentID : 1
         * type : 9
         */

        public String NameEmp;
        public String UserID;
        public String NameDep;
        public String DepartmentID;
        public String type;
        public String AddressDep;
        public String UserHightFlowUnitType;

    }

    public static class UIBean {
        /**
         * url : http://hywx.hnzhzf.top/xxhdpi/zhuomuniao.jpg
         * typeid : 6
         * title : 城市啄木鸟
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

    public ChangeNameBean getChangeName() {
        return ChangeName;
    }

    public CheckNameBean getCheckName() {
        return CheckName;
    }

    public ClassifyingRubbishBean getClassifyingRubbish() {
        return ClassifyingRubbish;
    }

    public LawEnforcementOfficialsBean getLawEnforcementOfficials() {
        return LawEnforcementOfficials;
    }

    public NameBean getName() {
        return Name;
    }

    public PublicUsersBean getPublicUsers() {
        return PublicUsers;
    }

    public ReviewNameBean getReviewName() {
        return ReviewName;
    }

    public TollCollectorBean getTollCollector() {
        return TollCollector;
    }

    public List<UIBean> getUI() {
        return UI;
    }

    public String getSWYTType() {
        if (getCheckName() != null && !getCheckName().getType().equals("0")) {
            return getCheckName().getType();
        }
        if (getReviewName() != null && !getReviewName().getType().equals("0")) {
            return getReviewName().getType();
        }
        if (getChangeName() != null && !getChangeName().getType().equals("0")) {
            return getChangeName().getType();
        }
        return "0";
    }
}
