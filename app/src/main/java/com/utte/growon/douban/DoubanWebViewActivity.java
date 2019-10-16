package com.utte.growon.douban;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.utte.growon.R;
import com.utte.growon.douban.model.DoubanData;
import com.utte.growon.douban.model.DoubanDetail;
import com.utte.growon.douban.model.Person;
import com.utte.growon.douban.model.PhotoUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DoubanWebViewActivity extends AppCompatActivity {

    private static final String TAG = "DoubanWebViewActivity";
    TextView title;
    TextView rate;
    TextView information;
    ImageView image;
    TextView intro;
    RecyclerView personListView;
    RecyclerView photoListView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_web_view);
//        WebView webView = findViewById(R.id.web_view);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(getIntent().getStringExtra("url"));
        getDeviceDensity();
        button = findViewById(R.id.back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = findViewById(R.id.title);
        rate = findViewById(R.id.rate);
        information = findViewById(R.id.information);
        image = findViewById(R.id.image);
        personListView = findViewById(R.id.person_list_view);
        photoListView = findViewById(R.id.photo_list_view);
        intro = findViewById(R.id.intro);

        String id = getIntent().getStringExtra("id");
        String url = "https://m.douban.com/rexxar/api/v2/movie/" + id;

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
//                final DoubanDetail detail = new Gson().fromJson(response.body().string(), DoubanDetail.class);

//                DoubanData.detail = detail;
//
//                DoubanData.personList.clear();
//                if (detail.directors.size() < 3) {
//                    for (int i = 0; i < detail.directors.size(); i++) {
//                        if (!detail.directors.get(i).roles.get(0).equals("演员")) {
//                            DoubanData.personList.add(new Person(detail.directors.get(i).cover_url,
//                                    detail.directors.get(i).name, detail.directors.get(i).roles.get(0)));
//                        } else {
//                            DoubanData.personList.add(new Person(detail.directors.get(i).cover_url,
//                                    detail.directors.get(i).name, "制片"));
//                        }
//                    }
//                    for (int i = 0; i < detail.actors.size() && i + detail.directors.size() < 8; i++) {
//                        DoubanData.personList.add(new Person(detail.actors.get(i).cover_url,
//                                detail.actors.get(i).name, detail.actors.get(i).roles.get(0)));
//                    }
//                } else {
//                    for (int i = 0; i < 3; i++) {
//                        DoubanData.personList.add(new Person(detail.directors.get(i).cover_url,
//                                detail.directors.get(i).name, detail.directors.get(i).roles.get(0)));
//                    }
//                    for (int i = 0; i < 5; i++) {
//                        DoubanData.personList.add(new Person(detail.actors.get(i).cover_url,
//                                detail.actors.get(i).name, detail.actors.get(i).roles.get(0)));
//                    }
//                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        title.setText(detail.title);
//                        rate.setText(String.valueOf("豆瓣评分：" + detail.rating.value));
//
//                        String info = detail.year + " / ";
//
//                        for (String kind : detail.genres) {
//                            info = info + " / " + kind;
//                        }
//
//                        info = info + "\n" + "上映时间：" + detail.pubdate.get(0) + "\n" + "片长：" + detail.durations.get(0);
//
//                        information.setText(info);
//                        Glide.with(DoubanWebViewActivity.this).load(detail.pic.large).
//                                into(image);
//                        intro.setText(detail.intro);

////                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                        LinearLayoutManager manager = new LinearLayoutManager(DoubanWebViewActivity.this);
//                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                        PersonAdapter adapter = new PersonAdapter(DoubanWebViewActivity.this);
//                        personListView.setLayoutManager(manager);
//                        personListView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });

//        String photosUrl = "https://m.douban.com/rexxar/api/v2/movie/" + id + "/photos";
//
//        final Request request1 = new Request.Builder().url(photosUrl).build();
//        call = mOkHttpClient.newCall(request1);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                DoubanData.photos.clear();
//                try {
//                    JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("photos");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("image");
//                        PhotoUrl photoUrl = new PhotoUrl(
//                                jsonObject.getJSONObject("small").getString("url"),
//                                jsonObject.getJSONObject("normal").getString("url"),
//                                jsonObject.getJSONObject("large").getString("url")
//                                );
//                        DoubanData.photos.add(photoUrl);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.d(TAG, "onResponse: 个数个数" + DoubanData.photos.size());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        LinearLayoutManager manager = new LinearLayoutManager(DoubanWebViewActivity.this);
//                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                        PhotoAdapter adapter = new PhotoAdapter(DoubanWebViewActivity.this);
//                        photoListView.setLayoutManager(manager);
//                        photoListView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                        adapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position) {
//                                if (!TextUtils.isEmpty(DoubanData.photos.get(position).large)) {
//                                    Log.d(TAG, "onItemClick: " + "click!!!");
//                                    new ShowImagesDialog(DoubanWebViewActivity.this, position).show();
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        });


    }

    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Config.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        Config.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }

}
