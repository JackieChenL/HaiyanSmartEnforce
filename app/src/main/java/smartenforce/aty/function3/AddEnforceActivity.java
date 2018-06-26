package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.pickerview.TimePickerView;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import smartenforce.adapter.ShowTitleAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.AddEnforceBean;
import smartenforce.bean.InvestigateDetailBean;
import smartenforce.bean.NameIdValueBean;
import smartenforce.bean.NextInfoBean;
import smartenforce.dialog.EditDialog;
import smartenforce.dialog.ListDialog;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.MyTextwatcher;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;
import smartenforce.intf.ListItemClickLisener;
import smartenforce.projectutil.TimePickerUtil;
import smartenforce.util.DateUtil;
import smartenforce.widget.FullyLinearLayoutManager;

import static android.view.View.FOCUS_DOWN;

//强制措施
public class AddEnforceActivity extends ShowPdfActivity {
    private AlertView alertView_sqlx;
    private TextView tev_sqlx, tev_address, tev_start_time, tev_end_time;
    private ImageView imv_add;
    private EditText edt_describe, edt_suggest, edt_referNumber;

    private TextView tev_sqyj;

    private String[] array_sqlx;
    private TimePickerView timePickerView;


    private RecyclerView rcv_list;
    private ScrollView scv;

    private ShowTitleAdapter adapter;
    //记录进入物品详情位置，-1代表添加物品
    private int ClickPostion = -1;

    private List<Object> list = new ArrayList<Object>();

    private List<Object> wareHouseList = new ArrayList();

    private ListDialog listDialog;
    private EditDialog editDialog;

    private int WareHouseID = -1;

    //获取到下一环节审核人具体信息之后可以调用提交申请
    private NextInfoBean nextInfoBean;
    //数据如果检查完整可以赋值
    private AddEnforceBean addEnforceBean;

    private int InATypeIDInA = -1;
    private InvestigateDetailBean investigateDetailBean;

