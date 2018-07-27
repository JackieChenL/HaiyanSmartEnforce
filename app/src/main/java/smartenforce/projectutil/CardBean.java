package smartenforce.projectutil;


import com.alibaba.fastjson.annotation.JSONField;

public class CardBean {


    /**
     * log_id : 207782130684676543
     * words_result_num : 6
     * direction : 0
     * image_status : unknown
     * words_result : {"住址":{"location":{"width":281,"top":728,"height":35,"left":269},"words":"炫舞时代奇舞飞扬"},"出生":{"location":{"width":286,"top":807,"height":34,"left":269},"words":"20130118"},"姓名":{"location":{"width":143,"top":565,"height":36,"left":266},"words":"腻在心里"},"公民身份号码":{"location":{"width":0,"top":0,"height":0,"left":0},"words":""},"性别":{"location":{"width":26,"top":647,"height":36,"left":274},"words":"女"},"民族":{"location":{"width":0,"top":0,"height":0,"left":0},"words":"汉"}}
     */

    public long log_id;
    public int error_code=0;
    public String error_msg;
    public int words_result_num;
    public int direction;
    public String image_status;
    public WordsResultBean words_result;

    public static class WordsResultBean {


        public String getAddress() {
            return addressBean==null?"":addressBean.words;
        }

        public String  getBirth() {
            return birthBean==null?"":birthBean.words;
        }

        public String getName() {
            return nameBean==null?"":nameBean.words;
        }

        public String getNumber() {
            return numberBean==null?"":numberBean.words;
        }

        public String getSex() {
            return sexBean==null?"":sexBean.words;
        }

        public String getNation() {
            return nationBean==null?"":nationBean.words;
        }

        /**
         * 住址 : {"location":{"width":281,"top":728,"height":35,"left":269},"words":"炫舞时代奇舞飞扬"}
         * 出生 : {"location":{"width":286,"top":807,"height":34,"left":269},"words":"20130118"}
         * 姓名 : {"location":{"width":143,"top":565,"height":36,"left":266},"words":"腻在心里"}
         * 公民身份号码 : {"location":{"width":0,"top":0,"height":0,"left":0},"words":""}
         * 性别 : {"location":{"width":26,"top":647,"height":36,"left":274},"words":"女"}
         * 民族 : {"location":{"width":0,"top":0,"height":0,"left":0},"words":"汉"}
         */
         @JSONField(name = "住址")
        public AddressBean addressBean;
        @JSONField(name = "出生")
        public BirthBean birthBean;
        @JSONField(name = "姓名")
        public NameBean nameBean;
        @JSONField(name = "公民身份号码")
        public NumberBean numberBean;
        @JSONField(name = "性别")
        public SexBean sexBean;
        @JSONField(name = "民族")
        public NationBean nationBean;

        public static class AddressBean {
            /**
             * location : {"width":281,"top":728,"height":35,"left":269}
             * words : 炫舞时代奇舞飞扬
             */

            public LocationBean location;
            public String words;


        }

        public static class BirthBean {
            /**
             * location : {"width":286,"top":807,"height":34,"left":269}
             * words : 20130118
             */

            public LocationBean location;
            public String words;


        }

        public static class NameBean {
            /**
             * location : {"width":143,"top":565,"height":36,"left":266}
             * words : 腻在心里
             */

            public LocationBean location;
            public String words;


        }

        public static class NumberBean {
            /**
             * location : {"width":0,"top":0,"height":0,"left":0}
             * words :
             */

            public LocationBean location;
            public String words;


        }

        public static class SexBean {
            /**
             * location : {"width":26,"top":647,"height":36,"left":274}
             * words : 女
             */

            public LocationBean location;
            public String words;


        }

        public static class NationBean {
            /**
             * location : {"width":0,"top":0,"height":0,"left":0}
             * words : 汉
             */

            public LocationBean location;
            public String words;

        }
    }

    public static class LocationBean {
        /**
         * width : 286
         * top : 807
         * height : 34
         * left : 269
         */

        public int width;
        public int top;
        public int height;
        public int left;
    }



}
