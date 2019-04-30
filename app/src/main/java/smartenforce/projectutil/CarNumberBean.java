package smartenforce.projectutil;


import java.util.List;

public class CarNumberBean {


    /**
     * errno : 0
     * msg : success
     * data : {"log_id":"5327722537189137631","words_result":{"color":"green","number":"苏AD12267","probability":[1,0.9999977350235,0.99999630451202,0.99999868869781,0.99998331069946,0.99999988079071,0.9531751871109,0.99922955036163],"vertexes_location":[{"y":223,"x":170},{"y":223,"x":282},{"y":256,"x":282},{"y":256,"x":170}]}}
     */

    public int error_code = 0;
    public String error_msg;

        /**
         * log_id : 5327722537189137631
         * words_result : {"color":"green","number":"苏AD12267","probability":[1,0.9999977350235,0.99999630451202,0.99999868869781,0.99998331069946,0.99999988079071,0.9531751871109,0.99922955036163],"vertexes_location":[{"y":223,"x":170},{"y":223,"x":282},{"y":256,"x":282},{"y":256,"x":170}]}
         */

        public String log_id;
        public WordsResultBean words_result;

        public static class WordsResultBean {
            /**
             * color : green
             * number : 苏AD12267
             * probability : [1,0.9999977350235,0.99999630451202,0.99999868869781,0.99998331069946,0.99999988079071,0.9531751871109,0.99922955036163]
             * vertexes_location : [{"y":223,"x":170},{"y":223,"x":282},{"y":256,"x":282},{"y":256,"x":170}]
             */

            public String color;
            public String number;
            public List<Integer> probability;
            public List<VertexesLocationBean> vertexes_location;

            public static class VertexesLocationBean {
                /**
                 * y : 223
                 * x : 170
                 */

                public int y;
                public int x;
            }
        }

}
