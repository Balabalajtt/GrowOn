package com.utte.growon.kaiyan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utte.growon.R;
import com.utte.growon.douban.MessageEvent;
import com.utte.growon.douban.NetWork;
import com.utte.growon.kaiyan.category.CategoryData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 江婷婷 on 2017/11/26.
 */

public class CategoryFragment extends Fragment {
    RecyclerView mRecyclerView;
    CategoryAdapter mCategoryAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public CategoryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_category, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CategoryData.categoryItemList.clear();
                NetWork.requestCategoryData("http://baobab.kaiyanapp.com/api/v4/discovery/category?start=0&num=18");
            }
        });

        mSwipeRefreshLayout.setRefreshing(true);
//        NetWork.requestHomeData("http://baobab.kaiyanapp.com/api/v4/tabs/selected?date=" + NextPageUrl.getDate() + "&num=1&page=1");

        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mCategoryAdapter = new CategoryAdapter(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(true);
        NetWork.requestCategoryData("http://baobab.kaiyanapp.com/api/v4/discovery/category?start=0&num=18");

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMain(MessageEvent messageEvent) {
                switch (messageEvent.getEventCode()) {
                    case 3:
                /**
                 * 显示CategoryFragment
                 */

                mCategoryAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                break;
        }
    }
}
