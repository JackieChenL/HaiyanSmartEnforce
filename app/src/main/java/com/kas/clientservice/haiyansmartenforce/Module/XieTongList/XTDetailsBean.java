package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import java.util.List;


public class XTDetailsBean {


    /**
     * State : true
     * ErrorCode : null
     * ErrorMsg : null
     * Page : null
     * Rtn : {"Table":[{"probdesc":"dxx","projname":"海盐数管【2018】第36995号","projcode":36995,"stepid":null,"stateid":null,"ButtonCode":null,"NodeId":"2","role":null,"StepDate":null,"TargetDepartCode":"","ChildDepartCode":null,"DoDepartCode":null,"DoRole":null,"startdate":"2018-06-26 09:56:57","enddate":null,"Telephonist":"啄木鸟用户","TelephonistCode":1,"IsManual":true,"probsource":"20","typecode":true,"bigclass":"02  ","smallclass":"0202","detailedclass":"        ","AreaID":"330402","StreetID":"330402001","SquareID":"330402001001","gridcode":"33040200100101","address":"ddd","fid":"120.948716,30.542031","PartID":null,"release":"0","isdel":"0","istransaction":false,"locktime":null,"lockusercode":null,"withDept":"0","isgreat":"1","ispress":false,"istimeout":null,"isthrough":"0","groupid":"","isProcess":null,"ProcessType":null,"IsNeedFeedBack":false,"IsFeedBack":null,"FeedbackDate":null,"Feedbacker":null,"FeedbackMode":null,"RequestProcessType":null,"ChangeStatus":"0","ChangeDate":null,"WorkGrid":"","IsReturned":null,"NoPassNum":null,"isManyDept":null,"DeptProjectState":null,"erjiprojcode":null,"isgz":null,"departtime":null,"departtimetype":null,"tracetime":null,"fgDepart":null,"fgs":null,"HowManyTime":null,"ffs":null,"laTime":null,"projcode1":36995,"projname1":"海盐数管【2018】第36995号","area":null,"street":null,"square":null,"probdesc1":"dxx","VerifyDesc":null,"Tracer":null,"Memo":null,"erjiprojname":null,"detailedclassname":null,"ProcessTypeID":0,"bigclassname":"宣传广告","smallclassname":"违法悬挂广告牌匾","detailedclassname1":"        ","ioflag":null,"PdaMsgContent":null,"TargetDepartName":null,"DoDepartName":null,"probsourceName":"未知","treatTime":"2018-06-26 09:56:57,-582,30,2018-06-26 10:26:57","probclassName":"事件","ImpeachName":"","ImpeachTel":"13222222222","csbs":0}],"Table1":[{"filestate":"1","filepath":"http://112.13.194.180:82/Handler/UploadImage/20180626095928/201806260959280.jpg","cudate":"2018-06-26 09:56:57"}],"Table2":[],"Table3":[]}
     * total : 0
     */

    public boolean State;
    public String ErrorCode;
    public String ErrorMsg;
    public int Page;
    public RtnBean Rtn;
    public int total;

    public static class RtnBean {
        public List<TableBean> Table;
        public List<Table1Bean> Table1;
        public List<?> Table2;
        public List<?> Table3;

