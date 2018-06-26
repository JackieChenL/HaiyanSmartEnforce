package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.adapter.InquestDetailAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.AddGraphBean;
import smartenforce.bean.AddPictureBean;
import smartenforce.bean.InquestBean;
import smartenforce.bean.InquestItemDetaiBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;
import smartenforce.projectutil.FileDownLoadUtil;
import smartenforce.widget.FullyLinearLayoutManager;

public class InquestItemDetailActivity extends ShowTitleActivity {
    private int ID;
    private InquestBean bean;
    private final  int UpdateRequestID=100;
    private RecyclerView rcv_list;
    private InquestDetailAdapter adapter;
    private List<Object> objList = new ArrayList<>();
    private TextView tev_StartTimeInq, tev_EndTimeInq, tev_AddressInq;
    private LinearLayout llt_citizen;
    private TextView tev_Name_cit, tev_sex, tev_nation, tev_card_cit, tev_mobile_cit, tev_address_cit, tev_postcode_cit;


    private LinearLayout llt_enterprise;
    private TextView tev_operator_title, tev_address_title;
    private TextView tev_name_ent, tev_operator, tev_license, tev_mobile, tev_address_ent, tev_postcode_ent;

    private TextView tev_HaManInq, tev_HaMPositionInq, tev_HaMIdentityCardInq;
    private TextView tev_WitManInq, tev_WitWorkPlaceInq;

