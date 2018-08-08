package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-08-01
 * 公司：COMS
 */

public class AreaEntity {

    private List<KSBean> KS;

    public List<KSBean> getKS() {
        return KS;
    }

    public void setKS(List<KSBean> KS) {
        this.KS = KS;
    }

    public static class KSBean {
        public KSBean( String name,int id) {
            this.id = id;
            this.name = name;
        }

        /**
         * id : 2
         * name : 海盐县武原街道
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }
}
