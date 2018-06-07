package com.kas.clientservice.haiyansmartenforce.tcsf.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

public class InfoDialog extends Dialog implements View.OnClickListener{
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView rightTxt;
    private TextView leftTxt;

    private Context mContext;
    private OnBtnClickListener listener;
    private String title;
    private String body;
    private String leftBtn;
    private String rightBtn;

    public InfoDialog(Context context,String title,String body,String leftBtn,String rightBtn,OnBtnClickListener onBtnClickListener) {
        super(context,R.style.dialog);
        this.mContext = context;
        this.title=title;
        this.body=body;
        this.leftBtn=leftBtn;
        this.rightBtn=rightBtn;
        this.listener=onBtnClickListener;
    }


    public InfoDialog setTitle(String title){
        this.title = title;
        return this;
    }
    public InfoDialog setBody(String body){
        this.body = body;
        return this;
    }

    public InfoDialog setleftBtn(String left){
        this.leftBtn = left;
        return this;
    }

    public InfoDialog setRightBtn(String right){
        this.rightBtn = right;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);
        setCanceledOnTouchOutside(false);
        titleTxt = (TextView)findViewById(R.id.tev_title);
        contentTxt = (TextView)findViewById(R.id.tev_body);
        leftTxt = (TextView)findViewById(R.id.tev_left);
        rightTxt = (TextView)findViewById(R.id.tev_right);
        leftTxt.setOnClickListener(this);
        rightTxt.setOnClickListener(this);
    }


    public void showView(){
        this.show();
        titleTxt.setText(title);
        contentTxt.setText(body);
        leftTxt.setText(leftBtn);
        rightTxt.setText(rightBtn);
    }

    public void showSpinnerString(CharSequence text){
        this.show();
        titleTxt.setText(title);
        contentTxt.setText(text);
        leftTxt.setText(leftBtn);
        rightTxt.setText(rightBtn);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tev_left:
                if(listener != null){
                    listener.onLeftClick();
                }
                this.dismiss();
                break;
            case R.id.tev_right:
                if(listener != null){
                    listener.onRightClick();
                }
                this.dismiss();
                break;
        }
    }

    public interface OnBtnClickListener{
        void onLeftClick();
        void onRightClick();
    }
}
