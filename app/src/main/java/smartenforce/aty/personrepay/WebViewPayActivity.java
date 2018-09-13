package smartenforce.aty.personrepay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.CommonActivity;
import smartenforce.bean.tcsf.ArrearsBean;


public class WebViewPayActivity extends CommonActivity {
    private WebView webview;
    private ArrearsBean arrearsBean;

    private final static String DefalutUrl="http://hywx.hnzhzf.top/wxpay/H5Default.aspx";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
              if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivityForResult(intent,100);
                    return true;
                }
                return false;

            }



        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        arrearsBean = (ArrearsBean) getIntent().getSerializableExtra("ArrearsBean");

        webview.loadUrl(DefalutUrl+"?berthid="+arrearsBean.ID+"&money="+arrearsBean.cash_fee);

    }



    protected void onDestroy() {
        super.onDestroy();
        webview.removeAllViews();
        webview.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         finish();
    }
}
















