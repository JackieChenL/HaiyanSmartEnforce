package com.kas.clientservice.haiyansmartenforce.Module.Register;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.RegistAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_register_register)
    TextView tv_login_login;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.et_register_account)
    EditText et_account;
    @BindView(R.id.et_register_pswd)
    EditText et_psw;
    @BindView(R.id.et_register_account_pswd_confirm)
    EditText et_psw_confirm;
    @BindView(R.id.et_register_id)
    EditText et_id;
    @BindView(R.id.et_register_carNum)
    EditText et_carNum;
    @BindView(R.id.et_register_address)
    EditText et_address;
    @BindView(R.id.et_register_phone)
    EditText et_phone;

    String account = "";
    String psw = "";
    String psw_confirm = "";
    String id = "";
    String carNum = "";
    String address = "";
    String name = "";
    String phone = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("注册");
        tv_login_login.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_register:
                account = et_account.getText().toString();
                psw = et_psw.getText().toString();
                psw_confirm = et_psw_confirm.getText().toString();
                id = et_id.getText().toString();
                carNum = et_carNum.getText().toString();
                address = et_address.getText().toString();
                phone = et_phone.getText().toString();
                if (account.equals("")) {
                    ToastUtils.showToast(mContext, "请输入账号");
                    break;
                }
                if (psw.equals("")) {
                    ToastUtils.showToast(mContext, "请输入密码");
                    break;
                }
                if (psw_confirm.equals("")) {
                    ToastUtils.showToast(mContext, "请再次确认密码");
                    break;
                }
                if (phone.equals("")) {
                    ToastUtils.showToast(mContext, "请输入手机号");
                    break;
                }
                if (!psw_confirm.equals(psw)) {
                    ToastUtils.showToast(mContext, "两次输入的密码不相同");
                    break;
                }
                if (!isPhoneValid(phone)) {
                    ToastUtils.showToast(mContext, "无效的手机号");
                    break;
                }
                if ((!id.equals("")) && id.trim().length() != 18) {
                    ToastUtils.showToast(mContext, "无效的身份证号");
                    break;
                }
                if ((!carNum.equals("")) && carNum.trim().length() < 7) {
                    ToastUtils.showToast(mContext, "无效的车牌号");
                    break;
                }
                regist();
                break;
            case R.id.iv_heaer_back:
                finish();
                break;

        }

    }

    private void regist() {
        RetrofitClient.createService(RegistAPI.class)
                .httpRegist(account, phone, carNum, id, address, psw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseEntity<RegistAPI.RegisterEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<RegistAPI.RegisterEntity> registerEntityBaseEntity) {
                        if (registerEntityBaseEntity.isState()) {
                            ToastUtils.showToast(mContext, " 注册成功");
                            Intent intent = new Intent();
                            intent.putExtra("account",account);
                            intent.putExtra("psw",psw);
                            setResult(100,intent);
                            finish();
                        } else {
                            ToastUtils.showToast(mContext, registerEntityBaseEntity.ErrorMsg);
                        }
                    }
                });
    }

    private boolean isPhoneValid(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}