    private TextView tev_referNumber_title;
    private int YEAR;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addenforce);
    }

    @Override
    protected void findViews() {
        tev_sqlx = (TextView) findViewById(R.id.tev_sqlx);
        tev_sqyj = (TextView) findViewById(R.id.tev_sqyj);
        tev_referNumber_title = (TextView) findViewById(R.id.tev_referNumber_title);
        tev_address = (TextView) findViewById(R.id.tev_address);
        tev_start_time = (TextView) findViewById(R.id.tev_start_time);
        tev_end_time = (TextView) findViewById(R.id.tev_end_time);
        imv_add = (ImageView) findViewById(R.id.imv_add);
        edt_describe = (EditText) findViewById(R.id.edt_describe);
        edt_referNumber = (EditText) findViewById(R.id.edt_referNumber);
        edt_suggest = (EditText) findViewById(R.id.edt_suggest);
        rcv_list = (RecyclerView) findViewById(R.id.rcv_list);
        scv = (ScrollView) findViewById(R.id.scv);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("新增笔录");
        tev_title_right.setText("提交");
        investigateDetailBean = (InvestigateDetailBean) getIntent().getSerializableExtra("InvestigateDetailBean");
        YEAR = Calendar.getInstance().get(Calendar.YEAR);
        tev_start_time.setText(DateUtil.getFormatDate(new Date(), DateUtil.YMD));
        timePickerView = TimePickerUtil.getYMDTimePicker(aty, watcher);
        tev_sqlx.addTextChangedListener(watcher);
        array_sqlx = getResources().getStringArray(R.array.sqlx);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(aty);
        edt_describe.setText(investigateDetailBean.ContentSou);
        if (investigateDetailBean.NameFiL.equals("工商管理")) {
            tev_sqlx.setText(array_sqlx[0]);
        } else {
            tev_sqlx.setText(array_sqlx[1]);
        }

        rcv_list.setLayoutManager(manager);
        tev_sqlx.setOnClickListener(noFastClickLisener);
        tev_address.setOnClickListener(noFastClickLisener);
        tev_start_time.setOnClickListener(noFastClickLisener);
        imv_add.setOnClickListener(noFastClickLisener);
        tev_title_right.setOnClickListener(noFastClickLisener);

    }

    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_sqlx:
                    if (alertView_sqlx == null) {
                        alertView_sqlx = getShowAlert("申请类型", array_sqlx, tev_sqlx);
                    }
                    alertView_sqlx.show();
                    break;
                case R.id.tev_address:
                    getWareHouse();
                    break;
                case R.id.tev_start_time:
                    timePickerView.setDate(DateUtil.str2Calendar(getText(tev_start_time), DateUtil.YMDHM));
                    timePickerView.show(tev_start_time);
                    break;
                case R.id.imv_add:
                    takePhoto(REQUESTCODE.CAMERA);
                    break;
                case R.id.tev_title_right:
                    doCheck();
                    break;


            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE.CAMERA) {
                ClickPostion = -1;
                startActivityForResult(new Intent(aty, AddGoodsActivity.class).putExtra("WithholdGoodsValueBean", new AddEnforceBean.WithholdGoodsValueBean(file.getAbsolutePath())), REQUESTCODE.ADD_GOODS);
            } else if (requestCode == REQUESTCODE.ADD_GOODS) {
                AddEnforceBean.WithholdGoodsValueBean goodsValueBean = (AddEnforceBean.WithholdGoodsValueBean) data.getSerializableExtra("WithholdGoodsValueBean");
                if (ClickPostion == -1) {
                    list.add(0, goodsValueBean);
                } else {
                    list.set(ClickPostion, goodsValueBean);
                }
                if (adapter == null) {
                    adapter = new ShowTitleAdapter(list, aty);
                    adapter.setListener(new ItemListener() {
                        @Override
                        public void onItemClick(int P) {
                            ClickPostion = P;
                            AddEnforceBean.WithholdGoodsValueBean goodsValueBean = (AddEnforceBean.WithholdGoodsValueBean) list.get(P);
                            startActivityForResult(new Intent(aty, AddGoodsActivity.class).putExtra("WithholdGoodsValueBean", goodsValueBean), REQUESTCODE.ADD_GOODS);
                        }
                    });
                    rcv_list.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                scv.fullScroll(FOCUS_DOWN);
            }
        } else if (requestCode == REQUESTCODE.ADD_GOODS && resultCode == 100) {
            if (ClickPostion != -1) {
                list.remove(ClickPostion);
                adapter.notifyDataSetChanged();
            }


        }
    }

    MyTextwatcher watcher = new MyTextwatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            String sqlx = getText(tev_sqlx);
            if (isEmpty(sqlx))
                return;
            Calendar calendar = DateUtil.str2Calendar(getText(tev_start_time), DateUtil.YMD);
            if (sqlx.equals("扣押申请")) {
                InATypeIDInA = 3;
                tev_sqyj.setText("根据《浙江省取缔无照经营条例》第七条第三项“工商行政管理部门查处无照经营，可以按照规定程序行使下列职权：（三）检查与无照经营有关的场所、物品，查封、扣押与无照经营有关的资料、物品、设备、工具等财物”之规定，对有关财物（物品清单）实施扣押");
                tev_referNumber_title.setText("海综执扣字[" + YEAR + "]第");
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 29);
            } else if (sqlx.equals("先行登记")) {
                InATypeIDInA = 6;
                tev_sqyj.setText("根据《中华人民共和国行政处罚法》第三十七条第二款“行政机关在收集证据时，可以采取抽样取证的方法；在证据可能灭失或者以后难以取得的情况下，经行政机关负责人批准，可以先行登记保存，并应当在七日内及时作出处理决定，在此期间，当事人或者有关人员不得销毁或者转移证据。”之规定，对有关证据（物品清单）实施先行登记保存");
                tev_referNumber_title.setText("海综执登存字[" + YEAR + "]第");
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 6);
            }
            tev_end_time.setText(DateUtil.getFormatDate(calendar.getTime(),DateUtil.YMD));
        }

    };


    //获取新增时下步环节信息
    private void getNextRequestID() {
        OkHttpUtils.post().url(HttpApi.URL_APPROVE_EXTEND)
                .addParams("ExtendType", "NextInAFlowWhenAdd")
                .addParams("UserID", app.userID)
                .addParams("DepartmentID", app.DepartmentID)
                .build().execute(new BeanCallBack(aty, "提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total > 0) {
                    try {
                        String json = bean.getResultBeanList(String.class).get(0);
                        JSONObject jo = new JSONObject(json);
                        String nextRequestID = jo.getString("id");
                        getNextInfo(nextRequestID);
                    } catch (JSONException e) {
                        warningShow("获取下步环节信息错误");
                    }
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });

    }

    //获取新增时下步审核人
    private void getNextInfo(String nextInAFlowID) {
        OkHttpUtils.post().url(HttpApi.URL_APPROVE_EXTEND)
                .addParams("ExtendType", "NextInAAuditorWhenAdd")
                .addParams("nextInAFlowID", nextInAFlowID)
                .addParams("UserID", app.userID)
                .addParams("DepartmentID", app.DepartmentID)
                .build().execute(new BeanCallBack(aty, "提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total > 0) {
                    nextInfoBean = bean.getResultBeanList(NextInfoBean.class).get(0);
                    addEnforceBean.NextInAFlow = nextInfoBean.InAFlowID;
                    addEnforceBean.NextInAAuditor = nextInfoBean.UserID;
                    doSubmit();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });


    }

    //数据提交
    private void doSubmit() {
        String InAPostData = JSON.toJSONString(addEnforceBean);
        OkHttpUtils.post().url(HttpApi.URL_ADDAPPROVE)
                .addParams("InAPostData", InAPostData)
                .build().execute(new BeanCallBack(aty, "提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("提交成功");
                    finish();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });


    }

    //数据完整性校验
    private void doCheck() {
        String sqlx = getText(tev_sqlx);
        String cfdd = getText(tev_address);
        String referNumber = getText(edt_referNumber);
        if (isEmpty(sqlx)) {
            warningShow("申请类型不能为空");
        } else if (isEmpty(cfdd)) {
            warningShow("存放地点不能为空");
        } else if (isEmpty(referNumber)) {
            warningShow("文书编号不能为空");
        } else if (list.size() == 0) {
            warningShow("扣押物品不能为空");
        } else {
            docheckWarehouse();
        }
    }


    //获取仓库
    private void getWareHouse() {
        if (wareHouseList.size() == 0) {
            OkHttpUtils.post().url(HttpApi.URL_APPROVE_EXTEND)
                    .addParams("DepartmentID", app.DepartmentID)
                    .addParams("UserID", app.userID)
                    .addParams("ExtendType", "GetWarehouse")
                    .build().execute(new BeanCallBack(aty, "获取仓库中") {
                @Override
                public void handleBeanResult(NetResultBean bean) {
                    wareHouseList.clear();
                    if (bean.State) {
                        List<NameIdValueBean> list = bean.getResultBeanList(NameIdValueBean.class);
                        wareHouseList.addAll(list);
                        wareHouseList.add(new NameIdValueBean("其他", -1));
                        if (listDialog == null) {
                            listDialog = new ListDialog(aty, wareHouseList, new ListItemClickLisener() {
                                @Override
                                public void onItemClick(int P, Object obj) {
                                    NameIdValueBean nameIdValueBean = (NameIdValueBean) obj;
                                    WareHouseID = nameIdValueBean.id;
                                    if (WareHouseID > 0) {
                                        tev_address.setText(nameIdValueBean.name);
                                    } else {
                                        showAddWareHouseDialog();
                                    }

                                }
                            });
                        }
                        listDialog.setTitle("请选择仓库");
                        listDialog.show();
                    } else {
                        warningShow(bean.ErrorMsg);
                    }
                }
            });

        } else {
            listDialog.show();

        }

    }

    private void showAddWareHouseDialog() {

        if (editDialog == null) {
            editDialog = new EditDialog(aty, "请输入仓库地址", "", "取消", "确定", new EditDialog.BtnClickCallBack() {
                @Override
                public void onClick(int postion, String txt) {
                    if (postion == 1) {
                        tev_address.setText(txt);
                    }
                }
            });
        }
        editDialog.showWithEditType(2);


    }


    private void docheckWarehouse() {
        addEnforceBean = new AddEnforceBean();
        List<AddEnforceBean.WithholdGoodsValueBean> goodslist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            goodslist.add((AddEnforceBean.WithholdGoodsValueBean) list.get(i));
        }
        addEnforceBean.WithholdGoodsValue = goodslist;
        addEnforceBean.ExecuteTimeSei = getText(tev_start_time);
        addEnforceBean.ApplicationInA = getText(tev_sqyj);
        addEnforceBean.ReasonBasisInA = getText(edt_describe);
        addEnforceBean.SuggestCas = getText(edt_suggest);
        addEnforceBean.HouseNatureIDWit = 1;
        addEnforceBean.WarehouseIDSei = WareHouseID;
        addEnforceBean.DepartmentID=app.DepartmentID;
        addEnforceBean.AddressWar=getText(tev_address);
        addEnforceBean.InATypeIDInA = InATypeIDInA;
        addEnforceBean.DurationSei=InATypeIDInA==3?29:6;
        addEnforceBean.UserID = app.userID;
        addEnforceBean.FlowID = investigateDetailBean.SourceFlowIDSou;
        addEnforceBean.ID = investigateDetailBean.SourceID;
        addEnforceBean.SortTypeID = investigateDetailBean.SouTypeIDSou;
        addEnforceBean.ReferenceNumberInA = getText(tev_referNumber_title) + getText(edt_referNumber) + "号";
        getNextRequestID();
    }

}
