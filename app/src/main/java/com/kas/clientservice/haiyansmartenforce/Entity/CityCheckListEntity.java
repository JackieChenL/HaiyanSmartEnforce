package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-07-05
 * 公司：COMS
 */

public class CityCheckListEntity {

    private List<KSBean> KS;

    public List<KSBean> getKS() {
        return KS;
    }

    public void setKS(List<KSBean> KS) {
        this.KS = KS;
    }

    public static class KSBean {
        /**
         * tn : 1
         * tc : 0
         * SourceID : 102
         * NameFiL : 市容环卫
         * NameSeL : 违反城市市容和环境卫生管理规定
         * NameEmp : null
         * SouNumberSou : S20180705005
         * NameTas :
         * AddressSou : 测得
         * NameSoF : 线索录入
         * ContentSou : 测试
         * EntryTimeSou : 2018-07-05 10:50:16
         * NameSoT : 上级交办
         * SuggestTas :
         * GpsXYSou : 120.743936,30.770102
         * AttachmentSou : UploadImage/Source/Passive/Origin/102/1290/201807051049320.jpg
         * HostUserID : -1
         * DepartmentID : -1
         * FlowID : 1
         * FirstLevelID : 1
         */

        private int tn;
        private int tc;
        private int SourceID;
        private String NameFiL;
        private String NameSeL;
        private Object NameEmp;
        private String SouNumberSou;
        private String NameTas;
        private String AddressSou;
        private String NameSoF;
        private String ContentSou;
        private String EntryTimeSou;
        private String NameSoT;
        private String SuggestTas;
        private String GpsXYSou;
        private String AttachmentSou;
        private int HostUserID;
        private int DepartmentID;
        private int FlowID;
        private int FirstLevelID;
        private String NameECSou;
        private int SecondLevelID;

        public int getSecondLevelID() {
            return SecondLevelID;
        }

        public int getTn() {
            return tn;
        }

        public void setTn(int tn) {
            this.tn = tn;
        }

        public int getTc() {
            return tc;
        }

        public void setTc(int tc) {
            this.tc = tc;
        }

        public int getSourceID() {
            return SourceID;
        }

        public void setSourceID(int SourceID) {
            this.SourceID = SourceID;
        }

        public String getNameFiL() {
            return NameFiL;
        }

        public void setNameFiL(String NameFiL) {
            this.NameFiL = NameFiL;
        }

        public String getNameSeL() {
            return NameSeL;
        }

        public void setNameSeL(String NameSeL) {
            this.NameSeL = NameSeL;
        }

        public Object getNameEmp() {
            return NameEmp;
        }

        public void setNameEmp(Object NameEmp) {
            this.NameEmp = NameEmp;
        }

        public String getSouNumberSou() {
            return SouNumberSou;
        }

        public void setSouNumberSou(String SouNumberSou) {
            this.SouNumberSou = SouNumberSou;
        }

        public String getNameTas() {
            return NameTas;
        }

        public void setNameTas(String NameTas) {
            this.NameTas = NameTas;
        }

        public String getAddressSou() {
            return AddressSou;
        }

        public void setAddressSou(String AddressSou) {
            this.AddressSou = AddressSou;
        }

        public String getNameSoF() {
            return NameSoF;
        }

        public void setNameSoF(String NameSoF) {
            this.NameSoF = NameSoF;
        }

        public String getContentSou() {
            return ContentSou;
        }

        public void setContentSou(String ContentSou) {
            this.ContentSou = ContentSou;
        }

        public String getEntryTimeSou() {
            return EntryTimeSou;
        }

        public String getNameECSou() {
            return NameECSou;
        }

        public void setEntryTimeSou(String EntryTimeSou) {
            this.EntryTimeSou = EntryTimeSou;
        }

        public String getNameSoT() {
            return NameSoT;
        }

        public void setNameSoT(String NameSoT) {
            this.NameSoT = NameSoT;
        }

        public String getSuggestTas() {
            return SuggestTas;
        }

        public void setSuggestTas(String SuggestTas) {
            this.SuggestTas = SuggestTas;
        }

        public String getGpsXYSou() {
            return GpsXYSou;
        }

        public void setGpsXYSou(String GpsXYSou) {
            this.GpsXYSou = GpsXYSou;
        }

        public String getAttachmentSou() {
            return AttachmentSou;
        }

        public void setAttachmentSou(String AttachmentSou) {
            this.AttachmentSou = AttachmentSou;
        }

        public int getHostUserID() {
            return HostUserID;
        }

        public void setHostUserID(int HostUserID) {
            this.HostUserID = HostUserID;
        }

        public int getDepartmentID() {
            return DepartmentID;
        }

        public void setDepartmentID(int DepartmentID) {
            this.DepartmentID = DepartmentID;
        }

        public int getFlowID() {
            return FlowID;
        }

        public void setFlowID(int FlowID) {
            this.FlowID = FlowID;
        }

        public int getFirstLevelID() {
            return FirstLevelID;
        }


        public void setFirstLevelID(int FirstLevelID) {
            this.FirstLevelID = FirstLevelID;
        }
    }
}