    private TextView tev_Rummager, tev_Record, tev_PresentSituationInq, tev_FieldConditionInq;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquest_detail);
    }

    @Override
    protected void findViews() {
        tev_StartTimeInq = (TextView) findViewById(R.id.tev_StartTimeInq);
        tev_EndTimeInq = (TextView) findViewById(R.id.tev_EndTimeInq);
        tev_AddressInq = (TextView) findViewById(R.id.tev_AddressInq);
        llt_citizen = (LinearLayout) findViewById(R.id.llt_citizen);
        tev_Name_cit = (TextView) findViewById(R.id.tev_Name_cit);
        tev_sex = (TextView) findViewById(R.id.tev_sex);
        tev_nation = (TextView) findViewById(R.id.tev_nation);
        tev_card_cit = (TextView) findViewById(R.id.tev_card_cit);
        tev_mobile_cit = (TextView) findViewById(R.id.tev_mobile_cit);
        tev_address_cit = (TextView) findViewById(R.id.tev_address_cit);
        tev_postcode_cit = (TextView) findViewById(R.id.tev_postcode_cit);
        llt_enterprise = (LinearLayout) findViewById(R.id.llt_enterprise);
        tev_name_ent = (TextView) findViewById(R.id.tev_name_ent);
        tev_operator = (TextView) findViewById(R.id.tev_operator);
        tev_license = (TextView) findViewById(R.id.tev_license);
        tev_mobile = (TextView) findViewById(R.id.tev_mobile);
        tev_address_ent = (TextView) findViewById(R.id.tev_address_ent);
        tev_postcode_ent = (TextView) findViewById(R.id.tev_postcode_ent);
        tev_operator_title = (TextView) findViewById(R.id.tev_operator_title);
        tev_address_title = (TextView) findViewById(R.id.tev_address_title);
        tev_HaManInq = (TextView) findViewById(R.id.tev_HaManInq);
        tev_HaMPositionInq = (TextView) findViewById(R.id.tev_HaMPositionInq);
        tev_HaMIdentityCardInq = (TextView) findViewById(R.id.tev_HaMIdentityCardInq);
        tev_WitManInq = (TextView) findViewById(R.id.tev_WitManInq);
        tev_WitWorkPlaceInq = (TextView) findViewById(R.id.tev_WitWorkPlaceInq);
        tev_Rummager = (TextView) findViewById(R.id.tev_Rummager);
        tev_Record = (TextView) findViewById(R.id.tev_Record);
        tev_PresentSituationInq = (TextView) findViewById(R.id.tev_PresentSituationInq);
        tev_FieldConditionInq = (TextView) findViewById(R.id.tev_FieldConditionInq);

        rcv_list = (RecyclerView) findViewById(R.id.rcv_list);
        rcv_list.setLayoutManager(new FullyLinearLayoutManager(aty));
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("详情");
        ID = getIntent().getIntExtra("ID", -1);
        getInfoMation();
    }


    //获取详情
    private void getInfoMation() {
        OkHttpUtils.post().url(HttpApi.URL_INQUSTDETAIL).addParams("InquestID", ID + "")
                .build().execute(new BeanCallBack(aty, "获取详情中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.getResultBeanList(InquestItemDetaiBean.class) != null) {
                    InquestItemDetaiBean inQuestItemDetaiBean = bean.getResultBeanList(InquestItemDetaiBean.class).get(0);
                    fillContent(inQuestItemDetaiBean);
                } else {
                    warningShow("获取详情失败");
                }

            }
        });
    }


    private void downLoadPdf(int id, int Type) {
        FileDownLoadUtil.doGetPdfUrl(aty, id, Type, app.userID, new FileDownLoadUtil.onDownLoadCallback() {
            @Override
            public void onErroMsg(String msg) {
                warningShow(msg);
            }

            @Override
            public void onSuccess() {
            }
        });
    }

    private void fillContent(InquestItemDetaiBean inQuestItemDetaiBean) {
        objList.clear();
        if (inQuestItemDetaiBean.Photograph != null && inQuestItemDetaiBean.Photograph.size() > 0) {
            objList.addAll(inQuestItemDetaiBean.Photograph);
        }
        if (inQuestItemDetaiBean.Ichnography != null && inQuestItemDetaiBean.Ichnography.size() > 0) {
            objList.addAll(inQuestItemDetaiBean.Ichnography);
        }
        if (adapter == null) {
            adapter = new InquestDetailAdapter(objList, aty);
            adapter.setOnItemClickListener(new InquestDetailAdapter.OnItemClickListener() {
                @Override
                public void onPrintClick(int id, int type) {
                    downLoadPdf(id, type);
                }

                @Override
                public void onEditClick(int id, int postion) {
                    int Type = adapter.getItemViewType(postion);
                    Object obj=objList.get(postion);
                    if (Type == adapter.TYPE_PICTURE) {
                        AddPictureBean pictureBean=(AddPictureBean)obj;
                        Intent intent=new Intent(aty,AddPictureActivity.class);
                        intent.putExtra("AddPictureBean",pictureBean);
                        startActivityForResult(intent,UpdateRequestID);
                    } else if (Type == adapter.TYPE_GRAPH) {
                        AddGraphBean graphBean=(AddGraphBean)obj;
                        Intent intent=new Intent(aty,AddGraphActivity.class);
                        intent.putExtra("AddGraphBean",graphBean);
                        startActivityForResult(intent,UpdateRequestID);
                    }

                }

                @Override
                public void onDelClick(final int id,final int postion) {
                    int Type = adapter.getItemViewType(postion);
                    if (Type == adapter.TYPE_PICTURE) {
                        warningShowAndAction("请确认要删除该现场照片证据吗?", 100, new ItemListener() {
                            @Override
                            public void onItemClick(int P) {
                                delPhotoGraph(postion,id);
                            }
                        });
                    } else if (Type == adapter.TYPE_GRAPH) {
                        warningShowAndAction("请确认要删除该平面图证据吗?", 100, new ItemListener() {
                            @Override
                            public void onItemClick(int P) {
                                delIchGraph(postion,id);
                            }
                        });

                    }

                }
            });
            rcv_list.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        bean = inQuestItemDetaiBean.Inquet.get(0);
        tev_StartTimeInq.setText(bean.StartTimeInq);
        tev_EndTimeInq.setText(bean.EndTimeInq);
        tev_AddressInq.setText(bean.AddressInq);
        if (bean.EntOrCitiInq == 1) {
            llt_enterprise.setVisibility(View.VISIBLE);
            tev_name_ent.setText(bean.NameEntInq);
            tev_operator.setText(bean.LegalrepresentativeInq);
            tev_license.setText(bean.OrganizationCodeInq);
            tev_mobile.setText(bean.EntCitMobileInq);
            tev_address_ent.setText(bean.EntCitAddressInq);
            tev_postcode_ent.setText(bean.EntCitPostcodeInq);
            tev_operator_title.setText(bean.EntUnitPropertIDEnt == 1 ? "经营者" : "法定代表人");
            tev_address_title.setText(bean.EntUnitPropertIDEnt == 1 ? "经营场所" : "住所");
        } else if (bean.EntOrCitiInq == 2) {
            llt_citizen.setVisibility(View.VISIBLE);
            tev_Name_cit.setText(bean.NameCitInq);
            tev_sex.setText(bean.SexInq);
            tev_nation.setText(bean.NationInq);
            tev_card_cit.setText(bean.IdentityCardInq);
            tev_mobile_cit.setText(bean.EntCitMobileInq);
            tev_address_cit.setText(bean.EntCitAddressInq);
            tev_postcode_cit.setText(bean.EntCitPostcodeInq);
        }

        tev_HaManInq.setText(bean.HaManInq);
        tev_HaMPositionInq.setText(bean.HaMPositionInq);
        tev_HaMIdentityCardInq.setText(bean.HaMIdentityCardInq);

        tev_WitManInq.setText(bean.WitManInq);
        tev_WitWorkPlaceInq.setText(bean.WitWorkPlaceInq);
        tev_Rummager.setText(bean.RummagerName);
        tev_Record.setText(bean.RecordMan);
        tev_PresentSituationInq.setText(bean.PresentSituationInq);
        tev_FieldConditionInq.setText(bean.FieldConditionInq);
        tev_title_right.setText("操作");
        tev_title_right.setOnClickListener(new NoFastClickLisener() {
            @Override
            public void onNofastClickListener(View v) {
                showAction();
            }
        });

    }

    //下载，删除，修改
    private void showAction() {
        new AlertView("请选择操作", null, null, null, new String[]{"下载", "删除"}, aty, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                switch (position) {
                    case 0:
                        downLoadPdf(bean.InquestID, 9);
                        break;
//                    case 1:
////                       TODO
//                        warningShowAndAction("修改操作开发中", 500, new ItemListener() {
//                            @Override
//                            public void onItemClick(int P) {
//                                delAction();
//                            }
//                        });
//                        break;
                    case 1:
                        warningShowAndAction("请确认要删除该笔录吗?", 500, new ItemListener() {
                            @Override
                            public void onItemClick(int P) {
                                delAction();
                            }
                        });
                        break;


                }
            }
        }).show();
    }


    private void delAction() {
        OkHttpUtils.post().url(HttpApi.URL_INQUEST_DEL)
                .addParams("InquestID", ID + "")
                .addParams("UserID", app.userID)
                .build().execute(new BeanCallBack(aty, "删除中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("删除成功");
                    finish();
                } else {
                    warningShow("删除失败");
                }

            }
        });
    }

    private void delPhotoGraph(final int Postion, int PhotographID) {
        OkHttpUtils.post().url(HttpApi.URL_PHOTOGRAPH_DEL)
                .addParams("PhotographID", PhotographID + "")
                .addParams("UserID", app.userID)
                .build().execute(new BeanCallBack(aty, "删除中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("删除成功");
                    objList.remove(Postion);
                    adapter.notifyDataSetChanged();
                } else {
                    warningShow("删除失败");
                }
            }
        });
    }
    private void delIchGraph(final int Postion, int IchnographyID) {
        OkHttpUtils.post().url(HttpApi.URL_ICHNOGRAPHY_DEL)
                .addParams("IchnographyID", IchnographyID + "")
                .addParams("UserID", app.userID)
                .build().execute(new BeanCallBack(aty, "删除中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("删除成功");
                    objList.remove(Postion);
                    adapter.notifyDataSetChanged();
                } else {
                    warningShow("删除失败");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==UpdateRequestID&&resultCode==RESULT_OK){
            getInfoMation();
        }
    }
}
