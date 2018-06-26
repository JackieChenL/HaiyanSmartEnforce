package smartenforce.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.util.UIUtil;


public class EditDialog extends Dialog implements View.OnClickListener {
    private EditText edt_input;
    private TextView titleTxt;
    private TextView rightTxt;
    private TextView leftTxt;

    private Context mContext;


    private BtnClickCallBack listener;
    private String title;
    private String body;
    private String leftBtn;
    private String rightBtn;

    public interface BtnClickCallBack {
        void onClick(int postion, String txt);
    }

    public EditDialog(Context context, String title, String body, String leftBtn, String rightBtn, BtnClickCallBack onBtnClickListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.title = title;
        this.body = body;
        this.leftBtn = leftBtn;
        this.rightBtn = rightBtn;
        this.listener = onBtnClickListener;
    }

    public EditDialog(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
    }


    public EditDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public EditDialog setBody(String body) {
        this.body = body;
        return this;
    }

    public EditDialog setleftBtn(String left) {
        this.leftBtn = left;
        return this;
    }

    public EditDialog setRightBtn(String right) {
        this.rightBtn = right;
        return this;
    }

    public EditDialog setListener(BtnClickCallBack listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_edit);
        this.getWindow().setLayout((int) (UIUtil.getScreenWidth(mContext) * 0.9), this.getWindow().getAttributes().height);
        setCanceledOnTouchOutside(false);
        titleTxt = (TextView) findViewById(R.id.tev_title);
        edt_input = (EditText) findViewById(R.id.edt_input);
        leftTxt = (TextView) findViewById(R.id.tev_left);
        rightTxt = (TextView) findViewById(R.id.tev_right);
        leftTxt.setOnClickListener(this);
        rightTxt.setOnClickListener(this);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                edt_input.requestFocus();
                InputMethodManager inputMgr = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.showSoftInput(edt_input, 0);


            }
        });
    }




    /**
     * @param type {1:number;text}
     */
    public void showWithEditType(int type) {
        this.show();
        if (type == 1) {
            edt_input.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            edt_input.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        edt_input.setText(body);
        leftTxt.setText(leftBtn);
        rightTxt.setText(rightBtn);
        if (!TextUtils.isEmpty(body))
            edt_input.setSelection(body.length());
        if (TextUtils.isEmpty(title)) {
        } else {
            titleTxt.setText(title);
        }

    }


    @Override
    public void onClick(View v) {
        String txt = edt_input.getText().toString().trim();
        switch (v.getId()) {
            case R.id.tev_left:
                if (listener != null) {
                    listener.onClick(0, txt);
                }
                break;
            case R.id.tev_right:
                if (listener != null) {
                    listener.onClick(1, txt);
                }
                break;
        }
        InputMethodManager inputMgr = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMgr.hideSoftInputFromWindow(edt_input.getWindowToken(), 0);
        dismiss();
    }


}
