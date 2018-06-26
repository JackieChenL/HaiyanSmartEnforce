package smartenforce.bean;


import smartenforce.base.BaseBean;

public class EnforceListBean extends BaseBean {


    /**
     * tn : 1
     * tc : 0
     * InternalApproveID : 19
     * SourceIDInA : -1
     * CaseIDInA : 557
     * InAFlowIDInA : 10
     * InATypeIDInA : 3
     * ApplicationInA : 根据《浙江省取缔无照经营条例》第七条第三项“工商行政管理部门查处无照经营，可以按照规定程序行使下列职权：（三）检查与无照经营有关的场所、物品，查封、扣押与无照经营有关的资料、物品、设备、工具等财物”之
     * ReferenceNumberInA : 444
     * ReasonBasisInA : 案件来源，123
     经初查，初查事实,456
     * DateInA : 1753-01-01 00:00:00
     * BuildDocumentInA :
     * CreateTimeInA : 2018-04-10 22:13:24
     * CaseStatusIDInA : 2
     * UserIDInA : 1031
     * SeizureID : 7
     * WarehouseID : 1
     * ReferenceNumber : 444
     * Duration : 30
     * ExecuteTime : 2018-04-10
     * CreateTime : 2018-04-10 22:13:24
     * NameEmp : 顾力飞
     * NameInF : 中队长审核
     * NameIAT : 扣押申请
     * EndTime : 2018-05-10
     * NameCaS : 提交
     * NameWit : 1
     * CountWit : 1组
     * NameWar : 暂存仓库
     * RemoveOrNot : -1
     * CurrentStateIDWit : 1
     * Number : 海综执立字〔2018〕第6号
     */

    public int tn;
    public int tc;
    public int InternalApproveID;
    public int SourceIDInA;
    public int CaseIDInA;
    public int InAFlowIDInA;
    public int InATypeIDInA;
    public String ApplicationInA;
    public String ReferenceNumberInA;
    public String ReasonBasisInA;
    public String DateInA;
    public String BuildDocumentInA;
    public String CreateTimeInA;
    public int CaseStatusIDInA;
    public int UserIDInA;
    public int SeizureID;
    public int WarehouseID;
    public String ReferenceNumber;
    public int Duration;
    public String ExecuteTime;
    public String CreateTime;
    public String NameEmp;
    public String NameInF;
    public String NameIAT;
    public String EndTime;
    public String NameCaS;
    public String NameWit;
    public String CountWit;
    public String NameWar;
    public int RemoveOrNot;
    public String CurrentStateIDWit;
    public String Number;


    @Override
    public String toString() {
        return "EnforceListBean{" +
                "tn=" + tn +
                ", tc=" + tc +
                ", InternalApproveID=" + InternalApproveID +
                ", SourceIDInA=" + SourceIDInA +
                ", CaseIDInA=" + CaseIDInA +
                ", InAFlowIDInA=" + InAFlowIDInA +
                ", InATypeIDInA=" + InATypeIDInA +
                ", ApplicationInA='" + ApplicationInA + '\'' +
                ", ReferenceNumberInA='" + ReferenceNumberInA + '\'' +
                ", ReasonBasisInA='" + ReasonBasisInA + '\'' +
                ", DateInA='" + DateInA + '\'' +
                ", BuildDocumentInA='" + BuildDocumentInA + '\'' +
                ", CreateTimeInA='" + CreateTimeInA + '\'' +
                ", CaseStatusIDInA=" + CaseStatusIDInA +
                ", UserIDInA=" + UserIDInA +
                ", SeizureID=" + SeizureID +
                ", WarehouseID=" + WarehouseID +
                ", ReferenceNumber='" + ReferenceNumber + '\'' +
                ", Duration=" + Duration +
                ", ExecuteTime='" + ExecuteTime + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", NameEmp='" + NameEmp + '\'' +
                ", NameInF='" + NameInF + '\'' +
                ", NameIAT='" + NameIAT + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", NameCaS='" + NameCaS + '\'' +
                ", NameWit='" + NameWit + '\'' +
                ", CountWit='" + CountWit + '\'' +
                ", NameWar='" + NameWar + '\'' +
                ", RemoveOrNot=" + RemoveOrNot +
                ", CurrentStateIDWit='" + CurrentStateIDWit + '\'' +
                ", Number='" + Number + '\'' +
                '}';
    }
}
