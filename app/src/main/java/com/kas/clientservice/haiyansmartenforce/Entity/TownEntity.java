package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-06-25
 * 公司：COMS
 */

public class TownEntity {

    private List<TownBean> town;

    public List<TownBean> getTown() {
        return town;
    }

    public void setTown(List<TownBean> town) {
        this.town = town;
    }

    public static class TownBean {
        /**
         * town : 武原街道
         * townid : 1
         */

        private String town;
        private String townid;

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getTownid() {
            return townid;
        }

        public void setTownid(String townid) {
            this.townid = townid;
        }
    }
}
