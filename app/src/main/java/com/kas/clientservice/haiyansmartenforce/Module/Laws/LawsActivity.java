package com.kas.clientservice.haiyansmartenforce.Module.Laws;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class LawsActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_heaer_back)
    ImageView iv_heaer_back;
    @BindView(R.id.tv_header_title)
    TextView tv_header_title;
    @BindView(R.id.lv_law_list)
    ListView lv_law_list;
    List<LawsBean> list;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_laws;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        iv_heaer_back.setOnClickListener(this);
        tv_header_title.setText("法律法规");
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("type","getlist");
        map.put("TitleReg", "城市");
        map.put("MaintextReg", "");
        map.put("IssuedNumberReg", "");
        map.put("IssueDateRegStart", "");
        map.put("IssueDateRegEnd", "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_heaer_back:
                finish();
                break;
        }
    }

}
