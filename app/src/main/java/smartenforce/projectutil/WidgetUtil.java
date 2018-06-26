package smartenforce.projectutil;


import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import smartenforce.util.StringUtil;


public class WidgetUtil {
    public static String str = "";

    public static String traversalView(ViewGroup viewGroup, boolean isFirst) {
        if (isFirst) {
            str = "";
        }
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                traversalView((ViewGroup) view, false);
            } else if (view instanceof TextView) {
                TextView tev = (TextView) view;
                str += tev.getText().toString().trim();
            }
        }
        return str;
    }


    public static boolean isFull(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                traversalView((ViewGroup) view, false);
            } else if (view instanceof TextView) {
                TextView tev = (TextView) view;
                if (StringUtil.isEmptyString(tev.getText().toString().trim())) {
                    return false;
                }
            }
        }
        return true;
    }

}
