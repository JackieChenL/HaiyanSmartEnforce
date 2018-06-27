package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-06-27
 * 公司：COMS
 */

public class LeaderSearchListEntity {

    private List<KSBean> KS;

    public List<KSBean> getKS() {
        return KS;
    }

    public void setKS(List<KSBean> KS) {
        this.KS = KS;
    }

    public static class KSBean {
        /**
         * CaseID : 2324
         * name : 李伟良
         * number : C20180627001
         * regTime : 2018-06-27 09:11:00
         * NameCaF : 行政机关立案审批
         * CaseFlowIDCas : 5
         * state : 待处理
         * caseFlow : 立案审批
         * HostUserID : 1
         * DepartmentID : 1
         * FlowID : 5
         * FirstLevelID : 1
         */

        private int CaseID;
        private String name;
        private String number;
        private String regTime;
        private String NameCaF;
        private int CaseFlowIDCas;
        private String state;
        private String caseFlow;
        private int HostUserID;
        private int DepartmentID;
        private int FlowID;
        private int FirstLevelID;

        public int getCaseID() {
            return CaseID;
        }

        public void setCaseID(int CaseID) {
            this.CaseID = CaseID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public String getNameCaF() {
            return NameCaF;
        }

        public void setNameCaF(String NameCaF) {
            this.NameCaF = NameCaF;
        }

        public int getCaseFlowIDCas() {
            return CaseFlowIDCas;
        }

        public void setCaseFlowIDCas(int CaseFlowIDCas) {
            this.CaseFlowIDCas = CaseFlowIDCas;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCaseFlow() {
            return caseFlow;
        }

        public void setCaseFlow(String caseFlow) {
            this.caseFlow = caseFlow;
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
