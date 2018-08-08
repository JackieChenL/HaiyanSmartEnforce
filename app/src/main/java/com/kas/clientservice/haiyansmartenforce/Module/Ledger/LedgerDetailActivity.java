package com.kas.clientservice.haiyansmartenforce.Module.Ledger;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.LedgerListEntity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

public class LedgerDetailActivity extends BaseActivity {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_ledgerDetail_area)
    TextView tv_area;
    @BindView(R.id.tv_ledgerDetail_text)
    TextView tv_text;
    @BindView(R.id.tv_ledgerDetail_time)
    TextView tv_time;
    @BindView(R.id.tv_ledgerDetail_type)
    TextView tv_type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ledger_detail;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("台账详情");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LedgerListEntity.RowsBean rowsBean = gson.fromJson(getIntent().getStringExtra("json"),LedgerListEntity.RowsBean.class);
        tv_area.setText(rowsBean.getNameAre());
        tv_text.setText(rowsBean.getText());
        tv_time.setText(rowsBean.getCreateTime().replaceAll("T"," ").substring(0,rowsBean.getCreateTime().indexOf(".")));
        tv_type.setText(rowsBean.getLedgerTypeName());

    }

}
