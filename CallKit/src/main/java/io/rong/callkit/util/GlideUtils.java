package io.rong.callkit.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by dengxudong on 2018/5/18.
 */

public class GlideUtils {

    public static void showBlurTransformation(Context context, ImageView imageView ,Uri val){
        if(val==null){return;}
        Glide.with(context)
                .load(val)
                .centerCrop()
//                .apply(RequestOptions.bitmapTransform(new GlideBlurformation(context)))
//                .apply(new RequestOptions().centerCrop())
                .into(imageView);
    }



}
