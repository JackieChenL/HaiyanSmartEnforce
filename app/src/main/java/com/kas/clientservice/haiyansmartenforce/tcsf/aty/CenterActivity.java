package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ToastUtil;

public class CenterActivity extends BaseActivity implements View.OnClickListener {

    /*5个模块：停车，离开，查询，补缴，设置*/
    private TextView tev_tc, tev_lk, tev_cx, tev_bj, tev_sz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        tev_tc = (TextView) findViewById(R.id.tev_tc);
        tev_lk = (TextView) findViewById(R.id.tev_lk);
        tev_cx = (TextView) findViewById(R.id.tev_cx);
        tev_bj = (TextView) findViewById(R.id.tev_bj);
        tev_sz = (TextView) findViewById(R.id.tev_sz);
        initAction();

    }

    private void initAction() {
        tev_tc.setOnClickListener(this);
        tev_lk.setOnClickListener(this);
        tev_cx.setOnClickListener(this);
        tev_bj.setOnClickListener(this);
        tev_sz.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tev_tc:
                startActivity(new Intent(aty, ParkActivity.class));
                break;


            case R.id.tev_lk:
                startActivity(new Intent(aty, ExitListActivity.class));
                break;

            case R.id.tev_cx:

                    startActivity(new Intent(aty,QueryActivity.class));


                break;
            case R.id.tev_bj:
//                String url="http://jisucpsb.market.alicloudapi.com/licenseplaterecognition/recognize";
//               String pic= ImgUtil.bitmapToBase64(ImgUtil.compressImage(ImgUtil.drawableToBitmap(getResources().getDrawable(R.mipmap.t3))));
//                OkHttpUtils.post().url(url).addHeader("X-Ca-Key","24553193")
//                        .addHeader("Authorization", "APPCODE 2e476d97d6994a489afb3491b44a2578")
//
//                        .addParams("pic",pic).build().execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                           LogUtil.e("SUCCESSS",response);
//                    }
//                });




                ToastUtil.show(aty, "补缴按钮点击了");
                break;
            case R.id.tev_sz:
                final String[] arr = new String[]{"001", "002", "003", "004", "005", "006"};
                new AlertView(null, null, null, null, arr, aty, null, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        ToastUtil.show(aty, arr[position] + ":" + position);
                    }
                }).show();


//                ToastUtil.show(aty,"设置按钮点击了");
                break;


            default:
                ;


        }

    }





















}
