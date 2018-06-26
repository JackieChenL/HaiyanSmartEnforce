package smartenforce.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.List;

import smartenforce.base.BaseBean;


public class TemplateModelBean extends BaseBean {


    /**
     * NameReT : 检查情况
     * TemplateContentRef : 1.现场位于{}，店铺招牌为{}。2.现场所见：{}。 3.经测量，上述物品超出门窗的面积为{}平方米，其中{}。4.{}5.执法队员现场送达了《海宁市综合行政执法局责令改正通知书》（海综执责改字〔{}〕第{}号），责令其在{}时前改正违法行为，并停止越门经营行为。
     * rule : [{"extend":"address","modify":"1","type":"text"},{"extend":"signage","modify":"1","type":"text"},{"extend":"","modify":"1","type":"text"},{"extend":"","modify":"1","type":"number"},{"extend":"","modify":"1","type":"text"},{"extend":"","modify":"1","type":"text"},{"extend":"","modify":"1","type":"date"},{"extend":"","modify":"1","type":"number"},{"extend":"","modify":"1","type":"time"}]
     */

    public String NameReT;
    public String NameRef;
    public String TemplateContentRef;
    public String RulesRef;

    public List<RuleBean> getRuleList() {
        if (TextUtils.isEmpty(RulesRef)) {
            return null;
        } else {
            String array = RulesRef.replaceAll("\\\\", "");
            return JSON.parseArray(array, RuleBean.class);
        }


    }


    public static class RuleBean {
        /**
         * extend : address
         * modify : 1
         * type : text
         */

        public String extend;
        public String modify;
        public String type;
        public String explain;
    }
}
