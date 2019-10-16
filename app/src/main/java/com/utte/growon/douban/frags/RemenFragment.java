package com.utte.growon.douban.frags;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utte.growon.R;
import com.utte.growon.douban.DoubanAdapter;
import com.utte.growon.douban.DoubanWebViewActivity;
import com.utte.growon.douban.model.DoubanData;
import com.utte.growon.douban.model.HotMovie;

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
 * Created by 江婷婷 on 2017/12/10.
 */

public class RemenFragment extends Fragment{
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    DoubanAdapter adapter;
    int start = 0;
    static String tag = "热门";
    static String baseUrl = "https://movie.douban.com/j/search_subjects?type=movie&tag=" + tag + "&sort=recommend&page_limit=20&page_start=";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DoubanData.subjects1.clear();
//        NetWork.requestDouban(baseUrl + start, tag);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_category, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DoubanData.subjects1.clear();
                start = 0;
//                NetWork.requestDouban(baseUrl + start, tag);
//                requestDouban();
            }
        });


        mRecyclerView = view.findViewById(R.id.recycler_view);
        adapter = new DoubanAdapter(getContext(), DoubanData.subjects1);
//        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DoubanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DoubanWebViewActivity.class);
                intent.putExtra("url", DoubanData.subjects1.get(position).url);
                intent.putExtra("id", DoubanData.subjects1.get(position).id);
                startActivity(intent);
            }
        });

        adapter.notifyDataSetChanged();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount() - 1){
                        if (start < 100) {
                            start = start + 20;
                            requestDouban();
                        } else {
                            Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        requestDouban();
        return view;
    }

    public void requestDouban() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String url = baseUrl + start;
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("subjects");
                    DoubanData.subjectsData = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<HotMovie>>(){}.getType());
                    for (HotMovie h : DoubanData.subjectsData) {
                        DoubanData.subjects1.add(h);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

}
