package smartenforce.bean;


import java.util.List;

import smartenforce.base.BaseBean;


public class AddEnforceBean extends BaseBean {


    /**
     * InATypeIDInA : 3
     * SortTypeID : 1
     * ReferenceNumberInA : eqweq
     * HouseNatureIDWit : 1
     * DurationSei : 15
     * ReasonBasisInA : ewqewqeewqewq
     * ApplicationInA :
     * WarehouseIDSei : 1
     * ExecuteTimeSei : 2017-09-06
     * Durationipt :
     * WithholdGoodsValue : [{"GoodsClassifyIDWit":"1","NameGoC":"交通工具类","NameWit":"1","ModelWit":"1","UnitWit":"1","CountWit":"1","GoodsPicWit":"UploadImage/WithholdGoods/1031/20170907155048/t01b3e37fa50cbba643.jpg|","ShowGoodsPic":"<a href=\"UploadImage/WithholdGoods/1031/20170907155048/t01b3e37fa50cbba643.jpg\" data-lightbox=\"example-set\" data-title=\"t01b3e37fa50cbba643.jpg\"><img src=\"UploadImage/WithholdGoods/1031/20170907155048/thumb_t01b3e37fa50cbba643.jpg\" style=\"border:1px solid #C3D289;border-radius:2px;\" /><\/a>","RemarkWit":"1"},{"GoodsClassifyIDWit":"1","NameGoC":"交通工具类","NameWit":"2","ModelWit":"2","UnitWit":"2","CountWit":"2","GoodsPicWit":"UploadImage/WithholdGoods/1031/20170907155048/企业自然人维护流程表.jpg|","ShowGoodsPic":"<a href=\"UploadImage/WithholdGoods/1031/20170907155048/企业自然人维护流程表.jpg\" data-lightbox=\"example-set\" data-title=\"企业自然人维护流程表.jpg\"><img src=\"UploadImage/WithholdGoods/1031/20170907155048/thumb_企业自然人维护流程表.jpg\" style=\"border:1px solid #C3D289;border-radius:2px;\" /><\/a>","RemarkWit":"2"}]
     * NextInAFlow : 10
     * NextInAAuditor : 1028
     * SuggestCas : 121212
     * ID : 481
     * FlowID : 6
     * TypeSort : generic
     * InAFlowID : 1
     * UserID : 1031
     * InAFlowIDInA : 1
     */

    public int InATypeIDInA;
    public int SortTypeID;
    public String ReferenceNumberInA;
    public int HouseNatureIDWit;
    public String ReasonBasisInA;
    public String ApplicationInA;
    public int WarehouseIDSei;
    public String ExecuteTimeSei;
    public String DepartmentID;
    public String AddressWar;

    public String NextInAFlow;
    public String NextInAAuditor;

    public int DurationSei;



    public String SuggestCas;
    public int ID;
    public String FlowID;
    public String UserID;
    public List<WithholdGoodsValueBean> WithholdGoodsValue;

    public static class WithholdGoodsValueBean extends BaseBean {
        /**
         * GoodsClassifyIDWit : 1
         * NameGoC : 交通工具类
         * NameWit : 1
         * ModelWit : 1
         * UnitWit : 1
         * CountWit : 1
         * GoodsPicWit : UploadImage/WithholdGoods/1031/20170907155048/t01b3e37fa50cbba643.jpg|
         * RemarkWit : 1
         */

        public int GoodsClassifyIDWit;
        public String NameGoC;
        public String NameWit;
        public String ModelWit;
        public String UnitWit;
        public String CountWit;
        public String GoodsPicWit;
        public String RemarkWit;


        public WithholdGoodsValueBean(String GoodsPicWit) {
            this.GoodsPicWit = GoodsPicWit;
        }

        public WithholdGoodsValueBean() {
        }
    }
}
