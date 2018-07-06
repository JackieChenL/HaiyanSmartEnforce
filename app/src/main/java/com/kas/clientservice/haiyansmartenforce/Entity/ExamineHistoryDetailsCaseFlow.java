package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

public class ExamineHistoryDetailsCaseFlow {
    private List<KSBean> KS;

    public List<KSBean> getKsBean() {
        return KS;
    }

    public class KSBean {


        String NameCaF;
        String NameEmp;
        String SuggestCaS;

        public String getNameCaF() {
            return NameCaF;
        }

        public void setNameCaF(String nameCaF) {
            NameCaF = nameCaF;
        }

        public String getNameEmp() {
            return NameEmp;
        }

        public void setNameEmp(String nameEmp) {
            NameEmp = nameEmp;
        }

        public String getSuggestCaS() {
            return SuggestCaS;
        }

        public void setSuggestCaS(String suggestCaS) {
            SuggestCaS = suggestCaS;
        }

    }
}