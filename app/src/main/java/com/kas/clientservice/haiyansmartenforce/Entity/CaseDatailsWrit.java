package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 查看文书
 *
 * @author Administrator
 */
public class CaseDatailsWrit {

    private List<KSBean> KS;

    public List<KSBean> getKS() {
        return KS;
    }

    public class KSBean {
        String ReferenceNumber;
        String type;
        String HandTime;
        String ID;

        public String getReferenceNumber() {
            return ReferenceNumber;
        }

        public void setReferenceNumber(String referenceNumber) {
            ReferenceNumber = referenceNumber;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHandTime() {
            return HandTime;
        }

        public void setHandTime(String handTime) {
            HandTime = handTime;
        }

        public String getID() {
            return ID;
        }

        public void setID(String iD) {
            ID = iD;
        }
    }
}
