package smartenforce.aty.function3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.TimePickerView;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.CitizenBean;
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.bean.GroupBean;
import smartenforce.bean.InquestBean;
import smartenforce.bean.InspectBean;
import smartenforce.bean.InvestigateBean;
import smartenforce.bean.TemplateModelBean;
import smartenforce.dialog.EditDialog;
import smartenforce.dialog.ListDialog;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ListItemClickLisener;
import smartenforce.projectutil.TimePickerUtil;
import smartenforce.projectutil.WidgetUtil;
import smartenforce.util.DateUtil;
import smartenforce.util.UIUtil;

//现场勘查
public class AddInquestActivity extends ShowPdfActivity {
    private TextView tev_StartTimeInq;
    private EditText edt_AddressInq;
    private TextView tev_WorkPlaceInq;
    private LinearLayout llt_container;

    private TextView tev_EmpNameInq, tev_RecordManInq;

    private TextView tev_model_name;
    //type==1,2对应企业自然人不为空
    private int TYPE;
    private EnterpriseDetailBean enterpriseDetailBean;
    private CitizenBean citizenBean, liveMan, otherMan;

    private InvestigateBean investigateBean;
    private TextView tev_live_man, tev_checked_man, tev_other, tev_model;

    private EditText edt_live_status;


    private RelativeLayout rtl_HaMPositionInq;
    private TextView tev_HaMPositionInq_devider;
    private EditText edt_HaMPositionInq;


    private RelativeLayout rtl_WitWorkPlaceInq;
    private TextView tev_WitWorkPlaceInq_devider;
    private EditText edt_WitWorkPlaceInq;

    private String ids;

    private String modelString = null;

    private TextView modelTev = null;

    private EditText noModelEdt = null;
    private List<TemplateModelBean.RuleBean> ruleList;
    private ListDialog listDialog;

    private List<Object> list = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinquest);
    }

    @Override
    protected void findViews() {
        tev_StartTimeInq = (TextView) findViewById(R.id.tev_StartTimeInq);
        edt_AddressInq = (EditText) findViewById(R.id.edt_AddressInq);
        tev_WorkPlaceInq = (TextView) findViewById(R.id.tev_WorkPlaceInq);
        tev_EmpNameInq = (TextView) findViewById(R.id.tev_EmpNameInq);
        tev_RecordManInq = (TextView) findViewById(R.id.tev_RecordManInq);
        tev_live_man = (TextView) findViewById(R.id.tev_live_man);
        tev_checked_man = (TextView) findViewById(R.id.tev_checked_man);
        tev_other = (TextView) findViewById(R.id.tev_other);
        edt_live_status = (EditText) findViewById(R.id.edt_live_status);
        llt_container = (LinearLayout) findViewById(R.id.llt_container);
        rtl_HaMPositionInq = (RelativeLayout) findViewById(R.id.rtl_HaMPositionInq);
        tev_HaMPositionInq_devider = (TextView) findViewById(R.id.tev_HaMPositionInq_devider);
        edt_HaMPositionInq = (EditText) findViewById(R.id.edt_HaMPositionInq);
        rtl_WitWorkPlaceInq = (RelativeLayout) findViewById(R.id.rtl_WitWorkPlaceInq);
        tev_WitWorkPlaceInq_devider = (TextView) findViewById(R.id.tev_WitWorkPlaceInq_devider);
        edt_WitWorkPlaceInq = (EditText) findViewById(R.id.edt_WitWorkPlaceInq);
        tev_model_name = (TextView) findViewById(R.id.tev_model_name);
        tev_model = (TextView) findViewById(R.id.tev_model);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("新增笔录");
        tev_title_right.setText("提交");
        investigateBean = (InvestigateBean) getIntent().getSerializableExtra("InvestigateBean");
        TYPE = getIntent().getIntExtra("TYPE", -1);
        tev_StartTimeInq.setText(DateUtil.currentTime());
        edt_AddressInq.setText(investigateBean.CSAddress);
        edt_AddressInq.setEnabled(isEmpty(investigateBean.CSAddress));
        tev_RecordManInq.setText(app.NameEmp);
        tev_live_man.setText("无");
        tev_other.setText("无");
        tev_WorkPlaceInq.setText("海宁市综合行政执法局");
        queryCheckedManInfo();
        tev_title_right.setOnClickListener(noFastClickLisener);
        tev_EmpNameInq.setOnClickListener(noFastClickLisener);
        tev_checked_man.setOnClickListener(noFastClickLisener);
        tev_live_man.setOnClickListener(noFastClickLisener);
        tev_other.setOnClickListener(noFastClickLisener);
        tev_model.setOnClickListener(noFastClickLisener);
    }

    private void getTemplate() {
        OkHttpUtils.post().url(HttpApi.URL_TEMPLATE_LIST)
                .addParams("UserID", app.userID)
                .build().execute(new BeanCallBack(aty, null) {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    list.addAll(bean.getResultBeanList(TemplateModelBean.class));
                    if (listDialog == null) {
                        listDialog = new ListDialog(aty, list, new ListItemClickLisener() {
                            @Override
                            public void onItemClick(int P, Object obj) {
                                TemplateModelBean templateBean = (TemplateModelBean) obj;
                                modelString = templateBean.TemplateContentRef;
                                ruleList = templateBean.getRuleList();
                                updateModel();
                            }
                        });
                    }
                    listDialog.show();
                } else {
                    updateModel();
                }


            }
        });

    }


    //被检查人信息
    private void queryCheckedManInfo() {
        OkHttpUtils.post().url(HttpApi.URL_LITIGANT_LIST)
                .addParams("ID", investigateBean.ID + "")
                .addParams("SortType", investigateBean.SortTypeID + "")
                .build().execute(new BeanCallBack(aty, "获取被检查人信息中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    try {
                        InspectBean inspectBean = bean.getResultBeanList(InspectBean.class).get(0);
                        if (TYPE == 1) {
                            enterpriseDetailBean = inspectBean.Enterprise.get(0);
                            tev_checked_man.setText(enterpriseDetailBean.NameEnt);
                        } else if (TYPE == 2) {
                            citizenBean = inspectBean.Citizen.get(0);
                            tev_checked_man.setText(citizenBean.NameCit);
                        }
                        showLiveStatus();
                    } catch (Exception e) {
                        tev_checked_man.setText("无");
                    }
                } else {
                    warningShow("获取被检查人信息失败");
                    tev_checked_man.setText("无");
                }

            }
        });
