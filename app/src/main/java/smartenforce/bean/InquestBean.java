package smartenforce.bean;


import smartenforce.base.BaseBean;

public class InquestBean extends BaseBean {
    public int InquestID = -1;
    public int IDInq;
    public int SortTypeIDInq;

    public String EnclosureInq = "";
    public String BuildDocumentInq = "";
    public String StartTimeInq="";
    public String EndTimeInq="";
    public String AddressInq="";

    public String EntCitMobileInq="";
    public String EntCitAddressInq="";
    public String EntCitPostcodeInq="";

    //企业
    public String NameEntInq="";
    public String LegalrepresentativeInq="";
    public String OrganizationCodeInq="";
    //如果是企业，需要把企业分类赋值
    public int EntClassifyIDEnt=-1;
    public int EntUnitPropertIDEnt;
    public int EntOrCitiInq;
    public int LitigantIDInq;

    //自然人
    public String NameCitInq="";
    public String SexInq="";
    public String NationInq="";
    public String IdentityCardInq="";

    //现场负责人
    public int HandleCitizenIDInq = -1;
    public String HaManInq = "无";
    public String HaMPositionInq = "无";
    public String HaMIdentityCardInq = "无";

    //其他见证人
    public int WitnessCitizenIDInq = -1;
    public String WitManInq = "无";
    public String WitWorkPlaceInq = "无";

    //检查人员
    public String RummagerIDInq;
    //记录人
    public int RecordIDInq;

    //通知当事人到场情况
    public String PresentSituationInq="";
    //    检查情况
    public String FieldConditionInq="";

    public int UserIDInq;

    //上传时不需要，只在查询详情展示时显示
    public String RecordMan;
    public String RummagerName;



}
