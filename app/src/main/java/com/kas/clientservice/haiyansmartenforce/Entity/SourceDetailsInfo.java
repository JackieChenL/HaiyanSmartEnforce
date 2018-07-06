package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 案件
 *
 * @author Administrator
 */
public class SourceDetailsInfo {

    public List<KsBean> KS;

    public List<KsBean> getKS() {
        return KS;
    }

    public class KsBean {


        String SourceID;
        String EntOrCitiSou;
        String SouNumberSou;
        String NameFiL;
        String EntryTimeSou;
        String NameECSou;
        String AddressSou;
        String NameSoF;
        String state;

        public String getSourceID() {
            return SourceID;
        }

        public void setSourceID(String sourceID) {
            SourceID = sourceID;
        }

        public String getEntOrCitiSou() {
            return EntOrCitiSou;
        }

        public void setEntOrCitiSou(String entOrCitiSou) {
            EntOrCitiSou = entOrCitiSou;
        }

        public String getSouNumberSou() {
            return SouNumberSou;
        }

        public void setSouNumberSou(String souNumberSou) {
            SouNumberSou = souNumberSou;
        }

        public String getNameFiL() {
            return NameFiL;
        }

        public void setNameFiL(String nameFiL) {
            NameFiL = nameFiL;
        }

        public String getEntryTimeSou() {
            return EntryTimeSou;
        }

        public void setEntryTimeSou(String entryTimeSou) {
            EntryTimeSou = entryTimeSou;
        }

        public String getNameECSou() {
            return NameECSou;
        }

        public void setNameECSou(String nameECSou) {
            NameECSou = nameECSou;
        }

        public String getAddressSou() {
            return AddressSou;
        }

        public void setAddressSou(String addressSou) {
            AddressSou = addressSou;
        }

        public String getNameSoF() {
            return NameSoF;
        }

        public void setNameSoF(String nameSoF) {
            NameSoF = nameSoF;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
