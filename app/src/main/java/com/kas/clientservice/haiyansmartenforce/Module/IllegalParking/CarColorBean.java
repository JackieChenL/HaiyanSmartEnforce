package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import java.util.List;

/**
 * 车身颜色
 */

public class CarColorBean {

    /**
     * State : true
     * ErrorCode : 1
     * ErrorMsg :
     * Rtn : [{"CarBodyColorID":1,"CarBodyColorCBC":"红","CreateTimeCBC":"2019-05-08 13:54:35","StatusCBC":1,"OrderByCBC":1},{"CarBodyColorID":2,"CarBodyColorCBC":"黄","CreateTimeCBC":"2019-05-08 13:54:38","StatusCBC":1,"OrderByCBC":1},{"CarBodyColorID":3,"CarBodyColorCBC":"蓝","CreateTimeCBC":"2019-05-08 13:54:46","StatusCBC":1,"OrderByCBC":1}]
     * Total : 3
     */

    public boolean State;
    public int ErrorCode;
    public String ErrorMsg;
    public int Total;
    public List<RtnBean> Rtn;

    public static class RtnBean {
        /**
         * CarBodyColorID : 1
         * CarBodyColorCBC : 红
         * CreateTimeCBC : 2019-05-08 13:54:35
         * StatusCBC : 1
         * OrderByCBC : 1
         */

        public int CarBodyColorID;
        public String CarBodyColorCBC;
        public String CreateTimeCBC;
        public int StatusCBC;
        public int OrderByCBC;
    }
}
