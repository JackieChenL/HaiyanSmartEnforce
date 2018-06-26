package smartenforce.projectutil;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;


import java.util.List;

import smartenforce.util.DateUtil;
import smartenforce.util.ImgUtil;
import smartenforce.util.WaterMaskUtil;

public class Base64Util {

    //获取图片数据的base64字符串,逗号分隔
    public static String toBase64String(Context context, List<String> list, boolean isPdfPic) {
        String base64Str = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (isPdfPic) {
                    Bitmap bmp = WaterMaskUtil.drawTextToRightBottom(context, ImgUtil.getPdfImg(list.get(i),0,280), DateUtil.currentTime(), 5, Color.WHITE, 5, 5);
                    base64Str += (ImgUtil.bitmapToBase64(bmp) + ",");
                } else {
                    base64Str += (ImgUtil.bitmapToBase64(ImgUtil.getFileImg(list.get(i))) + ",");
                }

            }
        }
        return base64Str.endsWith(",") ? base64Str.substring(0, base64Str.length() - 1) : base64Str;
    }


}
