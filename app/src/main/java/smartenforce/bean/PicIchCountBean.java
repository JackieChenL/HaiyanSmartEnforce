package smartenforce.bean;


import java.util.List;

import smartenforce.base.BaseBean;


public class PicIchCountBean extends BaseBean {


    /**
     * Photograph : {"PhList":[{"ShotTimePho":"2018-06-13 13:11:31","UserIDPho":14,"StatusPho":1,"PictruePho":"UploadImage/inquest/14/201806131312213536/201806131312210.jpg|UploadImage/inquest/14/201806131312213536/201806131312211.jpg|UploadImage/inquest/14/201","CreateTimePho":"2018-06-13 13:12:22","ShotAddressPho":"浙江省嘉兴市海宁市河东路50号","ShotManPho":"顾新丽","InquestIDPho":78,"PhotographID":1035,"ExplainPho":""},{"ShotTimePho":"2018-06-13 13:11:31","UserIDPho":14,"StatusPho":1,"PictruePho":"UploadImage/inquest/14/201806131312225243/201806131312220.jpg|UploadImage/inquest/14/201806131312225243/201806131312221.jpg|UploadImage/inquest/14/201","CreateTimePho":"2018-06-13 13:12:22","ShotAddressPho":"浙江省嘉兴市海宁市河东路50号","ShotManPho":"顾新丽","InquestIDPho":78,"PhotographID":1036,"ExplainPho":""}],"PhCount":4}
     * Ichnography : {"IcList":[],"IcCount":0}
     */

    public PhotographBean Photograph;
    public IchnographyBean Ichnography;

    public static class PhotographBean {
        /**
         * PhList : [{"ShotTimePho":"2018-06-13 13:11:31","UserIDPho":14,"StatusPho":1,"PictruePho":"UploadImage/inquest/14/201806131312213536/201806131312210.jpg|UploadImage/inquest/14/201806131312213536/201806131312211.jpg|UploadImage/inquest/14/201","CreateTimePho":"2018-06-13 13:12:22","ShotAddressPho":"浙江省嘉兴市海宁市河东路50号","ShotManPho":"顾新丽","InquestIDPho":78,"PhotographID":1035,"ExplainPho":""},{"ShotTimePho":"2018-06-13 13:11:31","UserIDPho":14,"StatusPho":1,"PictruePho":"UploadImage/inquest/14/201806131312225243/201806131312220.jpg|UploadImage/inquest/14/201806131312225243/201806131312221.jpg|UploadImage/inquest/14/201","CreateTimePho":"2018-06-13 13:12:22","ShotAddressPho":"浙江省嘉兴市海宁市河东路50号","ShotManPho":"顾新丽","InquestIDPho":78,"PhotographID":1036,"ExplainPho":""}]
         * PhCount : 4
         */

        public int PhCount;
        public List<PhListBean> PhList;

        public static class PhListBean {
            /**
             * ShotTimePho : 2018-06-13 13:11:31
             * UserIDPho : 14
             * StatusPho : 1
             * PictruePho : UploadImage/inquest/14/201806131312213536/201806131312210.jpg|UploadImage/inquest/14/201806131312213536/201806131312211.jpg|UploadImage/inquest/14/201
             * CreateTimePho : 2018-06-13 13:12:22
             * ShotAddressPho : 浙江省嘉兴市海宁市河东路50号
             * ShotManPho : 顾新丽
             * InquestIDPho : 78
             * PhotographID : 1035
             * ExplainPho :
             */

            public String ShotTimePho;
            public int UserIDPho;
            public int StatusPho;
            public String PictruePho;
            public String CreateTimePho;
            public String ShotAddressPho;
            public String ShotManPho;
            public int InquestIDPho;
            public int PhotographID;
            public String ExplainPho;
        }
    }

    public static class IchnographyBean {
        /**
         * IcList : []
         * IcCount : 0
         */

        public int IcCount;
        public List<?> IcList;
    }
}
