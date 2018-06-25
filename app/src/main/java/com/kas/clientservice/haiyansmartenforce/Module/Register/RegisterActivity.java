package com.kas.clientservice.haiyansmartenforce.Module.Register;

import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.RegistAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.MessageEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.CountDownTimerUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.MD5;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import okhttp3.Call;
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
    @BindView(R.id.tv_register_code)
    TextView tv_code;
    @BindView(R.id.et_register_code)
            EditText et_code;

    String account = "";
    String psw = "";
    String psw_confirm = "";
    String id = "";
    String carNum = "";
    String address = "";
//    String phone = "";
    long code = 0;

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
        tv_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_register:
                account = et_account.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                psw_confirm = et_psw_confirm.getText().toString().trim();
                id = et_id.getText().toString().trim();
                carNum = et_carNum.getText().toString().trim();
                address = et_address.getText().toString().trim();
//                phone = et_phone.getText().toString();
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
//                if (phone.equals("")) {
//                    ToastUtils.showToast(mContext, "请输入手机号");
//                    break;
//                }
                if (!psw_confirm.equals(psw)) {
                    ToastUtils.showToast(mContext, "两次输入的密码不相同");
                    break;
                }
                if (!et_code.getText().toString().trim().equals(code+"")) {
                    ToastUtils.showToast(mContext, "验证码错误");
                }
//                if (!isPhoneValid(phone)) {
//                    ToastUtils.showToast(mContext, "无效的手机号");
//                    break;
//                }
//                if ((!id.equals("")) && id.trim().length() != 18) {
//                    ToastUtils.showToast(mContext, "无效的身份证号");
//                    break;
//                }
//                if ((!carNum.equals("")) && carNum.trim().length() < 7) {
//                    ToastUtils.showToast(mContext, "无效的车牌号");
//                    break;
//                }
                regist();
                break;
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_register_code:
                if (!et_account.getText().toString().equals("")&&isPhoneValid(et_account.getText().toString())) {

                    sendMsg();
                }else {
                    ToastUtils.showToast(mContext,"请输入正确的手机号");
                }
                break;

        }

    }

    private void sendMsg() {
        String phone = et_account.getText().toString().trim();
        code = new Random().nextInt(8999)+1000;
//        String phone = "18395960703";
        String content = "验证码："+code;
        StringBuffer buffer = new StringBuffer();
        buffer.append(Constants.MSG_NAME);
        buffer.append(Constants.MSG_ACCOUNT);
        buffer.append(Constants.MSG_PSW);
        buffer.append(phone);
        buffer.append(content);
        buffer.append(Constants.MSG_SIGN);
        buffer.append("");
        Log.i(TAG, "sendMsg: "+buffer.toString());
        String mac = MD5.Encryption(buffer.toString());
        Log.i(TAG, "sendMsg: "+mac);
        final MessageEntity messageEntity = new MessageEntity(Constants.MSG_NAME,Constants.MSG_ACCOUNT,Constants.MSG_PSW,phone,content,Constants.MSG_SIGN,
                "",mac);
        String req = gson.toJson(messageEntity);
        Log.i(TAG, "sendMsg: "+req);
        String base64 = null;
        try {
            base64 = Base64.encodeToString(req.getBytes("utf-8"),Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "sendMsg: "+base64);
        OkHttpUtils.postString().url("http://112.35.1.155:1992/sms/norsubmit")
                .content(base64)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                MessageRtnEntity messageRtnEntity = gson.fromJson(response,MessageRtnEntity.class);
                if (messageRtnEntity.isSuccess()) {
                    ToastUtils.showToast(mContext,"发送成功");
                    CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(tv_code,30000,1000);
                    countDownTimerUtils.start();
                }else {
                    ToastUtils.showToast(mContext,"发送失败");
                }
            }
        });

    }
    class MessageRtnEntity{

        /**
         * msgGroup : 0622141526001000545834
         * rspcod : success
         * success : true
         */

        private String msgGroup;
        private String rspcod;
        private boolean success;

        public String getMsgGroup() {
            return msgGroup;
        }

        public void setMsgGroup(String msgGroup) {
            this.msgGroup = msgGroup;
        }

        public String getRspcod() {
            return rspcod;
        }

        public void setRspcod(String rspcod) {
            this.rspcod = rspcod;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }


    private void regist() {
        RetrofitClient.createService(RegistAPI.class)
                .httpRegist(account,account,"", carNum, id, address, psw)
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
                            intent.putExtra("account", account);
                            intent.putExtra("psw", psw);
                            setResult(100, intent);
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
