package smartenforce.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;

import java.io.File;

import smartenforce.base.HttpApi;
import smartenforce.base.ShowTitleActivity;
import smartenforce.widget.view.PhotoView;


public class ViewPhotoActivity extends ShowTitleActivity {


    private PhotoView img_iv ;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_image);
    }

    @Override
    protected void findViews() {
        img_iv= (PhotoView) findViewById(R.id.img_iv);

    }

    @Override
    protected void initDataAndAction() {
        url=getIntent().getStringExtra("Url");
        if (url.startsWith("UploadImage")) {
            Glide.with(aty).load(HttpApi.URL_IMG_HEADER + url).into(img_iv);
        } else if (url.startsWith("http")) {
            Glide.with(aty).load( url).into(img_iv);
        } else {
            Glide.with(aty).load(new File(url)).into(img_iv);
        }
    }

}




















