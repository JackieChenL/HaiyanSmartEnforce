package smartenforce.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.kas.clientservice.haiyansmartenforce.MyApplication;

import smartenforce.util.LogUtil;
import smartenforce.util.StringUtil;
import smartenforce.util.ToastUtil;


public class BaseFragment extends Fragment {
    protected final static String TAG="TAG";
    protected Context context ;
    protected CommonActivity aty;
    protected MyApplication app;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        aty= (CommonActivity) getActivity();
        app = (MyApplication)aty.getApplication();
    }

    protected  void log(String msg){
        LogUtil.e(TAG,msg+"---------->");
    }

    protected  void log(String TAG, String MSG){
        LogUtil.e(TAG,MSG+"---------->");
    }

    protected  void show(String MSG){
        ToastUtil.show(context,MSG);
    }

    protected  void warningShow(String MSG){
        new AlertView("提醒", MSG, "确定", null, null, context, AlertView.Style.Alert, null
        ).show();
    }
    protected String getText(TextView tev){
        return tev.getText().toString().trim();

    }

    protected  boolean isEmpty(TextView tev){
        return  StringUtil.isEmptyString(tev.getText().toString().trim());
    }
    protected  boolean isEmpty(String value){
        return  StringUtil.isEmptyString(value);
    }

    protected void closeKeybord() {
        InputMethodManager imm = (InputMethodManager)aty. getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && aty.getWindow().getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(aty.getWindow().getCurrentFocus().getWindowToken(), 0);
    }


}
