package com.utte.growon.douban;

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

import com.utte.growon.R;
import com.utte.growon.douban.model.DoubanData;
import com.utte.growon.douban.model.HotMovie;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/11/29.
 */

public class DoubanFragment extends Fragment {
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    DoubanAdapter adapter;
    int start = 0;
    static String tag = "";//热门 最新 经典 豆瓣高分 冷片佳片
//    String url = "";
    static String baseUrl = "";
    static List<HotMovie> subjects = new ArrayList<>();

    public DoubanFragment() {

    }

    public static DoubanFragment getDoubanFragment(String tag) {
        DoubanFragment doubanFragment = new DoubanFragment();
        doubanFragment.tag = tag;
        baseUrl = "https://movie.douban.com/j/search_subjects?type=movie&tag=" + tag + "&sort=recommend&page_limit=20&page_start=";
        switch (tag) {
            case "热门":
                subjects = DoubanData.subjects1;
                break;
            case "最新":
                subjects = DoubanData.subjects2;
                break;
            case "经典":
                subjects = DoubanData.subjects3;
                break;
            case "豆瓣高分":
                subjects = DoubanData.subjects4;
                break;
            case "冷片佳片":
                subjects = DoubanData.subjects5;
                break;
        }

        return doubanFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        subjects.clear();
        NetWork.requestDouban(baseUrl + start, tag);
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
                subjects.clear();
                start = 0;
                NetWork.requestDouban(baseUrl + start, tag);
            }
        });




        mRecyclerView = view.findViewById(R.id.recycler_view);
        adapter = new DoubanAdapter(getContext(), subjects);
//        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DoubanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DoubanWebViewActivity.class);
                intent.putExtra("url", subjects.get(position).url);
                intent.putExtra("id", subjects.get(position).id);
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
                            NetWork.requestDouban(baseUrl + start, tag);
                        } else {
                            Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMain(MessageEvent messageEvent) {
        switch (messageEvent.getEventCode()) {
            case 5:
                adapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                break;
        }
    }
}
