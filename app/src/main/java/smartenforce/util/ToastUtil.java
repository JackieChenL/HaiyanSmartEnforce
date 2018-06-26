package smartenforce.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class ToastUtil {

    private static Toast toast = null;

    public static void show(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, "" , Toast.LENGTH_LONG);
        }
        toast.setText(content);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



}
