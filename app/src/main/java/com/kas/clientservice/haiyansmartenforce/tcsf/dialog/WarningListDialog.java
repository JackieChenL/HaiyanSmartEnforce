package com.kas.clientservice.haiyansmartenforce.tcsf.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.adapter.ExitListAdapter;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.TcListBeanResult;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.UIUtil;

import java.util.List;

public class WarningListDialog extends Dialog implements View.OnClickListener {
    private TextView titleTxt;
    private TextView tev_confirm;
    private RecyclerView rcy_list;
    private List<TcListBeanResult> objList;

    private Context mContext;
    private View.OnClickListener listener;
    private String title;
    private String ConmfirmText;
    private ExitListAdapter adapter;

    public WarningListDialog(Context context, List<TcListBeanResult> objList, View.OnClickListener onClickListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        adapter = new ExitListAdapter(objList, context);
        this.objList = objList;
        this.listener = onClickListener;
    }


    public WarningListDialog setTitle(String title) {
        this.title = title;
        return this;
    }


    public WarningListDialog setConfirm(String ConmfirmText) {
        this.ConmfirmText = ConmfirmText;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history_warning_dialog);
        this.getWindow().setLayout((int)(UIUtil.getScreenWidth(mContext)*0.85), this.getWindow().getAttributes().height);
        setCanceledOnTouchOutside(false);
        titleTxt = (TextView) findViewById(R.id.tev_title);
        tev_confirm = (TextView) findViewById(R.id.tev_confirm);
        rcy_list = (RecyclerView) findViewById(R.id.rcy_list);
        tev_confirm.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rcy_list.setLayoutManager(layoutManager);
        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);

        }
        if (!TextUtils.isEmpty(ConmfirmText)) {
            tev_confirm.setText(ConmfirmText);

        }
        rcy_list.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tev_confirm:
                if (listener != null) {
                    listener.onClick(v);
                }
                this.dismiss();
                break;
        }
    }


}