//        getTemplate();
    }


    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_EmpNameInq:
                    startActivityForResult(new Intent(aty, SelectEmpNameActivity.class), REQUESTCODE.PERSON_SINGLE);
                    break;
                case R.id.tev_checked_man:
                    Intent checkedIntent = new Intent();
                    if (TYPE == 1) {
                        checkedIntent.setClass(aty, InspectEnterpriseActivity.class);
                        checkedIntent.putExtra("EnterpriseDetailBean", enterpriseDetailBean);
                        startActivityForResult(checkedIntent, REQUESTCODE.CHECKED_ENTERPRISE);
                    } else if (TYPE == 2) {
                        checkedIntent.setClass(aty, InspectCitizenActivity.class);
                        checkedIntent.putExtra("CitizenBean", citizenBean);
                        startActivityForResult(checkedIntent, REQUESTCODE.CHECKED_CITIZEN);
                    }
                    updateModel();
                    break;
                case R.id.tev_live_man:
                    Intent liveIntent = new Intent(aty, LiveOrOtherActivity.class);
                    liveIntent.putExtra("TYPE", REQUESTCODE.LIVE_MAN);
                    liveIntent.putExtra("CitizenBean", liveMan);
                    startActivityForResult(liveIntent, REQUESTCODE.LIVE_MAN);
                    break;
                case R.id.tev_other:
                    Intent otherIntent = new Intent(aty, LiveOrOtherActivity.class);
                    otherIntent.putExtra("TYPE", REQUESTCODE.OTHER_MAN);
                    otherIntent.putExtra("CitizenBean", otherMan);
                    startActivityForResult(otherIntent, REQUESTCODE.OTHER_MAN);
                    break;
                case R.id.tev_title_right:
                    check();
                    break;
                case R.id.tev_model:
                    if (list.size() == 0) {
                        getTemplate();
                    } else {
                        if (listDialog == null) {
                            listDialog = new ListDialog(aty, list, new ListItemClickLisener() {
                                @Override
                                public void onItemClick(int P, Object obj) {
                                    TemplateModelBean templateBean = (TemplateModelBean) obj;
                                    modelString = templateBean.TemplateContentRef;
                                    ruleList = templateBean.getRuleList();
                                    updateModel();
                                }
                            });
                        }
                        listDialog.show();
                    }
                    break;
            }

        }
    };


    private void check() {
        if (isEmpty(edt_AddressInq)) {
            warningShow("检查地点不能为空");
        } else if (isEmpty(tev_checked_man)) {
            warningShow("被检查人不能为空");
        } else if (isEmpty(tev_EmpNameInq)) {
            warningShow("协办人不能为空");
        } else if (isEmpty(modelString) && isEmpty(noModelEdt)) {
            warningShow("检查情况不能为空");
        } else if (modelString.contains("{}")||modelString.contains("{请点击录入}")) {
            warningShow("检查情况不完整");
        } else {
            saveInquest();
        }

    }

    private void saveInquest() {
        InquestBean inquestBean = new InquestBean();
        inquestBean.IDInq = investigateBean.ID;
        inquestBean.SortTypeIDInq = investigateBean.SortTypeID;
        inquestBean.StartTimeInq = getText(tev_StartTimeInq);
        inquestBean.EndTimeInq = DateUtil.currentTime();
        inquestBean.AddressInq = getText(edt_AddressInq);
        if (TYPE == 1) {
            inquestBean.NameEntInq = enterpriseDetailBean.NameEnt;
            inquestBean.LegalrepresentativeInq = enterpriseDetailBean.LegalrepresentativeEnt;
            inquestBean.OrganizationCodeInq = enterpriseDetailBean.OrganizationCodeEnt;
            inquestBean.EntCitMobileInq = enterpriseDetailBean.MobileEnt;
            inquestBean.EntCitAddressInq = enterpriseDetailBean.AddressEnt;
            inquestBean.EntCitPostcodeInq = enterpriseDetailBean.PostcodeEnt;
            inquestBean.EntClassifyIDEnt = enterpriseDetailBean.EntClassifyIDEnt;
            inquestBean.LitigantIDInq = enterpriseDetailBean.EnterpriseID;

        } else if (TYPE == 2) {
            inquestBean.NameCitInq = citizenBean.NameCit;
            inquestBean.SexInq = citizenBean.SexCit;
            inquestBean.NationInq = citizenBean.NationCit;
            inquestBean.IdentityCardInq = citizenBean.IdentityCardCit;
            inquestBean.EntCitMobileInq = citizenBean.MobileCit;
            inquestBean.EntCitAddressInq = citizenBean.AddressCit;
            inquestBean.EntCitPostcodeInq = citizenBean.PostcodeCit;
            inquestBean.LitigantIDInq = citizenBean.CitizenID;
        }
        if (liveMan != null) {
            inquestBean.HandleCitizenIDInq = liveMan.CitizenID;
            inquestBean.HaManInq = liveMan.NameCit;
            inquestBean.HaMIdentityCardInq = liveMan.IdentityCardCit;
            inquestBean.HaMPositionInq = getText(edt_HaMPositionInq);
        }
        if (otherMan != null) {
            inquestBean.WitnessCitizenIDInq = otherMan.CitizenID;
            inquestBean.WitManInq = otherMan.NameCit;
            inquestBean.WitWorkPlaceInq = getText(edt_WitWorkPlaceInq);
        }
        inquestBean.RummagerIDInq = ids;
        inquestBean.RecordIDInq = Integer.valueOf(app.userID);
        inquestBean.UserIDInq = investigateBean.UserID;
        inquestBean.PresentSituationInq = getText(edt_live_status);
        inquestBean.FieldConditionInq = WidgetUtil.traversalView(llt_container, true).replaceAll("\\{", "").replaceAll("\\}", "");
        inquestBean.EntOrCitiInq = TYPE;
        String InquestPostData = JSON.toJSONString(inquestBean);
        OkHttpUtils.post().url(HttpApi.URL_INQUEST_SAVE).addParams("InquestPostData", InquestPostData)
                .build().execute(new BeanCallBack(aty, "数据提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("提交成功");
                    finish();
//                    try {
//                        String json = bean.getResultBeanList(String.class).get(0);
//                        JSONObject jo = new JSONObject(json);
//                        int InquestID = jo.getInt("InquestID");
//                        showPdfChoose(InquestID, 9);
//                    } catch (JSONException e) {
//                        finish();
//                    }
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE.PERSON_SINGLE:
                    GroupBean.UserBean userBean = (GroupBean.UserBean) data.getSerializableExtra("UserBean");
                    tev_EmpNameInq.setText(userBean.NameEmp);
                    ids = app.userID + "," + userBean.UserID;
                    break;
                case REQUESTCODE.CHECKED_ENTERPRISE:
                    enterpriseDetailBean = (EnterpriseDetailBean) data.getSerializableExtra("EnterpriseDetailBean");
                    tev_checked_man.setText(enterpriseDetailBean.NameEnt);
                    showLiveStatus();
                    break;

                case REQUESTCODE.CHECKED_CITIZEN:
                    citizenBean = (CitizenBean) data.getSerializableExtra("CitizenBean");
                    tev_checked_man.setText(citizenBean.NameCit);
                    showLiveStatus();
                    break;
                case REQUESTCODE.LIVE_MAN:
                    liveMan = (CitizenBean) data.getSerializableExtra("CitizenBean");
                    tev_live_man.setText(liveMan.NameCit);
                    rtl_HaMPositionInq.setVisibility(View.VISIBLE);
                    tev_HaMPositionInq_devider.setVisibility(View.VISIBLE);
                    showLiveStatus();
                    break;
                case REQUESTCODE.OTHER_MAN:
                    otherMan = (CitizenBean) data.getSerializableExtra("CitizenBean");
                    tev_other.setText(otherMan.NameCit);
                    rtl_WitWorkPlaceInq.setVisibility(View.VISIBLE);
                    tev_WitWorkPlaceInq_devider.setVisibility(View.VISIBLE);
                    showLiveStatus();
                    break;

            }

        }
    }

    // 现场情况文字填充
    private void showLiveStatus() {
        boolean isEmpty_live = getText(tev_live_man).equals("无");
        boolean isEmpty_other = getText(tev_other).equals("无");
        String str = "";
        if (isEmpty_live && isEmpty_other) {
            str = "%1$s到达现场陪同执法人员检查";
            if (TYPE == 2) {
                str = String.format(str, getText(tev_checked_man));
            } else if (TYPE == 1) {
                str = String.format(str, enterpriseDetailBean.LegalrepresentativeEnt);
            }
        } else if (!isEmpty_live) {
            str = "现场负责人%1$s到达现场陪同执法人员检查";
            str = String.format(str, getText(tev_live_man));
        } else if (!isEmpty_other) {
            str = "经联系，当事人拒绝到场，故要求见证人%1$s陪同执法人员检查";
            str = String.format(str, getText(tev_other));
        }
        edt_live_status.setText(str);
        edt_live_status.setSelection(str.length());
    }

    //TODO:模板获取，数据填入
    private void createFieldConditionInqView() {
        llt_container.removeAllViews();
        View childView;
        if (!isEmpty(modelString)) {
            childView = LayoutInflater.from(aty).inflate(R.layout.layout_model, null);
            modelTev = (TextView) childView.findViewById(R.id.tev_input);
            createModelString();
        } else {
            childView = LayoutInflater.from(aty).inflate(R.layout.layout_model_null, null);
            noModelEdt = (EditText) childView.findViewById(R.id.edt_input);

        }

        llt_container.addView(childView);
    }


    //TODO:修改企业或者自然人信息之后，同步修改模板值{}
    private void updateModel() {
        if (!isEmpty(modelString)) {
            int current_index = 0;
            StringBuilder builder = new StringBuilder();
            while (modelString.indexOf("{") != -1) {
                final int startIndex = modelString.indexOf("{");
                final int endIndex = modelString.indexOf("}");
                final String nomalText = modelString.substring(0, startIndex);
                String editText = modelString.substring(startIndex, endIndex + 1);
                builder.append(nomalText);
                final int Index = current_index;
                TemplateModelBean.RuleBean ruleBean = ruleList.get(Index);
                if (ruleBean.extend.equalsIgnoreCase("address")) {
                    editText = "{" + investigateBean.CSAddress + "}";
                } else if (ruleBean.extend.equalsIgnoreCase("signage")) {
                    if (TYPE == 1 && enterpriseDetailBean != null) {
                        editText = "{" + enterpriseDetailBean.SignageEnt + "}";
                    }
                } else if (ruleBean.extend.equalsIgnoreCase("year")) {
                    editText = "{" + Calendar.getInstance().get(Calendar.YEAR) + "}";
                } else if (ruleBean.extend.equalsIgnoreCase("time")) {
                    editText = "{" + DateUtil.getFormatDate(new Date(),DateUtil.YMDH_CHINESE) + "}";
                }else if(ruleBean.extend.equalsIgnoreCase("litigantname")){
                    editText = "{" + getText(tev_checked_man)+ "}";
                }else if (ruleBean.extend.equalsIgnoreCase("addressdep")){
                    editText = "{" + app.AddressDep+ "}";
                }else if (ruleBean.extend.equalsIgnoreCase("namedep")){
                    editText = "{" + app.NameDep+ "}";
                }
                builder.append(editText);
                current_index++;
                modelString = modelString.substring(endIndex + 1, modelString.length());
            }
            if (!isEmpty(modelString)) {
                builder.append(modelString);
            }
            modelString = builder.toString();
        }
        createFieldConditionInqView();
    }


    //导入模板数据时先按照规则拆分字符串
    private void createModelString() {
        String bodyStr = modelString;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int current_index = 0;
        while (bodyStr.indexOf("{") != -1) {
            final int startIndex = bodyStr.indexOf("{");
            final int endIndex = bodyStr.indexOf("}");
            final String nomalText = bodyStr.substring(0, startIndex);
            final String editText = bodyStr.substring(startIndex, endIndex + 1);
            spannableStringBuilder.append(nomalText);
            final String recentStr = spannableStringBuilder.toString();
            final int Index = current_index;
            spannableStringBuilder.append(editText);
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(View widget) {
                    onEditTextClick(Index, recentStr, editText);
                }
            }, recentStr.length(), spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            bodyStr = bodyStr.substring(endIndex + 1, bodyStr.length());
            current_index++;
        }
        if (!isEmpty(bodyStr)) {
            spannableStringBuilder.append(bodyStr);
        }
        modelTev.setText(spannableStringBuilder);
        modelTev.setTextSize(UIUtil.sp2px(aty, 12));
        modelTev.setMovementMethod(LinkMovementMethod.getInstance());
    }


    /**
     * @param postion   第几个可编辑位置
     * @param recentStr 需要保留之前的字符串
     * @param editText  需要替代的字符串{....}
     */
    private void onEditTextClick(int postion, final String recentStr, final String editText) {
        final String hintTxT = editText.substring(1, editText.length() - 1);
        final int len = recentStr.length() + editText.length();
        final TemplateModelBean.RuleBean ruleBean = ruleList.get(postion);
        if (ruleBean.type.equalsIgnoreCase("text")) {
            new EditDialog(aty, ruleBean.explain, hintTxT.equals("请点击录入")?"":hintTxT, "取消", "确定", new EditDialog.BtnClickCallBack() {

                @Override
                public void onClick(int postion, String txt) {
                    if (postion == 1) {
                        modelString = recentStr + "{" + txt + "}" + modelString.substring(len);
                        createModelString();
                    }
                }

            }).showWithEditType(2);

        } else if (ruleBean.type.equalsIgnoreCase("number") || ruleBean.type.equalsIgnoreCase("year")) {
            new EditDialog(aty, ruleBean.explain, hintTxT.equals("请点击录入")?"":hintTxT, "取消", "确定", new EditDialog.BtnClickCallBack() {

                @Override
                public void onClick(int postion, String txt) {
                    if (postion == 1) {
                        modelString = recentStr + "{" + txt + "}" + modelString.substring(len);
                        createModelString();
                    }
                }

            }).showWithEditType(1);

        } else if (ruleBean.type.equalsIgnoreCase("time")) {
            TimePickerView timePickerView = TimePickerUtil.getYMDHTimePicker(aty, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    String txt = DateUtil.getFormatDate(date,DateUtil.YMDH_CHINESE);
                    modelString = recentStr + "{" + txt + "}" + modelString.substring(len);
                    createModelString();
                }
            });
            if(!(hintTxT.equals("请点击录入"))){
                timePickerView.setDate(DateUtil.str2Calendar(hintTxT, DateUtil.YMDH_CHINESE));
            }
            timePickerView.show();

        } else {
            new EditDialog(aty, ruleBean.explain, hintTxT.equals("请点击录入")?"":hintTxT, "取消", "确定", new EditDialog.BtnClickCallBack() {

                @Override
                public void onClick(int postion, String txt) {
                    if (postion == 1) {
                        modelString = recentStr + "{" + txt + "}" + modelString.substring(len);
                        createModelString();
                    }
                }

            }).showWithEditType(2);

        }


    }


}
