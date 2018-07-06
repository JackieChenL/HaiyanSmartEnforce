package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.SPUtils;

import butterknife.BindView;

/**
 * Created by DELL_Zjcoms02 on 2018/7/5.
 */

public class XieTongImage extends BaseActivity {
    @BindView(R.id.iv_orderdetailImage)
    ImageView imageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail_image;
    }

    @Override
    protected String getTAG() {
        return "OrderDetailImageActivity";
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        String url = getIntent().getStringExtra("url");
        if (url != null) {
            Glide.with(mContext).load(url).into(imageView);
        }
        String path = getIntent().getStringExtra("uri");
        if (path != null) {
            Bitmap bmp = BitmapFactory.decodeFile(path);//filePath
            imageView.setImageBitmap(bmp);
        }
        String image = getIntent().getStringExtra("image");
        if (image != null) {
            String byteString = (String) SPUtils.get(mContext, "ImageClick","");
            byte[] bytes = byteString.getBytes();

            Log.i(TAG, "initResAndListener: "+bytes.length);
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bmp);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
