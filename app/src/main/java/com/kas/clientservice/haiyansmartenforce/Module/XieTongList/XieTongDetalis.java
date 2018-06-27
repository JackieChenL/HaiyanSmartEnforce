package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.Logger;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import smartenforce.util.ToastUtil;

/**
 * Created by DELL_Zjcoms02 on 2018/6/25.
 */

public class XieTongDetalis extends BaseActivity implements View.OnClickListener{
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
    EditText edt_sfwz;
    @BindView(R.id.tev_xtfankui)
    TextView tev_xtfankui;
    @BindView(R.id.tev_xttijiao)
    TextView tev_xttijiao;
    @BindView(R.id.edt_qkms)
    EditText edt_qkms;
    @BindView(R.id.rev_xtdetails)
    RecyclerView recyclerView;
    EditText edt_diglog;
    Button btn_xgyy;
    Intent intent=new Intent();
    String PrejectCode = "";
    XIGuanBean xiguan;
    XTDetailsBean list;
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
        int projcode=getIntent().getIntExtra("projcode",0);
        getDetails(projcode);
//        if(State.equals("已处理")){
//            tev_xtfankui.setVisibility(View.GONE);
//            tev_xttijiao.setVisibility(View.GONE);
//        }else{
//            tev_xtfankui.setOnClickListener(this);
//            tev_xttijiao.setOnClickListener(this);
//        }
        tev_xtfankui.setOnClickListener(this);
        tev_xttijiao.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tev_xtfankui:
//                FanKui("反馈意见",0);
                finish();
                break;
            case R.id.tev_xttijiao:
                finish();
//                FanKui("回退意见",1);
                break;
        }
    }



    List<String> list_img = new ArrayList<>();
    ImageListRvAdapter adapter;
    private void getDetails(final int projcode) {
        OkHttpUtils.post().url(RequestUrl.URLLIST).addParams("optionname", RequestUrl.GetProjectDetail)
                .addParams("projcode", projcode+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(mContext,"协同详情异常，即将退出");
            }

            @Override
            public void onResponse(String response, int id) {

                XTDetailsBean xtDetailsBean=   gson.fromJson(response,XTDetailsBean.class);
                try{
                if (xtDetailsBean.State&&xtDetailsBean.Rtn!=null){
                   XTDetailsBean.RtnBean.TableBean tableBean= xtDetailsBean.Rtn.Table.get(0);
                    tev_ajbh.setText("案卷编号:"+tableBean.projname);
                    tev_ajlx.setText("案卷类型:"+tableBean.bigclassname+"-"+tableBean.smallclassname);
                    tev_sbsj.setText("上报时间:"+tableBean.startdate);
                    tev_sqjd.setText("剩余时间:"+tableBean.area+"-"+tableBean.street+"-"+tableBean.square);
                    tev_sfwz.setText("社区街道:"+tableBean.address);
                    tev_sysj.setText("事发位置:"+tableBean.tracetime);
                    tev_qkms.setText("情况描述:"+tableBean.probdesc1);
                    if (xtDetailsBean.Rtn.Table1!=null&&xtDetailsBean.Rtn.Table1.size()>0){
                       XTDetailsBean.RtnBean.Table1Bean table1Bean= xtDetailsBean.Rtn.Table1.get(0);
                       String[] arrays= table1Bean.filepath.split(",");
                        list_img= Arrays.asList(arrays);
                        adapter = new ImageListRvAdapter(list_img, mContext);
                        RecyclerView.LayoutManager manager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.setOnImagelickListener(new ImageListRvAdapter.OnImagelickListener() {
                            @Override
                            public void onImageClick(int p) {
                                Intent intent = new Intent(mContext, ImageActivity.class);
                                intent.putExtra("url", list_img.get(p));
                                startActivity(intent);
                            }
                        });
                    }

                }else{
                    ToastUtils.showToast(mContext,xtDetailsBean.ErrorMsg);
                }

                }catch (Exception e){
                    Logger.e(response);
                }




            }
        });

    }

//    private void FanKui(String title,final   int fk) {
//        AlertDialog.Builder buildert = new AlertDialog.Builder(this);
//        LayoutInflater inflatert = getLayoutInflater();
//        final View view = inflatert.inflate(R.layout.xtdetails_diglog,null);
//        buildert.setView(view);
//        buildert.setMessage(title);
//        edt_diglog=(EditText) view.findViewById(R.id.edt_dialog);
//        btn_xgyy=(Button) view.findViewById(R.id.btn_xgyy);
//        btn_xgyy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(fk==1){
//                    XiGuan("1");
//
//                }
//                if(fk==0){
//                    XiGuan("0");
//                }
//            }
//
//        });
//        buildert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(fk==0){
//                    FanKuiOk();
//                }
//                if(fk==1){
//                    Back();
//
//                }
//            }
//
//
//
//
//        });
//        buildert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//        final AlertDialog dlg = buildert.create();
//        dlg.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        dlg.show();
//    }
//    private void FanKuiOk()  {
//        OkHttpUtils.post().url(RequestUrl.URLLIST)
//                .addParams("optionname", RequestUrl.GetFeedBackOk)
//                .addParams("userCode", UserSingleton.USERINFO.getPublicUsersID())
//                .addParams("projcode", PrejectCode)
//                .addParams("text", edt_diglog.getText().toString().trim())
//                .addParams("departcode",UserSingleton.USERINFO.getDepartcode())
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ToastUtils.showToast(mContext,"网络异常");
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                try {
//                    ToastUtils.showToast(mContext,"反馈成功");
//                    XieTongDetalis.this.finish();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    private void Back() {
//        OkHttpUtils.post().url(RequestUrl.URLLIST)
//                .addParams("optionname", RequestUrl.GetBackSpace)
//                .addParams("userCode", UserSingleton.USERINFO.getPublicUsersID())
//                .addParams("projcode", projcode+"")
//                .addParams("text", edt_diglog.getText().toString().trim())
//                .addParams("depcode",UserSingleton.USERINFO.getClassifyingRubbishId())
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ToastUtils.showToast(mContext,"网络异常");
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                try {
//                    ToastUtils.showToast(mContext,"回退成功");
//                    XieTongDetalis.this.finish();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    private void XiGuan(String code){
//        OkHttpUtils.post().url(RequestUrl.URLLIST)
//                .addParams("optionname", RequestUrl.GetWording)
//                .addParams("type", code)
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ToastUtils.showToast(mContext,"获取习惯用语异常");
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                try{
//                    XIGuanBean bean=gson.fromJson(response,XIGuanBean.class);
//
//                    if(bean.State&& bean.Rtn!=null){
//                        XIGuanBean.RtnBean rtnBean=bean.Rtn.get(0);
//                        edt_diglog.setText(rtnBean.short_sentence);
//                    }
//
//                }catch (Exception e){
//                    ToastUtils.showToast(mContext,"程序异常");
//
//                }
//            }
//        });
//
//
//    }
//
//
//    public void setRecyclerViewHeight(int size) {
//        int height = ((size / 2) + 1) * 140 + 30;
//        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
//        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
//    }
}
