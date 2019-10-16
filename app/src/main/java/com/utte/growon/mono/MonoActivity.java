package com.utte.growon.mono;

import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.utte.growon.R;

import java.util.HashMap;
import java.util.Map;

public class MonoActivity extends AppCompatActivity {

    MyWebView mWebView;
    private static final String TAG = "MonoActivity";
    String url = "https://mmmono.com/g/meow/1504999/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mono);

        initWebView();

        url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
            Log.d(TAG, "onCreate: " + url);
        } else {
            long id = getIntent().getLongExtra("id", 0);
            /**
             * 给webView加载也加上头，这样就不会显示下载的框框了
             */
            Map<String, String> extraHeaders;
            extraHeaders = new HashMap<>();
            extraHeaders.put("Host", "mmmono.com");
            extraHeaders.put("Connection", "keep-alive");
            extraHeaders.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; ONEPLUS A3010 Build/OPR1.170623.032; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044306 Mobile Safari/537.36");
            extraHeaders.put("http-authorization", "58a38bd4d9b711e88f54525400b827a7");
            extraHeaders.put("mono-user-agent", "api-client/2.0.0 com.mmmono.mono/3.6.8 Android/8.0.0 ONEPLUS A3010 channel/qq");
            extraHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,image/tpg,*/*;q=0.8\n");
            extraHeaders.put("Accept-Encoding", "gzip, deflate");
            extraHeaders.put("Accept-Language", "zh-CN,en-US;q=0.8");
            extraHeaders.put("Cookie", "bid=8214abf6; SERVER_ID=1912e68c-e0506478; session=eyJfZnJlc2giOmZhbHNlLCJfcGVybWFuZW50Ijp0cnVlfQ.Dr_kJQ.czgf1HD1rQB9-OQ3BQ1Qo_1K4QQ; _ga=GA1.2.308643949.1540624282; _gid=GA1.2.490867005.1541295656; _gat=1; Hm_lvt_71177825c90433c2abc2768db5734b17=1540624282; Hm_lpvt_71177825c90433c2abc2768db5734b17=1541296803\n");

            mWebView.loadUrl("https://mmmono.com/g/meow/" + id + "/", extraHeaders);
            Log.d(TAG, "onCreate: " + "yyyyyyyyyyyyyyyyyyyy");
        }
    }


    private void initWebView() {
        mWebView = findViewById(R.id.wv);
        //支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);


        /*
         * 设置5.0之后支持https http混合加载
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        /*
         * 如果不设置WebViewClient，请求会跳转系统浏览器
         */
        mWebView.setWebViewClient(new WebViewClient() {
            /**
             * 接受所有证书
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    /**
     * 防止音乐继续播放
     */
    @Override
    protected void onDestroy() {
        if (mWebView != null) {
//            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            mWebView.clearHistory();
//            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
//            mWebView = null;
        }
        super.onDestroy();
    }



}
