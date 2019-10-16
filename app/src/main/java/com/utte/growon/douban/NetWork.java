package com.utte.growon.douban;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utte.growon.douban.model.DoubanData;
import com.utte.growon.douban.model.HotMovie;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 江婷婷 on 2017/11/25.
 */

public class NetWork {

    private static final String TAG = "NetWork";

    public static void requestCategoryData(String url) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ParseWork.parseCategoryData(response);
                EventBus.getDefault().post(new MessageEvent("find page data already"));
            }
        });
    }

    public static void requestDouban(String url, final String tag) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, "豆瓣豆瓣豆瓣豆瓣豆瓣豆瓣豆瓣豆瓣豆瓣豆瓣 " + response.body().string());
                try {
                    JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("subjects");
                    DoubanData.subjectsData = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HotMovie>>(){}.getType());
                    for (HotMovie h : DoubanData.subjectsData) {

                        switch (tag) {
                            case "热门":
                                DoubanData.subjects1.add(h);
                                break;
                            case "最新":
                                DoubanData.subjects2.add(h);
                                break;
                            case "经典":
                                DoubanData.subjects3.add(h);
                                break;
                            case "豆瓣高分":
                                DoubanData.subjects4.add(h);
                                break;
                            case "冷片佳片":
                                DoubanData.subjects5.add(h);
                                break;
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EventBus.getDefault().post(new MessageEvent("douban data already"));
            }
        });
    }
}
