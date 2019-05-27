package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import java.util.List;

/**
 * 车牌类型
 */

public class CPLXBean {


    /**
     * State : true
     * ErrorCode : 1
     * ErrorMsg :
     * Rtn : [{"CarPlateTypeID":1,"CarPlateTypeCPT":"号牌种类1","CreateTimeCPT":"2019-05-08 13:52:24","StatusCPT":1,"OrderByCPT":1},{"CarPlateTypeID":2,"CarPlateTypeCPT":"号牌种类2","CreateTimeCPT":"2019-05-08 13:52:25","StatusCPT":1,"OrderByCPT":1},{"CarPlateTypeID":3,"CarPlateTypeCPT":"号牌种类3","CreateTimeCPT":"2019-05-08 13:52:26","StatusCPT":1,"OrderByCPT":1},{"CarPlateTypeID":4,"CarPlateTypeCPT":"号牌种类4","CreateTimeCPT":"2019-05-08 13:52:27","StatusCPT":1,"OrderByCPT":1},{"CarPlateTypeID":5,"CarPlateTypeCPT":"号牌种类5","CreateTimeCPT":"2019-05-08 13:52:29","StatusCPT":1,"OrderByCPT":1}]
     * Total : 5
     */

    public boolean State;
    public int ErrorCode;
    public String ErrorMsg;
    public int Total;
    public List<RtnBean> Rtn;

    public static class RtnBean {
        /**
         * CarPlateTypeID : 1
         * CarPlateTypeCPT : 号牌种类1
         * CreateTimeCPT : 2019-05-08 13:52:24
         * StatusCPT : 1
         * OrderByCPT : 1
         */

        public int CarPlateTypeID;
        public String CarPlateTypeCPT;
        public String CreateTimeCPT;
        public int StatusCPT;
        public int OrderByCPT;
    }
}
