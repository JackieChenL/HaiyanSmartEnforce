package com.kas.clientservice.haiyansmartenforce.tcsf.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

public class PayInfoDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView tev_money, tev_weixin, tev_none;

    private Context mContext;
    private View.OnClickListener listener;
    private String title;
    private String body;

    public PayInfoDialog(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = onClickListener;
    }


    public PayInfoDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public PayInfoDialog setBody(String body) {
        this.body = body;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pay_info_dialog);
        setCanceledOnTouchOutside(false);
        titleTxt = (TextView) findViewById(R.id.tev_title);
        contentTxt = (TextView) findViewById(R.id.tev_body);
        tev_money = (TextView) findViewById(R.id.tev_money);
        tev_weixin = (TextView) findViewById(R.id.tev_weixin);
        tev_none = (TextView) findViewById(R.id.tev_none);
        tev_money.setOnClickListener(this);
        tev_weixin.setOnClickListener(this);
        tev_none.setOnClickListener(this);
    }


    public void showSpinnerString(CharSequence text) {
        this.show();
        if (title != null) {
            titleTxt.setText(title);
        }
        contentTxt.setText(text);

    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
        dismiss();

    }


}
