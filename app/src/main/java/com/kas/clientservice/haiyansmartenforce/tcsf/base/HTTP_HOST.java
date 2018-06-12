package com.kas.clientservice.haiyansmartenforce.tcsf.base;

/**
 * 网络请求地址
 */
public class HTTP_HOST {

    private static final String URL_BASE="http://hywx.hnzhzf.top/system/theme/credit/";
    /**停车**/
    public static final String URL_PARK = URL_BASE+"TCHandler.ashx";
    /**泊位占用列表 type:{2:全部 1：占用:0：空闲}**/
    public static final String URL_PARK_LIST = URL_BASE+"YZYBWHlist.ashx";
    /**离开**/
    public static final String URL_PARK_EXIT =URL_BASE+ "LK.ashx";
    /**停车收费选择微信支付扫描后上传服务器扣费**/
    //auth_code:{-1:现金正常；-2：异常；其他微信授权码}
    public static final String URL_WXPAY =URL_BASE+ "wxpay.ashx";

   //对账
    public static final String URL_RECONCILIATIONS =URL_BASE+ "Reconciliations.ashx";

   //路段查询
    public static final String URL_ROAD =URL_BASE+ "Road.ashx";

    //泊位列表查询接口
    public static final String URL_BERTHSEARCH =URL_BASE+ "BerthSearch.ashx";

    //泊位详细信息接口
    public static final String URL_BERTHDETAIL=URL_BASE+ "BerthDetail.ashx";

    //督察情况录入接口
    public static final String URL_CHECKADD=URL_BASE+ "ChecksAdd.ashx";




}
