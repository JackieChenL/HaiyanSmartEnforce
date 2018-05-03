package com.kas.clientservice.haiyansmartenforce.tcsf.widget;

import android.content.Context;
import android.graphics.Rect;

import com.kas.clientservice.haiyansmartenforce.tcsf.util.UIUtil;


/**
 *
 */
public class EasyPrBiz {

    /**
     * 获取默认的预览区
     * 车牌比例440*140
     */
    public static Rect getDefRectFrame(Context context) {
        int screenWidth = UIUtil.getScreenWidth(context);
        int screenHeight = UIUtil.getScreenHeight(context);
        int rectLeft = screenWidth / 15;
        int rectTop = screenHeight * 3 / 9;
        int rectRight = screenWidth * 14/ 15;
        int rectBottom = rectTop+(rectRight-rectLeft)*14/44;
        return new Rect(rectLeft, rectTop, rectRight, rectBottom);
    }
}
