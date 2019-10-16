package com.utte.growon.one.net;

import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OneNetWork {

    private static OkHttpClient mOkHttpClient;

    // one首页获取 前缀后缀中间写要请求的数据日期 格式如：2018-10-16
    // "http://v3.wufazhuce.com:8000/api/channel/one/2018-10-16/0?channel=14&sign=3bf8751865ff6b0012607e82f6ae7d60&version=4.5.5&uuid=ffffffff-952b-ee3f-1660-13941dc55507&platform=android HTTP/1.1"
    private static final String ONE_FIRST_PAGE_URL_PREFIX = "http://v3.wufazhuce.com:8000/api/channel/one/";
    private static final String ONE_FIRST_PAGE_URL_SUFFIX = "/0?channel=14&sign=3bf8751865ff6b0012607e82f6ae7d60&version=4.5.5&uuid=ffffffff-952b-ee3f-1660-13941dc55507&platform=android HTTP/1.1";

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

    }


    public static void getOneData(String date, Callback callback) {

        String url = ONE_FIRST_PAGE_URL_PREFIX.concat(date).concat(ONE_FIRST_PAGE_URL_SUFFIX);
        final Request request = new Request.Builder()
                .url(url)
//                .addHeader("Accept-Encoding", "gzip,deflate")
//                .addHeader("Accept-Encoding", "gzip,deflate")
                .addHeader("User-Agent", "android-async-http/2.0 (http://loopj.com/android-async-http)")
                .addHeader("Host", "v3.wufazhuce.com:8000")
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);


    }

}
