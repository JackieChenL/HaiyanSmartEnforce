package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.HTTP_HOST;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.TcListBeanResult;
import com.kas.clientservice.haiyansmartenforce.tcsf.impl.NoFastClickLisener;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.DateUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.PrintUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.zxing.ScanActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * 离开详情页面
 */
public class ExitActivity extends PrintActivity {
   private TextView tev_cphm,tev_trsj,tev_pwbh,tev_lksj,tev_tcfy;
    private TextView tev_print,tev_submit;
    private ImageView iv_heaer_back;
    private TextView  tv_header_title;

    private String endTime;
    private long cost;
    private String lengthTime;

    private TcListBeanResult bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exitinging);
        bean= (TcListBeanResult) getIntent().getSerializableExtra("TcListBeanResult");

        tev_cphm= (TextView) findViewById(R.id.tev_cphm);
        tev_trsj= (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh= (TextView) findViewById(R.id.tev_pwbh);
        tev_lksj= (TextView) findViewById(R.id.tev_lksj);
        tev_tcfy= (TextView) findViewById(R.id.tev_tcfy);
        tev_print= (TextView) findViewById(R.id.tev_print);
        tev_submit= (TextView) findViewById(R.id.tev_submit);
        iv_heaer_back = (ImageView) findViewById(R.id.iv_heaer_back);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText("离开收费");
        iv_heaer_back.setOnClickListener(FastClickLister);
        tev_print.setOnClickListener(FastClickLister);
        tev_submit.setOnClickListener(FastClickLister);
        endTime=DateUtil.currentTime();
        cost=DateUtil.calMoney(endTime,bean.starttime);
        lengthTime=DateUtil.getTimeLenth(endTime,bean.starttime);
        tev_cphm.setText(bean.carnum);
        tev_pwbh.setText(bean.Berthname);
        tev_trsj.setText(bean.starttime);
        tev_lksj.setText(endTime);
        tev_tcfy.setText(cost+"元");
        changeState(true,tev_print,tev_submit);
    }

    @Override
    public void onPrintSuccess() {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (scanResult != null) {
                    String authCode = scanResult.getContents();
                    doSuccessInfo(authCode);
                }else{
                    showPayCash("扫描失败，重新选择支付方式");
                }
            }
        }

    }

    NoFastClickLisener FastClickLister = new NoFastClickLisener() {
        @Override
        public void onNoFastClickListener(View v) {
            super.onClick(v);
            switch(v.getId()){
                case R.id.iv_heaer_back:
                    finish();
                    break;
                case R.id.tev_print:
                    String[] body = new String[]{"车牌号码：" + bean.carnum, "停入时间：" + bean.starttime,
                            "泊位编号：" + bean.Berthname,"离开时间："+endTime,"停车费用："+cost};
                    ArrayList<byte[]> list = (new PrintUtil("停车收费小票", null, body, getFooterString())).getData();
                    doCheckConnection(list);
                    break;
                case R.id.tev_submit:

                    OkHttpUtils.post().url(HTTP_HOST.URL_PARK_EXIT)
                            .addParams("Opername", getOpername())
                            .addParams("type", "1")
                            .addParams("stoptime", endTime)
                            .addParams("money", cost+"")
                            .addParams("btid", bean.btid+"")
                            .addParams("LengthTime", lengthTime)
                            .build().execute(new BeanCallBack(aty, "数据提交中") {

                        @Override
                        public void handleBeanResult(NetResultBean bean) {
                            if (bean.State) {
                                changeState(false,tev_print,tev_submit);
                                showPayCash("本次停车将收取"+cost+"元");
                            } else {
                                show(bean.ErrorMsg);
                            }

                        }

                    });
                    break;
            }

        }
    };

//
    private void showPayCash(final String body){
        if (cost==0){

        }else{
            final String[] arr=new String[]{"现金","微信"};
            new AlertView("收费", body, null, null, arr, aty, null, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position==0){
                        //现金，不做处理
                    }else{
                      startActivityForResult(new Intent(aty, ScanActivity.class),REQUEST_CODE);
                    }
                }
            }).show();
        }

    }


    private void doSuccessInfo(String authCode){
        OkHttpUtils.post().url(HTTP_HOST.URL_WXPAY)
                .addParams("auth_code", authCode)
                .addParams("body", bean.carnum+"停车收费")
                .addParams("fee", cost*100+"")
                .addParams("btid",bean.btid+"")
                .build().execute(new BeanCallBack(aty, "微信收款中") {

            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("收款成功");
                } else {
                    showPayCash(bean.ErrorMsg);
                }
            }
        });

    }

}
