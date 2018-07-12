package com.kas.clientservice.haiyansmartenforce.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.kas.clientservice.haiyansmartenforce.R;

/**
 * 描述：
 * 时间：2018-07-10
 * 公司：COMS
 */

public class EditviewDialog {
    private Context mContext;
    private OnEditviewDialogClick onEditviewDialogClick;
    AlertDialog.Builder alertDialog;
    EditText editText;
    public EditviewDialog(Context mContext,OnEditviewDialogClick onEditviewDialogClick) {
        this.mContext = mContext;
        this.onEditviewDialogClick = onEditviewDialogClick;
    }

    public void showDialog(){
        alertDialog = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.editview_dialog,null);
        editText = (EditText) view.findViewById(R.id.et_dialog);
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onEditviewDialogClick.onPositiveClick(editText.getText().toString().trim());
            }
        });
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        alertDialog.setTitle("  ");
        alertDialog.show();
    }

    public interface OnEditviewDialogClick{
        void onPositiveClick(String content);
//        void onPositiveClick(String content);
    }
}
