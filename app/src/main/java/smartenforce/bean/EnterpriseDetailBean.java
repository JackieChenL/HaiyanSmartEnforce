package smartenforce.bean;


import smartenforce.base.BaseBean;

public class EnterpriseDetailBean extends BaseBean {

    /**
     * EnterpriseID : 3
     * OrganizationCodeEnt : 
     * NameEnt : 海宁天泓文化传播有限公司
     * IdentityCardEnt : 
     * LegalrepresentativeEnt : 吴洁非
     * PositionEnt : 
     * MobileEnt : 13706595958
     * AddressEnt : 海宁市硖石街道康乐路177号2号楼二楼
     * PostcodeEnt : 
     * CitAddressEnt : 
     * SexEnt :  
     * NationEnt : 
     * GpsXYEnt : 
     * EntTypeIDEnt : 35
     * EntIndustryIDEnt : 340
     * AreaIDEnt : 2
     * EstablishedDateEnt : 2013-05-29 00:00:00
     * ScopeEnt : 组织、策划文化艺术交流活动；企业形象策划服务；企业宣传策划服务；摄影摄像服务；会展服务；摄影技术咨询服务；平面设计；字牌制作；设计、制作、代理、发布国内各类广告。
     * StatusEnt : 1
     * CreateTimeEnt : 2016-06-03 21:14:36
     * IsSysEnt : 1
     * PhotoEnt : 
     * EntClassifyIDEnt : -1
     * EntUnitPropertIDEnt : -1
     * BusinessLicenseEnt : 
     * UniformSocialCodeEnt : 
     * RegisteredCapitalEnt : 
     * EntOperatingStateEnt : 1
     * SignageEnt : 
     * UserIDEnt : 1
     * EntLabArray : null
     */

    public int EnterpriseID=-1;//企业ID 主键
    public String OrganizationCodeEnt="";//组织机构代码证
    public String NameEnt="";//单位名称
    public String IdentityCardEnt="";//身份证
    public String LegalrepresentativeEnt="";//法定代表人
    public String PositionEnt="";//职务
    public String MobileEnt="";//手机
    public String AddressEnt="";//单位地址
    public String PostcodeEnt="";//邮编
    public String CitAddressEnt="";//住址
    public String SexEnt="";//性别
    public String NationEnt="";//民族
    public String GpsXYEnt="";//坐标
    public int EntTypeIDEnt=-1;//单位属性-已废弃
    public int EntIndustryIDEnt=-1;//单位分类-已废弃
    public int AreaIDEnt=-1;//区域ID
    public String EstablishedDateEnt="";//成立时间
    public String ScopeEnt="";//经营范围
    public int StatusEnt;//状态值--系统值
    public String CreateTimeEnt;//创建时间--系统值
    public int IsSysEnt;//是否是系统导入值--系统值
    public String PhotoEnt="";//照片

    public int EntClassifyIDEnt=-1;//单位分类
    public int EntUnitPropertIDEnt=-1;//单位性质

    public String BusinessLicenseEnt="";//注册号
    public String UniformSocialCodeEnt="";//统一识别代码
    public String RegisteredCapitalEnt="";//注册资金
    public int EntOperatingStateEnt=-1;//单位状态;续存、停止
    public String SignageEnt="";//店招
    public int UserIDEnt=-1;//上传人ID
    public String EntLabArray="";//单位标签
  //其中 CertificateType 为1 代表 统一识别代码 取 UniformSocialCodeEnt
 // 其中 CertificateType 为2 代表 注册号 取 BusinessLicenseEnt
   // 其中 CertificateType 为3 代表 组织机构代码证 取 OrganizationCodeEnt
    public int  CertificateTypeID;


    @Override
    public String toString() {
        return "EnterpriseDetailBean{" +
                "EnterpriseID=" + EnterpriseID +
                ", OrganizationCodeEnt='" + OrganizationCodeEnt + '\'' +
                ", NameEnt='" + NameEnt + '\'' +
                ", IdentityCardEnt='" + IdentityCardEnt + '\'' +
                ", LegalrepresentativeEnt='" + LegalrepresentativeEnt + '\'' +
                ", PositionEnt='" + PositionEnt + '\'' +
                ", MobileEnt='" + MobileEnt + '\'' +
                ", AddressEnt='" + AddressEnt + '\'' +
                ", PostcodeEnt='" + PostcodeEnt + '\'' +
                ", CitAddressEnt='" + CitAddressEnt + '\'' +
                ", SexEnt='" + SexEnt + '\'' +
                ", NationEnt='" + NationEnt + '\'' +
                ", GpsXYEnt='" + GpsXYEnt + '\'' +
                ", EntTypeIDEnt=" + EntTypeIDEnt +
                ", EntIndustryIDEnt=" + EntIndustryIDEnt +
                ", AreaIDEnt=" + AreaIDEnt +
                ", EstablishedDateEnt='" + EstablishedDateEnt + '\'' +
                ", ScopeEnt='" + ScopeEnt + '\'' +
                ", StatusEnt=" + StatusEnt +
                ", CreateTimeEnt='" + CreateTimeEnt + '\'' +
                ", IsSysEnt=" + IsSysEnt +
                ", PhotoEnt='" + PhotoEnt + '\'' +
                ", EntClassifyIDEnt=" + EntClassifyIDEnt +
                ", EntUnitPropertIDEnt=" + EntUnitPropertIDEnt +
                ", BusinessLicenseEnt='" + BusinessLicenseEnt + '\'' +
                ", UniformSocialCodeEnt='" + UniformSocialCodeEnt + '\'' +
                ", RegisteredCapitalEnt='" + RegisteredCapitalEnt + '\'' +
                ", EntOperatingStateEnt=" + EntOperatingStateEnt +
                ", SignageEnt='" + SignageEnt + '\'' +
                ", UserIDEnt=" + UserIDEnt +
                ", EntLabArray='" + EntLabArray + '\'' +
                ", CertificateTypeID=" + CertificateTypeID +
                '}';
    }
}
