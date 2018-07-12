package com.kas.clientservice.haiyansmartenforce.Module.PersonalInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Login.LoginActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Register.ResetPswActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.EditviewDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;

import butterknife.BindView;

public class PersonalInfoEditActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface {
    @BindView(R.id.rl_infoEdit_icon)
    RelativeLayout rl_icon;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.rl_infoEdit_sex)
    RelativeLayout rl_sex;
    @BindView(R.id.rl_infoEdit_birth)
    RelativeLayout rl_birth;
    @BindView(R.id.rl_infoEdit_describe)
    RelativeLayout rl_describe;
    @BindView(R.id.rl_infoEdit_reset)
    RelativeLayout rl_reset;
    @BindView(R.id.tv_infoEdit_exit)
    TextView tv_exit;
    @BindView(R.id.iv_infoEdit_icon)
    ImageView iv_icon;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_infoEdit_describe)
    TextView tv_des;
    @BindView(R.id.tv_infoedit_sex)
    TextView tv_sex;
    @BindView(R.id.tv_infoedit_birth)
            TextView tv_birth;


    String describe = "";
    TimePickerDialog timePickerDialog;
    String birth;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info_edit;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("个人信息");
        iv_back.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_birth.setOnClickListener(this);
        rl_reset.setOnClickListener(this);
        rl_describe.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.rl_infoEdit_icon:
                break;
            case R.id.rl_infoEdit_sex:
                choseSex();
                break;
            case R.id.rl_infoEdit_birth:
                timePickerDialog = new TimePickerDialog(mContext);
                timePickerDialog.showDatePickerDialog();
                break;
            case R.id.rl_infoEdit_describe:
                showEditDialog();
                break;
            case R.id.rl_infoEdit_reset:
                startActivity(new Intent(mContext, ResetPswActivity.class));
                break;
            case R.id.tv_infoEdit_exit:
                startActivityWithoutBack(mContext, LoginActivity.class,null);
                break;
        }
    }

    Dialog mDialog;

    private void choseSex() {
        final String[] datas = new String[]{"男", "女"};
        mDialog = new AlertDialog.Builder(this).setSingleChoiceItems(datas, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
//                        gotoLive(info);
                        break;
                    case 1:
//                        gotoPlayback(info);
                        break;
                    default:
                        break;
                }
                tv_sex.setText(datas[which]);
            }

        }).create();
        mDialog.show();
    }

    private void showEditDialog() {
        EditviewDialog editviewDialog = new EditviewDialog(mContext, new EditviewDialog.OnEditviewDialogClick() {
            @Override
            public void onPositiveClick(String content) {
                tv_des.setText(content);
                describe = content;
            }
        });
        editviewDialog.showDialog();
    }

    @Override
    public void positiveListener() {
        birth = timePickerDialog.getYear() + "-" + timePickerDialog.getMonth() + "-" + timePickerDialog.getDay();
        tv_birth.setText(birth);
    }

    @Override
    public void negativeListener() {

    }
}
