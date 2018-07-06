package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.Logger;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by DELL_Zjcoms02 on 2018/6/25.
 */

public class XieTongDetalis extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tev_ajbh)
    TextView tev_ajbh;
    @BindView(R.id.tev_ajlx)
    TextView tev_ajlx;
    @BindView(R.id.tev_sbsj)
    TextView tev_sbsj;
    @BindView(R.id.tev_sysj)
    TextView tev_sysj;
    @BindView(R.id.tev_sqjd)
    TextView tev_sqjd;
    @BindView(R.id.tev_sfwz)
    TextView tev_sfwz;
    @BindView(R.id.tev_qkms)
    TextView tev_qkms;

    @BindView(R.id.edt_ajbh)
    EditText edt_ajbh;
    @BindView(R.id.edt_ajlx)
    EditText edt_ajlx;
    @BindView(R.id.edt_sbsj)
    EditText edt_sbsj;
    @BindView(R.id.edt_sysj)
    EditText edt_sysj;
    @BindView(R.id.edt_sqjd)
    EditText edt_sqjd;
    @BindView(R.id.edt_sfwz)
    TextView edt_sfwz;
    @BindView(R.id.tev_xtfankui)
    TextView tev_xtfankui;
    @BindView(R.id.tev_xttijiao)
    TextView tev_xttijiao;
    @BindView(R.id.edt_qkms)
    EditText edt_qkms;
    @BindView(R.id.rev_xtdetails)
    RecyclerView recyclerView;
    @BindView(R.id.rel_xtdetails)
    RelativeLayout rel_xtdetails;
    @BindView(R.id.img_sfwz)
    ImageView img_sfwz;

    EditText edt_diglog;
    Button btn_xgyy;
    Intent intent = new Intent();
    int projcode;
    XTDetailsBean list;
    String state;
    PopupWindow ppw;
    List<XIGuanBean.RtnBean> xiguanlist;
    XTDetailsBean.RtnBean.TableBean table ;
    @Override
    protected int getLayoutId() {
        return R.layout.xietongdetails;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("案件详情");
        iv_back.setOnClickListener(this);

        projcode = getIntent().getIntExtra("projcode", 0);
        state = getIntent().getStringExtra("state");
        getDetails(projcode);

        if (state != null && state.equals("已处理")) {
            tev_xtfankui.setVisibility(View.GONE);
            tev_xttijiao.setVisibility(View.GONE);
        } else {
            tev_xtfankui.setOnClickListener(this);
            tev_xttijiao.setOnClickListener(this);
        }

    }

    private void getDetails(final int projcode) {
        OkHttpUtils.post().url(XTURL.URLLIST).addParams("optionname", XTURL.GetProjectDetail)
                .addParams("projcode", projcode + "")
                .addParams("state", "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(mContext, "协同详情异常，即将退出");
            }

            @Override
            public void onResponse(String response, int id) {

                XTDetailsBean xtDetailsBean = gson.fromJson(response, XTDetailsBean.class);
                try {
                    if (xtDetailsBean.State && xtDetailsBean.Rtn != null) {
                        XTDetailsBean.RtnBean.TableBean tableBean = xtDetailsBean.Rtn.Table.get(0);
                        edt_ajbh.setText(tableBean.projname);
                        edt_ajlx.setText(tableBean.bigclassname + "-" + tableBean.smallclassname);
                        edt_sbsj.setText(tableBean.startdate);
                        edt_sqjd.setText(tableBean.area + "-" + tableBean.street + "-" + tableBean.square);
                        edt_sfwz.setText(tableBean.address);
                        edt_sysj.setText(tableBean.tracetime);
                        edt_qkms.setText(tableBean.probdesc1);
                        if (xtDetailsBean.Rtn.Table1 != null && xtDetailsBean.Rtn.Table1.size() > 0) {
                            for(int i=0;i<xtDetailsBean.Rtn.Table1.size();i++){
                                XTDetailsBean.RtnBean.Table1Bean table1Bean = xtDetailsBean.Rtn.Table1.get(i);
                                String[] arrays = table1Bean.filepath.split(",");
                                list_img.addAll(Arrays.asList(arrays));

                            }
                            adapter = new ImageListRvAdapter(list_img, mContext);
                            RecyclerView.LayoutManager manager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnImagelickListener(new ImageListRvAdapter.OnImagelickListener() {
                                @Override
                                public void onImageClick(int p) {
                                    Intent intent = new Intent(mContext, XieTongImage.class);
                                    intent.putExtra("url", list_img.get(p));
                                    startActivity(intent);
                                }
                            });

                            }
                        table = tableBean;
                        img_sfwz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String fid=table.fid;
                                String address=table.address;
                                if(fid==null||fid.equals("0.0,0.0")){
                                    ToastUtils.showToast(mContext,"未获取到准确坐标");

                                }else {
                                    intent = new Intent(mContext, XieTongMapActivity.class);
                                    intent.putExtra("fid", fid);
                                    intent.putExtra("xtaddress", address);
                                    startActivity(intent);
                                }
                            }
                        });


                    } else {
                        ToastUtils.showToast(mContext, xtDetailsBean.ErrorMsg);
                    }

                } catch (Exception e) {
                    Logger.e(response);
                }


            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tev_xtfankui:
                FanKui("反馈意见", 0);
                break;
            case R.id.tev_xttijiao:
                FanKui("回退意见", 1);
                break;
        }
    }


    List<String> list_img = new ArrayList<>();
    ImageListRvAdapter adapter;

    private void FanKui(String title, final int fk) {
        AlertDialog.Builder buildert = new AlertDialog.Builder(this);
        LayoutInflater inflatert = getLayoutInflater();
        final View view = inflatert.inflate(R.layout.xtdetails_diglog, null);
        buildert.setView(view);
        buildert.setMessage(title);
        edt_diglog = (EditText) view.findViewById(R.id.edt_dialog);
        btn_xgyy = (Button) view.findViewById(R.id.btn_xgyy);
        btn_xgyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fk == 1) {
                    XiGuan("1");

                }
                if (fk == 0) {
                    XiGuan("0");
                }
            }

        });
        buildert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (fk == 0) {
                    FanKuiOk();
                }
                if (fk == 1) {
                    Back();

                }
            }


        });
        buildert.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        final AlertDialog dlg = buildert.create();
        dlg.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dlg.show();
    }

    private void FanKuiOk() {
        Log.i(TAG, "FanKuiOk: projcode=" + projcode + "  targetDepartCode=" + UserSingleton.USERINFO.szcg.departcode + "  userCode =" + UserSingleton.USERINFO.szcg.usercode);
        OkHttpUtils.post().url(XTURL.URLLIST)
                .addParams("optionname", XTURL.GetFeedBackOk)
                .addParams("userCode", UserSingleton.USERINFO.szcg.usercode)
                .addParams("projcode", projcode + "")
                .addParams("text", edt_diglog.getText().toString().trim())
                .addParams("depCode", UserSingleton.USERINFO.szcg.departcode)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(mContext, "网络异常");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: " + response);
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.isState()) {

                    ToastUtils.showToast(mContext, "反馈成功");
                    XieTongDetalis.this.finish();
                }else {
                    ToastUtils.showToast(mContext, "反馈失败");
                }
            }
        });
    }

    private void Back() {
        Log.i(TAG, "FanKuiOk: projcode=" + projcode + "  targetDepartCode=" + UserSingleton.USERINFO.szcg.departcode);
        OkHttpUtils.post().url(XTURL.URLLIST)
                .addParams("optionname", XTURL.GetBackSpace)
                .addParams("userCode", UserSingleton.USERINFO.szcg.usercode)
                .addParams("projcode", projcode + "")
                .addParams("text", edt_diglog.getText().toString().trim())
                .addParams("depCode", UserSingleton.USERINFO.szcg.departcode)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(mContext, "网络异常");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: " + response);
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.isState()) {

                    ToastUtils.showToast(mContext, "退回成功");
                    XieTongDetalis.this.finish();
                }else {
                    ToastUtils.showToast(mContext, "退回失败");
                }
            }
        });
    }
    ListView ltv_xiguan;
    XiGuanAdapter xiGuanAdapteradapter;
    //TODO 习惯用语
    String code;
    public void Dialog(){
//        View view = LayoutInflater.from(this).inflate(R.layout.,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflatert = getLayoutInflater();
        final View view = inflatert.inflate(R.layout.xgyy_dialog, null);
        builder.setView(view);
        ltv_xiguan=(ListView) view.findViewById(R.id.ltv_xiguan);
        xiGuanAdapteradapter=new XiGuanAdapter(xiguanlist,mContext);
        ltv_xiguan.setAdapter(xiGuanAdapteradapter);
        xiGuanAdapteradapter.setListner(new XiGuanAdapter.Listeren() {
            @Override
            public void onItem(int p,View v) {
                String word=xiguanlist.get(p).short_sentence;
                edt_diglog.setText(word);
            }

        });
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void XiGuan(String code) {

        OkHttpUtils.post().url(XTURL.URLLIST)
                .addParams("optionname", XTURL.GetWording)
                .addParams("type", code)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(mContext, "获取习惯用语异常");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    XIGuanBean bean = gson.fromJson(response, XIGuanBean.class);
                    if (bean.State && bean.Rtn != null) {
                        xiguanlist=bean.Rtn;
                        Dialog();


                    }

                } catch (Exception e) {
                    ToastUtils.showToast(mContext, "程序异常");

                }
            }
        });


    }


    public void setRecyclerViewHeight(int size) {
        int height = ((size / 2) + 1) * 140 + 30;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }
}
