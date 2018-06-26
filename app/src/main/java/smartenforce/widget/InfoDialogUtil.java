package smartenforce.widget;


import android.app.AlertDialog;
import android.content.Context;

public class InfoDialogUtil {
   static AlertDialog.Builder builder;

    public static void  showInfoDialog(Context context, String msg){
         if (builder==null){
             builder=new AlertDialog.Builder(context);
             builder.setCancelable(false);
         }
        builder.setTitle("提示").setMessage(msg).setPositiveButton("确定",null).show();

    }

}
