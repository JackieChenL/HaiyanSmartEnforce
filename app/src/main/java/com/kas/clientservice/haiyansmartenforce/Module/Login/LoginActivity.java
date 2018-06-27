package com.kas.clientservice.haiyansmartenforce.Module.Login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.API.LoginAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.MainActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Register.RegisterActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Register.ResetPswActivity;
import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserInfo;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.AppParameter;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.SPBuild;
import com.kas.clientservice.haiyansmartenforce.Utils.SPUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import videotalk.VideoTalkMainActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_login_login)
    TextView tv_login;
    @BindView(R.id.et_login_account)
    EditText et_userName;
    @BindView(R.id.et_login_psw)
    EditText et_psw;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_login_forget)
    TextView tv_forget;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        iv_back.setVisibility(View.GONE);
        tv_title.setText("登录");
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        String userName = UserSingleton.getInstance().getUserAccount(this);
        String psw = UserSingleton.getInstance().getUserPassword(this);
        if (userName != null) {
            et_userName.setText(userName);
        }
        if (!psw.equals("")) {
            et_psw.setText(psw);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null) {
                String account = data.getStringExtra("account");
                String paw = data.getStringExtra("psw");
                et_userName.setText(account);
                et_psw.setText(paw);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_login:
                login();

                break;
            case R.id.tv_register:
                startActivityForResult(new Intent(mContext, RegisterActivity.class), 100);
                break;
            case R.id.tv_login_forget:
                startActivity(new Intent(mContext, ResetPswActivity.class));
                break;
        }
    }

    private void login() {

        if (!et_userName.getText().toString().equals("")) {
            if (!et_psw.getText().toString().equals("")) {
                RetrofitClient.createService(LoginAPI.class)
                        .httpLogin(et_userName.getText().toString(), et_psw.getText().toString(), AppParameter.getVersionCode(mContext) + "", AppParameter.getIMEI(mContext))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<BaseEntity<UserInfo>>(mContext) {
                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                Log.i(TAG, "onError: " + responeThrowable.toString());
                                showNetErrorToast();
                            }

                            @Override
                            public void onNext(BaseEntity<UserInfo> entity) {
                                Log.i(TAG, "onNext: " + gson.toJson(entity));
                                if (entity.isState()) {
                                    UserSingleton.USERINFO = entity.getRtn();
                                    UserInfo.NameBean nameBean = entity.getRtn().Name;
                                    if (nameBean != null) {
                                        MyApplication application = (MyApplication) getApplication();
                                        application.userID = nameBean.UserID;
                                        application.DepartmentID = nameBean.DepartmentID;
                                        application.NameEmp = nameBean.NameEmp;
                                        application.NameDep = nameBean.NameDep;
                                        application.AddressDep = nameBean.AddressDep;
                                    }
                                    saveUserInfo(et_userName.getText().toString(), et_psw.getText().toString(), entity.getRtn());
                                    startActivity(new Intent(mContext, MainActivity.class));
                                } else {
                                    ToastUtils.showToast(mContext, entity.ErrorMsg);
                                }
                            }
                        });
//                OkHttpUtils.post().url(RetrofitClient.mBaseUrl + RequestUrl.Login)
//                        .addParams("Opername", et_userName.getText().toString())
//                        .addParams("OperPwd", et_psw.getText().toString())
//                        .build().execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        Log.i(TAG, "onError: "+e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, "onResponse: " + response);
//                        BaseEntity loginEntityBaseEntity = gson.fromJson(response,BaseEntity.class);
//                        Log.i(TAG, "onResponse: "+loginEntityBaseEntity.isState());
//                    }
//                });


            } else {
                ToastUtils.showToast(mContext, "请输入密码");
            }
        } else {
            ToastUtils.showToast(mContext, "请输入账号");
        }
    }

    private void saveUserInfo(String mPhone, String mUserPassword, UserInfo USERINFO) {
        //保存先清空内容
//        SPUtils.clear(getApplicationContext());
        String userInfo = new Gson().toJson(USERINFO);
        new SPBuild(getApplicationContext())
                .addData(Constants.ISLOGIN, Boolean.TRUE)//登陆志位
                .addData(Constants.LOGINTIME, System.currentTimeMillis())//登陆时间
                .addData(Constants.USERACCOUNT, mPhone)//账号
                .addData(Constants.USERPASSWORD, mUserPassword)//密码
                .addData(Constants.USERINFO, userInfo)
                .build();
        SPUtils.putCommit(mContext, Constants.USERINFO, userInfo);
    }
}