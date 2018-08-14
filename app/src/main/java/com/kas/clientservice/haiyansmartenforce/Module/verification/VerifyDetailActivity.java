package com.kas.clientservice.haiyansmartenforce.Module.verification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import smartenforce.adapter.ImageAdapter;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.widget.FullyGridLayoutManager;


public class VerifyDetailActivity extends ShowTitleActivity {

    private VerifyBean verifyBean;

    private TextView tev_projcode,tev_type,tev_address,tev_time,tev_describe;
    private RecyclerView rcv_img;
    private VerifyDetailAdapter adapter;
    private ScrollView scv;
    private Spinner spn_result;

    private RecyclerView rcv_result;

    private Button btn_upload;

    private List<String> list=new ArrayList<>();
    private ImageAdapter imageAdapter;
    private String result="已完成";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_detail);
    }

    @Override
    protected void findViews() {
        tev_projcode=findViewById(R.id.tev_projcode);
        tev_type=findViewById(R.id.tev_type);
        tev_address=findViewById(R.id.tev_address);
        tev_time=findViewById(R.id.tev_time);
        tev_describe=findViewById(R.id.tev_describe);
        rcv_img=findViewById(R.id.rcv_img);
        spn_result=findViewById(R.id.spn_result);
        rcv_result=findViewById(R.id.rcv_result);
        scv=findViewById(R.id.scv);
        btn_upload=findViewById(R.id.btn_upload);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("任务详情");
        final String[] result_arr=getResources().getStringArray(R.array.verify_result);
        verifyBean= (VerifyBean) getIntent().getSerializableExtra("VerifyBean");
        tev_projcode.setText(verifyBean.getProjcode());
        tev_type.setText(verifyBean.getProbclassName()+">"+verifyBean.getBigclassname()+">"+verifyBean.getSmallclassname());
        tev_address.setText(verifyBean.getAddress().replaceAll("\\/",""));
        tev_time.setText(verifyBean.getCu_date());
        tev_describe.setText(verifyBean.getProbdesc());
        rcv_img.setLayoutManager(new FullyGridLayoutManager(aty,3));
         if(!isEmpty(verifyBean.getPicFiles())){
          List<String> list= Arrays.asList( verifyBean.getPicFiles().split(","));
             adapter=new VerifyDetailAdapter(list,aty);
             rcv_img.setAdapter(adapter);
         }
         spn_result.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 result=result_arr[position];
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });



        rcv_result.setLayoutManager(new FullyGridLayoutManager(aty,4));
        imageAdapter=new ImageAdapter(list,aty,scv);
        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                  takePhoto(REQUESTCODE.CAMERA);
            }

            @Override
            public void onDelImageClick(int p) {
                  list.remove(p);
                  imageAdapter.notifyChanged();
            }
        });
        rcv_result.setAdapter(imageAdapter);

        btn_upload.setOnClickListener(new NoFastClickLisener() {
            @Override
            public void onNofastClickListener(View v) {
                doUploadImg();
            }
        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE.CAMERA) {
                list.add(file.getAbsolutePath());
                imageAdapter.notifyChanged();
            }


        }


    }



    private void doUploadImg() {
       if (list.size()==0){
           doUploadInfo("");

       }else{
           String base64Bmp=imageAdapter.getImageListStr(false);

           OkHttpUtils.post()
                   .url(IpUtils.URL_HOST)
                   .addParams("optionName", "uploadPic")
                   .addParams("img", base64Bmp)
                   .build().execute(new BeanCallBack(aty, "上传中") {
               @Override
               public void handleBeanResult(NetResultBean bean) {
                   if (bean.State) {
                    String[] arr=   bean.Rtn.split("\\|");
                     final String Img_header="http://sxzhcg.zjcoms.cn/handler/";
                       String Url = "";
                       for (int i = 0; i < arr.length; i++) {
                           Url =Url+ Img_header + arr[i] + "|";
                       }
                       Url = Url.endsWith("|") ? Url.substring(0, Url.length() - 1) : Url;
                       doUploadInfo(Url);
                   } else {
                       warningShow(bean.ErrorMsg);
                   }
               }
           });



       }



    }



    private void doUploadInfo(String pic_url) {

        OkHttpUtils.post()
                .url(IpUtils.URL_HOST)
                .addParams("optionName", "pdamodifypdamsg")
                .addParams("projcode", verifyBean.getProjcode())
                .addParams("collcode", "1628")
                .addParams("msgcontent", result)
                .addParams("ePicArry",pic_url)
                .build().execute(new BeanCallBack(aty, "上传中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State ) {
                    show(bean.Rtn);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });

    }


}
















