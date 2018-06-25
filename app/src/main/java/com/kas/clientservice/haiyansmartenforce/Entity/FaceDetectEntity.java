package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 描述：
 * 时间：2018-06-15
 * 公司：COMS
 */

public class FaceDetectEntity {

    /**
     * image_id : f32eV0TLIfQWG8UnIDp1Vw==
     * request_id : 1529046868,0f19bdbc-ee86-414b-b1ae-6a35a72af1d3
     * time_used : 627
     * faces : [{"face_rectangle":{"width":890,"top":982,"left":370,"height":890},"face_token":"57f721919e043c6227a3a1eddb86f532"}]
     */

    public String image_id;
    public String request_id;
    public int time_used;
    public List<FacesBean> faces;

    public String getImage_id() {
        return image_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public List<FacesBean> getFaces() {
        return faces;
    }

    public static class FacesBean {
        /**
         * face_rectangle : {"width":890,"top":982,"left":370,"height":890}
         * face_token : 57f721919e043c6227a3a1eddb86f532
         */

        public FaceRectangleBean face_rectangle;
        public String face_token;

        public FaceRectangleBean getFace_rectangle() {
            return face_rectangle;
        }

        public String getFace_token() {
            return face_token;
        }

        public static class FaceRectangleBean {
            /**
             * width : 890
             * top : 982
             * left : 370
             * height : 890
             */

            public int width;
            public int top;
            public int left;
            public int height;

            public int getWidth() {
                return width;
            }

            public int getTop() {
                return top;
            }

            public int getLeft() {
                return left;
            }

            public int getHeight() {
                return height;
            }
        }
    }
}
