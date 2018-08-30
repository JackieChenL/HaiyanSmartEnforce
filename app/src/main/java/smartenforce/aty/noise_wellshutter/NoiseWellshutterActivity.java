package smartenforce.aty.noise_wellshutter;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.CommonActivity;


public class NoiseWellshutterActivity extends CommonActivity {
    private WebView webview;
    private ProgressDialog progressDialog;
    private final static String DefalutUrl="http://117.149.146.131:6111/Monitor/Map";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        progressDialog = ProgressDialog.show(aty, "提示", "加载中...", false, false);
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        String src = getIntent().getStringExtra("src");
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
                    webview.stopLoading();
                    return true;
                }
                return false;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                log("onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                log("onPageFinished");
                progressDialog.dismiss();
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                log("onProgressChanged："+newProgress);
            }
        });

        if (isEmpty(src)) {
            webview.loadUrl(DefalutUrl);
        } else{
            webview.loadUrl(DefalutUrl+"?src="+src);
        }

    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog=null;
        webview.removeAllViews();
        webview.destroy();
    }

    protected void onDestroy() {
        super.onDestroy();
        progressDialog=null;
        webview.removeAllViews();
        webview.destroy();
    }


}
















