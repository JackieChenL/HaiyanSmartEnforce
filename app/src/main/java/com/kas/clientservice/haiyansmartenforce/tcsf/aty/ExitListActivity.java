package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.SPUtils;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.HTTP_HOST;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.DateUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * 离开页面（占用车位列表）
 */
public class ExitListActivity extends BaseActivity {
private Button btn_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_list);
        doNetworkList();

    }


   private void doNetworkList(){
       OkHttpUtils.post().url(HTTP_HOST.URL_PARK_LIST)
               .addParams("Opername", UserSingleton.getInstance().getUserAccount(aty)).build().execute(new BeanCallBack(aty,null) {
                @Override
                public void handleBeanResult(NetResultBean bean) {
                    doNetworkExit();
                }
            });
   }

   private void doNetworkExit(){
       OkHttpUtils.post().url(HTTP_HOST.URL_PARK_EXIT).addParams("UCarnum","15355")
               .addParams("BerthName","1号停车位").build().execute(new BeanCallBack(aty,null) {
                @Override
                public void handleBeanResult(NetResultBean bean) {
                  String test1=  DateUtil.getCost("2018-05-03 12:29:59","2018-05-03 12:00:00");
                  String test2=  DateUtil.getCost("2018-05-03 12:30:00","2018-05-03 12:00:00");
                  String test3=  DateUtil.getCost("2018-05-03 12:30:01","2018-05-03 12:00:00");
                  String test4=  DateUtil.getCost("2018-05-03 13:00:00","2018-05-03 12:00:00");
                  String test5=  DateUtil.getCost("2018-05-03 13:00:01","2018-05-03 12:00:00");
                    LogUtil.e(TAG,test1+","+test2+","+test3+","+test4+","+test5);
                }
            });
   }


}
