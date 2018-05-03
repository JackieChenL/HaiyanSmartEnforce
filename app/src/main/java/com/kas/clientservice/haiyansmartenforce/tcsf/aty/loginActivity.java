//package com.kas.clientservice.haiyansmartenforce.tcsf.aty;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.kas.clientservice.haiyansmartenforce.R;
//import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
//import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
//import com.kas.clientservice.haiyansmartenforce.tcsf.bean.LoginResultBean;
//import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
//import com.kas.clientservice.haiyansmartenforce.tcsf.util.StringUtil;
//import com.kas.clientservice.haiyansmartenforce.tcsf.util.ToastUtil;
//import com.zhy.http.okhttp.OkHttpUtils;
//
//public class loginActivity extends BaseActivity implements View.OnClickListener {
//    private EditText etv_user, etv_pwd;
//    private TextView tev_login;
//    private static final String LOGIN_URL = "http://111.1.31.210:88/system/theme/credit/login.ashx";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        etv_user = (EditText) findViewById(R.id.etv_user);
//        etv_pwd = (EditText) findViewById(R.id.etv_pwd);
//        tev_login = (TextView) findViewById(R.id.tev_login);
//        tev_login.setOnClickListener(this);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        String Opername = etv_user.getText().toString();
//        String OperPwd = etv_pwd.getText().toString();
//        if (StringUtil.isEmptyString(Opername)) {
//            ToastUtil.show(aty, "请输入用户名");
//        } else if (StringUtil.isEmptyString(OperPwd)) {
//            ToastUtil.show(aty, "请输入密码");
//        } else {
//            OkHttpUtils.post().url(LOGIN_URL)
//                    .addParams("Opername", Opername).addParams("OperPwd", OperPwd).build().execute(new BeanCallBack(aty,"登录中") {
//                @Override
//                public void handleBeanResult(NetResultBean bean) {
//                    if (bean.State){
//                        LoginResultBean LoginResultBean=   bean.getResultBean(LoginResultBean.class);
//                        app.putLoginUser(LoginResultBean);
//                        startActivity(new Intent(aty,CenterActivity.class));
//                        finish();
//                    }else{
//                        ToastUtil.show(aty,bean.ErrorMsg);
//                    }
//                }
//            });
//        }
//
//    }
//}
