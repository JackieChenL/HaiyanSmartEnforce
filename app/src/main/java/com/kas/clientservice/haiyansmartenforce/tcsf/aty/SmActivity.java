package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.CpBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.FileUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ImgUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.widget.EasyPRPreSurfaceView;
import com.kas.clientservice.haiyansmartenforce.tcsf.widget.EasyPRPreView;
import com.kas.clientservice.haiyansmartenforce.tcsf.widget.ProgressDialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 扫描（占用车位列表）
 */
public class SmActivity extends BaseActivity {
    private EasyPRPreView preSurfaceView;
    private Button btn_sm;
    private final String url = "http://jisucpsb.market.alicloudapi.com/licenseplaterecognition/recognize";
    private final String AppKey = "24553193";
    private final String AppCode = "APPCODE 2e476d97d6994a489afb3491b44a2578";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpsm);

        preSurfaceView = (EasyPRPreView) findViewById(R.id.preSurfaceView);
        btn_sm = (Button) findViewById(R.id.btn_sm);
        preSurfaceView.setPictureTakenListener(new EasyPRPreSurfaceView.OnPictureTakenListener() {
            @Override
            public void onPictureTaken(byte[] data) {
                Bitmap map = ImgUtil.getimage(data);
                recognition(map);
            }
        });


        btn_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogUtil.show(aty, "正在识别.....");
                preSurfaceView.recognize();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preSurfaceView != null) {
            preSurfaceView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (preSurfaceView != null) {
            preSurfaceView.onStop();
        }
    }

    public void recognition(Bitmap bitmap) {
        final String base64bmp = ImgUtil.bitmapToBase64(bitmap);
        final String filePath = FileUtil.saveBitmap(bitmap);
        OkHttpUtils.post().url(url)
                .addHeader("X-Ca-Key", AppKey)
                .addHeader("Authorization", AppCode)
                .addParams("pic", base64bmp).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                onCpjxReturn(false, "图片解析异常", filePath);
            }

            @Override
            public void onResponse(String response, int id) {
                log(response);
                final CpBean bean = JSON.parseObject(response, CpBean.class);
                if (bean.status .equals("0")&&bean.result!=null) {
                    onCpjxReturn(true, bean.result.number, filePath);
                } else {
                    onCpjxReturn(false, "车牌解析错误", filePath);
                }
            }



                                                             }

        );

    }


    public void onCpjxReturn(final boolean isResponse, final String msg, final String path) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
                if (!isResponse) {
                    warningShow( msg);
                    preSurfaceView.getPreView().onFail();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("path", path).putExtra("car", msg);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        });


    }

}
