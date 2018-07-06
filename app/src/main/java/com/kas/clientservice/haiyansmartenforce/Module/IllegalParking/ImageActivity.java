package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.SPUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class ImageActivity extends BaseActivity {
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
            Log.i(TAG, "initResAndListener: "+url);
            Picasso.with(this).load(url).placeholder(R.drawable.normal_pic).error(R.drawable.normal_pic).into(imageView);
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