        public static class TableBean {
            /**
             * probdesc : dxx
             * projname : 海盐数管【2018】第36995号
             * projcode : 36995
             * stepid : null
             * stateid : null
             * ButtonCode : null
             * NodeId : 2
             * role : null
             * StepDate : null
             * TargetDepartCode :
             * ChildDepartCode : null
             * DoDepartCode : null
             * DoRole : null
             * startdate : 2018-06-26 09:56:57
             * enddate : null
             * Telephonist : 啄木鸟用户
             * TelephonistCode : 1
             * IsManual : true
             * probsource : 20
             * typecode : true
             * bigclass : 02
             * smallclass : 0202
             * detailedclass :
             * AreaID : 330402
             * StreetID : 330402001
             * SquareID : 330402001001
             * gridcode : 33040200100101
             * address : ddd
             * fid : 120.948716,30.542031
             * PartID : null
             * release : 0
             * isdel : 0
             * istransaction : false
             * locktime : null
             * lockusercode : null
             * withDept : 0
             * isgreat : 1
             * ispress : false
             * istimeout : null
             * isthrough : 0
             * groupid :
             * isProcess : null
             * ProcessType : null
             * IsNeedFeedBack : false
             * IsFeedBack : null
             * FeedbackDate : null
             * Feedbacker : null
             * FeedbackMode : null
             * RequestProcessType : null
             * ChangeStatus : 0
             * ChangeDate : null
             * WorkGrid :
             * IsReturned : null
             * NoPassNum : null
             * isManyDept : null
             * DeptProjectState : null
             * erjiprojcode : null
             * isgz : null
             * departtime : null
             * departtimetype : null
             * tracetime : null
             * fgDepart : null
             * fgs : null
             * HowManyTime : null
             * ffs : null
             * laTime : null
             * projcode1 : 36995
             * projname1 : 海盐数管【2018】第36995号
             * area : null
             * street : null
             * square : null
             * probdesc1 : dxx
             * VerifyDesc : null
             * Tracer : null
             * Memo : null
             * erjiprojname : null
             * detailedclassname : null
             * ProcessTypeID : 0
             * bigclassname : 宣传广告
             * smallclassname : 违法悬挂广告牌匾
             * detailedclassname1 :
             * ioflag : null
             * PdaMsgContent : null
             * TargetDepartName : null
             * DoDepartName : null
             * probsourceName : 未知
             * treatTime : 2018-06-26 09:56:57,-582,30,2018-06-26 10:26:57
             * probclassName : 事件
             * ImpeachName :
             * ImpeachTel : 13222222222
             * csbs : 0
             */

            public String probdesc;
            public String projname;
            public String projcode;
            public Object stepid;
            public Object stateid;
            public Object ButtonCode;
            public String NodeId;
            public Object role;
            public Object StepDate;
            public String TargetDepartCode;
            public Object ChildDepartCode;
            public Object DoDepartCode;
            public Object DoRole;
            public String startdate;
            public Object enddate;
            public String Telephonist;
            public int TelephonistCode;
            public boolean IsManual;
            public String probsource;
            public boolean typecode;
            public String bigclass;
            public String smallclass;
            public String detailedclass;
            public String AreaID;
            public String StreetID;
            public String SquareID;
            public String gridcode;
            public String address;
            public String fid;
            public Object PartID;
            public String release;
            public String isdel;
            public boolean istransaction;
            public Object locktime;
            public Object lockusercode;
            public String withDept;
            public String isgreat;
            public boolean ispress;
            public Object istimeout;
            public String isthrough;
            public String groupid;
            public Object isProcess;
            public Object ProcessType;
            public boolean IsNeedFeedBack;
            public Object IsFeedBack;
            public Object FeedbackDate;
            public Object Feedbacker;
            public Object FeedbackMode;
            public Object RequestProcessType;
            public String ChangeStatus;
            public Object ChangeDate;
            public String WorkGrid;
            public Object IsReturned;
            public Object NoPassNum;
            public Object isManyDept;
            public Object DeptProjectState;
            public Object erjiprojcode;
            public Object isgz;
            public Object departtime;
            public Object departtimetype;
            public String tracetime;
            public Object fgDepart;
            public Object fgs;
            public Object HowManyTime;
            public Object ffs;
            public Object laTime;
            public int projcode1;
            public String projname1;
            public String area;
            public String street;
            public String square;
            public String probdesc1;
            public String VerifyDesc;
            public String Tracer;
            public String Memo;
            public String erjiprojname;
            public String detailedclassname;
            public int ProcessTypeID;
            public String bigclassname;
            public String smallclassname;
            public String detailedclassname1;
            public Object ioflag;
            public Object PdaMsgContent;
            public Object TargetDepartName;
            public Object DoDepartName;
            public String probsourceName;
            public String treatTime;
            public String probclassName;
            public String ImpeachName;
            public String ImpeachTel;
            public int csbs;
        }

        public static class Table1Bean {
            /**
             * filestate : 1
             * filepath : http://112.13.194.180:82/Handler/UploadImage/20180626095928/201806260959280.jpg
             * cudate : 2018-06-26 09:56:57
             */

            public String filestate;
            public String filepath;
            public String cudate;
        }
    }
}
