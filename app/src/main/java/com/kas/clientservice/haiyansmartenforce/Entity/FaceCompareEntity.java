package com.kas.clientservice.haiyansmartenforce.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 描述：
 * 时间：2018-06-13
 * 公司：COMS
 */

public class FaceCompareEntity {

    /**
     * faces1 : [{"face_rectangle":{"width":582,"top":806,"left":521,"height":582},"face_token":"4fa5a283ad10e2685bdf6374d675d31d"}]
     * faces2 : [{"face_rectangle":{"width":594,"top":832,"left":436,"height":594},"face_token":"7821eb895efb4dfd4aff6310578b82ad"}]
     * time_used : 1429
     * thresholds : {"1e-3":62.327,"1e-5":73.975,"1e-4":69.101}
     * confidence : 94.735
     * image_id2 : Z/mAogLCeKWIqwJr8v5DNQ==
     * image_id1 : jpFE+8rliVV/5ID38GYtpA==
     * request_id : 1528859518,b5b4b1d3-222f-407c-a232-9ecac3c7ccff
     */

    public int time_used;
    public ThresholdsBean thresholds;
    public double confidence;
    public String image_id2;
    public String image_id1;
    public String request_id;
    public List<Faces1Bean> faces1;
    public List<Faces2Bean> faces2;

    public static class ThresholdsBean {
        /**
         * 1e-3 : 62.327
         * 1e-5 : 73.975
         * 1e-4 : 69.101
         */

        @SerializedName("1e-3")
        public double _$1e3;
        @SerializedName("1e-5")
        public double _$1e5;
        @SerializedName("1e-4")
        public double _$1e4;
    }

    public static class Faces1Bean {
        /**
         * face_rectangle : {"width":582,"top":806,"left":521,"height":582}
         * face_token : 4fa5a283ad10e2685bdf6374d675d31d
         */

        public FaceRectangleBean face_rectangle;
        public String face_token;

        public static class FaceRectangleBean {
            /**
             * width : 582
             * top : 806
             * left : 521
             * height : 582
             */

            public int width;
            public int top;
            public int left;
            public int height;
        }
    }

    public static class Faces2Bean {
        /**
         * face_rectangle : {"width":594,"top":832,"left":436,"height":594}
         * face_token : 7821eb895efb4dfd4aff6310578b82ad
         */

        public FaceRectangleBeanX face_rectangle;
        public String face_token;

        public static class FaceRectangleBeanX {
            /**
             * width : 594
             * top : 832
             * left : 436
             * height : 594
             */

            public int width;
            public int top;
            public int left;
            public int height;
        }
    }
}
