package smartenforce.aty.personrepay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kas.clientservice.haiyansmartenforce.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.tcsf.ArrearsBean;
import smartenforce.bean.tcsf.TcListBeanResult;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.zxing.ScanActivity;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * 离开详情页面
 */
public class RepayDetailActivity extends ShowTitleActivity {
    private TextView tev_cphm, tev_trsj, tev_pwbh, tev_lksj, tev_tcfy;
    private ArrearsBean arrearsBean;
    private TextView tev_sfy;
    private TextView tev_wx_pay;

    private IWXAPI msgApi;


    private String outTradeNo,prepay_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_detail);
        msgApi = WXAPIFactory.createWXAPI(this, null);

    }

    @Override
    protected void findViews() {
        tev_cphm = (TextView) findViewById(R.id.tev_cphm);
        tev_trsj = (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh = (TextView) findViewById(R.id.tev_pwbh);
        tev_lksj = (TextView) findViewById(R.id.tev_lksj);
        tev_tcfy = (TextView) findViewById(R.id.tev_tcfy);
        tev_sfy = (TextView) findViewById(R.id.tev_sfy);
        tev_wx_pay = (TextView) findViewById(R.id.tev_wx_pay);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("详情");
        arrearsBean = (ArrearsBean) getIntent().getSerializableExtra("ArrearsBean");
        tev_cphm.setText(arrearsBean.UCarnum);
        tev_pwbh.setText(arrearsBean.BerthName);
        tev_trsj.setText(arrearsBean.StartTime);
        tev_lksj.setText(arrearsBean.EndTime);
        tev_sfy.setText(arrearsBean.OperRealname);
        tev_tcfy.setText(Double.valueOf(arrearsBean.cash_fee) / 100+"元");
        tev_wx_pay.setOnClickListener(new NoFastClickLisener() {
            @Override
            public void onNofastClickListener(View v) {
              new GetPrepayIdTask().execute();
            }
        });

    }



    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(aty,"提示", "正在获取预支付订单...");
        }

        @Override
        protected void onPostExecute(Map<String,String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            prepay_id= result.get("prepay_id");

//       TODO:测试     pay();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String,String>  doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();
            byte[] buf = Util.httpPost(url, entity);
            String content = new String(buf);
            Map<String,String> xml=ParamsUtil.getInstance().decodeXml(content);

            return xml;
        }
    }


    //TODO:notify_url，total_fee
    private String genProductArgs() {
        try {
            LinkedHashMap<String,String> map=new LinkedHashMap<>();
            map.put("appid",Constants.APP_ID);
            map.put("body","weixin");
            map.put("mch_id",Constants.MCH_ID);
            map.put("nonce_str",genNonceStr());
            map.put("notify_url","weixin");
            map.put("out_trade_no",genOutTradNo());
            map.put("spbill_create_ip","127.0.0.1");
            map.put("total_fee",arrearsBean.cash_fee);
            map.put("trade_type","APP");
            map.put("sign",ParamsUtil.getInstance().genPackageSign(map));

            return ParamsUtil.getInstance().toXmlString(map);

        } catch (Exception e) {
            return null;
        }

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }



    private String genOutTradNo() {
        Random random = new Random();
        outTradeNo=  MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
        return outTradeNo;
    }

    private void pay(){
        msgApi.registerApp(Constants.APP_ID);
        PayReq req = new PayReq();
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = prepay_id;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        LinkedHashMap<String ,String> map=new LinkedHashMap<>();
        map.put("appid", req.appId);
        map.put("noncestr", req.nonceStr);
        map.put("package", req.packageValue);
        map.put("partnerid", req.partnerId);
        map.put("prepayid", req.prepayId);
        map.put("timestamp", req.timeStamp);
        req.sign =ParamsUtil.getInstance().genAppSign(map);
        msgApi.sendReq(req);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int errCode=  intent.getIntExtra("errCode",-1);
        String errMsg=  intent.getStringExtra("errMsg");
        show(errMsg);
        if (errCode==0){

            // TODO:付款成功需要掉服务端接口
            setResult(RESULT_OK);

            finish();
        }
    }
}