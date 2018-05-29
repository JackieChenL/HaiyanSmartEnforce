package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;

import butterknife.BindView;

public class HuanWeiEntryActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_huanwei_handle)
    TextView tv_handle;
    @BindView(R.id.tv_huanwei_search)
    TextView tv_search;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;

    String type = "";
    int typeId = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_huan_wei_entry;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();


        type = UserSingleton.USERINFO.getType();
        Log.i(TAG, "initResAndListener: "+type);
        if (type.equals("5")) {
            tv_handle.setText("审核");
            typeId = 5;
        }else if (type.equals("6")){
            tv_handle.setText("检查录入");
            typeId = 6;
        }else if (type.equals("7")){
            tv_handle.setText("整改");
            typeId = 7;
        }
        tv_title.setText("四位一体");
        iv_back.setOnClickListener(this);
        tv_handle.setOnClickListener(this);
        tv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_huanwei_handle:
                Log.i(TAG, "onClick: "+typeId);
                switch (typeId) {
                    case 5:
                        Intent intent = new Intent(mContext,HuanweiCheckListActivity.class);
                        intent.putExtra("Type",typeId);
                        startActivity(intent);
                        break;
                    case 6:
                        startActivity(new Intent(mContext,HuanweiCommitActivity.class));
                        break;
                    case 7:
                        Intent intent2 = new Intent(mContext,HuanweiCheckListActivity.class);
                        intent2.putExtra("Type",typeId);
                        startActivity(intent2);
                        break;
                }
                break;
            case R.id.tv_huanwei_search:
                startActivity(new Intent(mContext,HuanweiHistoryActivity.class));
                break;

        }
    }
}
