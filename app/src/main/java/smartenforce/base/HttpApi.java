package smartenforce.base;


public class HttpApi {

//    public static final String URL_HOST = "http://192.168.1.7/111";
    public static final String URL_HOST = "http://117.149.146.131:88";

    private static final String MOBILE_BASE = URL_HOST + "/MobileApi/staff/";

    //上传图片，返回地址需要手动添加图片地址头
    public static final String URL_IMG_HEADER = URL_HOST+"/use/";


    private static final String URL_BASE="http://hywx.hnzhzf.top/system/theme/credit/";
    /**停车**/
    public static final String URL_PARK = URL_BASE+"TCHandler.ashx";
    /**泊位占用列表 type:{2:全部 1：占用:0：空闲}**/
    public static final String URL_PARK_LIST = URL_BASE+"YZYBWHlist.ashx";
    //个人查询欠费记录
    public static final String URL_ARREARAGE = URL_BASE+"Arrearage.ashx";
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

    //获取案源列表
    public static final String SOURCE_INVESTLIST_LIST = MOBILE_BASE + "SourceInvestigationList.ashx";
    //获取案件列表
    public static final String CASE_INVESTLIST_LIST = MOBILE_BASE + "CaseInvestigationList.ashx";

    //调查取证详情，巡查查询详情
    public static final String URL_SOURCEDETAIL = MOBILE_BASE + "SourceDetail.ashx";

    //巡查查询列表
    public static final String URL_SOURCELIST = MOBILE_BASE + "SourceList.ashx";

    /**
     * 内部审批辅助信息
     * ExtendType为
     * NextInAFlowWhenAdd(获取新增时下步环节信息)、
     * NextInAFlowWhenCheck(获取审核时下步环节)、
     * NextInAAuditorWhenAdd(获取新增时下步审核人)、
     * NextInAAuditorWhenCheck(获取审核时下步审核人)
     * GetGoodsClassify(获取物品分类)、
     * GetApplicaition(获取申请依据)
     * GetHouseNature(获取保存方式)、
     * GetWarehouse(获取存放地点)
     */
    public static final String URL_APPROVE_EXTEND = MOBILE_BASE + "InternalApproveExtend.ashx";


    // 新增内部审批
    public static final String URL_ADDAPPROVE = MOBILE_BASE + "InternalApproveAdd.ashx";

    // 扣押及解除扣押列表
    public static final String URL_ENFORCELIST = MOBILE_BASE + "InternalEnUnforceList.ashx";

    // 扣押及解除详情
    public static final String URL_ENFORCE_DETAIL = MOBILE_BASE + "InternalEnUnforceDetail.ashx";
    // 扣押物品列表
    public static final String URL_WITHHOLDGOODLIST = MOBILE_BASE + "WithHoldgoodsList.ashx";
    //新加证据判断已经添加图片信息
    public static final String URL_INQUESTPHICHCOUNT = MOBILE_BASE + "InquestPhIchCount.ashx";
    //获取模板接口
    public static final String URL_TEMPLATE_LIST = MOBILE_BASE + "TemplateReferenceList.ashx";

    //物品处理
    public static final String URL_WITHHOLDGOODSHANDLE = MOBILE_BASE + "WithholdGoodsHandle.ashx";

    //登录
    public static final String URL_LOGIN = MOBILE_BASE + "UserLoginCheck.ashx";

    public static final String URL_CHECKUPDATE = MOBILE_BASE + "MobileVersion.ashx?Type=worker";

    //获取大类
    public static final String URL_FIRSTLEVEL_LIST = MOBILE_BASE + "IllegalFirstLevelList.ashx";

    //获取违法行为
    public static final String URL_THIRDLIST = MOBILE_BASE + "IllegalThirdLevelList.ashx";

    //获取企业详情
    public static final String URL_ENTERPRISEDETAIL = MOBILE_BASE + "EnterpriseDetail.ashx";

    public static final String URL_ENTERPRISELIST = MOBILE_BASE + "EnterpriseList.ashx";

    //新增或者修改企业信息
    public static final String URL_ENTERPRISE_SAVE = MOBILE_BASE + "EnterpriseSave.ashx";

    //获取自然人列表
    public static final String URL_CITIZENLIST = MOBILE_BASE + "CitizenList.ashx";

    //新增或者修改自然人信息
    public static final String URL_CITIZEN_SAVE = MOBILE_BASE + "CitizenSave.ashx";

    //身份证解析
    public static final String URL_IDCARDINFO = MOBILE_BASE + "IdCardInfo.ashx";

    //上传图片
    public static final String URL_UPLOADIMG = MOBILE_BASE + "ImgUpload.ashx";

    //巡查新增
    public static final String URL_SOURCEPERSONADD = MOBILE_BASE + "SourcePersonAdd.ashx";

    //案件证据列表
    public static final String URL_CASEINVESTIGATIONALL = MOBILE_BASE + "CaseInvestigationAll.ashx";

    //获取现场勘验详情
    public static final String URL_INQUSTDETAIL = MOBILE_BASE + "InquestDetailWithPhIch.ashx";

    //获取部门下人员列表
    public static final String URL_EMPLOYEE_LIST = MOBILE_BASE + "EmployeeList.ashx";

    //获取部门列表
    public static final String URL_DEPARTMENT_LIST = MOBILE_BASE + "DepartmentList.ashx";

    //获取案件中当事人信息
    public static final String URL_LITIGANT_LIST = MOBILE_BASE + "LitigantList.ashx";

    //详情中证据列表
    public static final String URL_INVESTIGATIONALL  = MOBILE_BASE + "InvestigationAll.ashx";


    //获取pdf地址接口
    public static final String URL_PDFURL = MOBILE_BASE + "PdfOfEvidence.ashx";

    //勘验笔录新增
    public static final String URL_INQUEST_SAVE = MOBILE_BASE + "InquestSave.ashx";
    //勘验笔录删除
    public static final String URL_INQUEST_DEL = MOBILE_BASE + "InquestDel.ashx";

    //照片证据新增
    public static final String URL_PHOTOGRAPH_SAVE = MOBILE_BASE + "PhotographSave.ashx";

    public static final String URL_PHOTOGRAPH_DEL = MOBILE_BASE + "PhotographDel.ashx";

    //平面图证据新增
    public static final String URL_ICHNOGRAPHY_SAVE = MOBILE_BASE + "IchnographySave.ashx";

    public static final String URL_ICHNOGRAPHY_DEL = MOBILE_BASE + "IchnographyDel.ashx";

    //轨迹上传间隔获取
    public static final String URL_TERINTERVALDETAIL = MOBILE_BASE + "TerIntervalDetail.ashx";
    //轨迹上传
    public static final String URL_TRAJECTORYADD = MOBILE_BASE + "TrajectoryAdd.ashx";


    //TODO:获取企业标签
    public static final String URL_ENTERPRISELABELLIST = MOBILE_BASE + "EnterpriseLabelList.ashx";

    // 获取车牌类型
    public static final String URL_CPLX ="http://hywx.hnzhzf.top/system/theme/anjuan/GetCarProp.ashx";


}
