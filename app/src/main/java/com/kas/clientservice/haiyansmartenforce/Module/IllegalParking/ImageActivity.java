package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
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
        if (url!=null) {
            Picasso.with(this).load(url).into(imageView);
        }
        String uri = getIntent().getStringExtra("uri");
        if (uri!=null) {
            Bitmap bmp = BitmapFactory.decodeFile(uri);//filePath
            imageView.setImageBitmap(bmp);
        }
        byte[] bytes = getIntent().getByteArrayExtra("image");
        if (bytes!=null) {
            Bitmap bmp=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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