package com.utte.growon.one.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.utte.growon.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OneContentActivity extends AppCompatActivity {

    String id = "";
    String baseUrl = "http://v3.wufazhuce.com:8000/api/essay/htmlcontent/";
    String canshu = "?channel=yingyongbao&sign=91dddfd04703bf5022393b4ee287ea87&source=summary&source_id=16670&version=4.5.6&uuid=ffffffff-952b-ee3f-1660-13941dc55507&platform=android";
    String url;
    private static final String TAG = "OneContentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_content);

        id = getIntent().getStringExtra("id");
        url = baseUrl + id + canshu;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();

        final TextView title = findViewById(R.id.title);
        final TextView author = findViewById(R.id.author);
        final TextView content = findViewById(R.id.content);

        final Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                Drawable drawable = null;
                URL url;
                try {
                    url = new URL(source);
                    drawable = Drawable.createFromStream(url.openStream(), "");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, "onResponse: " + url);
//                Log.d(TAG, "onResponse: " + response.body().string());
                String html = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("data");
//                    Log.d(TAG, "onResponse: " + jsonObject);
//                    OneData.contentData = new Gson().fromJson(jsonObject.toString(), OneContentData.class);
                    html = jsonObject.getString("html_content");
                    // Todo: Jsoup解析一下
                    Log.d(TAG, "onResponse: " + html.substring(html.indexOf("body", 0), html.length()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
