package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-06-13
 * 公司：COMS
 */

public class IdentityCardScanEntity {

    /**
     * image_id : YKKa6R5qtipQCtbl7mvpVA==
     * request_id : 1528872288,ddf7f83d-f105-4bd2-9290-4d4eb7c96c33
     * cards : [{"name":"代用名","gender":"男","id_card_number":"430512198908131367","birthday":"1989-08-13","race":"汉","address":"湖南省长沙市开福区巡道街幸福小区居民组一","type":1,"side":"front"}]
     * time_used : 937
     */

    public String image_id;
    public String request_id;
    public int time_used;
    public List<CardsBean> cards;

    public String getImage_id() {
        return image_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public List<CardsBean> getCards() {
        return cards;
    }

    public static class CardsBean {
        /**
         * name : 代用名
         * gender : 男
         * id_card_number : 430512198908131367
         * birthday : 1989-08-13
         * race : 汉
         * address : 湖南省长沙市开福区巡道街幸福小区居民组一
         * type : 1
         * side : front
         */

        public String name;
        public String gender;
        public String id_card_number;
        public String birthday;
        public String race;
        public String address;
        public int type;
        public String side;
        public String issued_by;
        public String valid_date;

        public String getIssued_by() {
            return issued_by;
        }

        public String getValid_date() {
            return valid_date;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getId_card_number() {
            return id_card_number;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getRace() {
            return race;
        }

        public String getAddress() {
            return address;
        }

        public int getType() {
            return type;
        }

        public String getSide() {
            return side;
        }
    }
}
