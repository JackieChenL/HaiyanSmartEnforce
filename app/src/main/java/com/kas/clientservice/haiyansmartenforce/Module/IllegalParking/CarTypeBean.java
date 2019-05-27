package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import java.util.List;

/**
 * 车辆类型
 */

public class CarTypeBean {

    /**
     * State : true
     * ErrorCode : 1
     * ErrorMsg :
     * Rtn : [{"CarTypeID":1,"CarTypeCT":"大型客车 ","CreateTimeCT":"2019-05-08 13:57:26","StatusCT":1,"OrderByCT":1},{"CarTypeID":2,"CarTypeCT":"小型客车","CreateTimeCT":"2019-05-08 13:57:38","StatusCT":1,"OrderByCT":1},{"CarTypeID":3,"CarTypeCT":"中型客车","CreateTimeCT":"2019-05-08 13:57:58","StatusCT":1,"OrderByCT":1}]
     * Total : 3
     */

    public boolean State;
    public int ErrorCode;
    public String ErrorMsg;
    public int Total;
    public List<RtnBean> Rtn;

    public static class RtnBean {
        /**
         * CarTypeID : 1
         * CarTypeCT : 大型客车
         * CreateTimeCT : 2019-05-08 13:57:26
         * StatusCT : 1
         * OrderByCT : 1
         */

        public int CarTypeID;
        public String CarTypeCT;
        public String CreateTimeCT;
        public int StatusCT;
        public int OrderByCT;
    }
}
