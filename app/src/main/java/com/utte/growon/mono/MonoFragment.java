package com.utte.growon.mono;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utte.growon.R;
import com.utte.growon.mono.model.Music;
import com.utte.growon.mono.music.MonoMusicNet;
import com.utte.growon.one.entity.OneData;

/**
 * Created by 江婷婷 on 2017/12/1.
 */

public class MonoFragment extends Fragment implements MonoIn {

    private static final String TAG = "WenFragment";

    private MonoHandler mHandler = new MonoHandler(this);
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MonoAdapter mAdapter;
    private int type;

    private String next = "";
    private boolean isRefresh = true;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        type = getArguments().getInt("type");

        View view = inflater.inflate(R.layout.fg_category, container, false);
        initView(view);


        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        getMonoData("");

    }

    private void initView(View view) {
        // swipe
        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                getMonoData(OneData.getDate());
            }
        });

        // rv
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new MonoAdapter(getContext());
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(getContext());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= mLayoutManager.getItemCount() - 1){
                        getMonoData(OneData.getDate());
                    }
                }
            }
        });
    }


    private void getMonoData(String date) {
        if (type == 0) {
            date = "2018-10-27";
            MonoNetWork.getMonoData(date, this);
        } else if (type == 1) {
            int from = 0, to = 0;
            if (!TextUtils.isEmpty(next)) {
                from = Integer.parseInt(next.substring(0, next.indexOf(",")));
                to = Integer.parseInt(next.substring(next.indexOf(",") + 1, next.length()));
            }
            MonoMusicNet.getMonoMusicData(from, to, this);
        } else {
            int from = 0, to = 0;
            if (!TextUtils.isEmpty(next)) {
                from = Integer.parseInt(next.substring(0, next.indexOf(",")));
                to = Integer.parseInt(next.substring(next.indexOf(",") + 1, next.length()));
            }
            MonoMusicNet.getMonoPicData(from, to, this);
        }


    }

    public void refresh(MonoData data) {
        mAdapter.setMonoData(data);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void refreshMusic(Music data) {
        Log.d(TAG, "refreshMusic: " + data.entity_list.size() + "   " + type);
        if (data.entity_list.size() == 0) {
            getMonoData("");
            return;
        }
        if (isRefresh) {
            mAdapter.setMonoMusicData(data);
            isRefresh = false;
        } else {
            mAdapter.addMonoMusicData(data);
        }
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSuccess(MonoData data) {
        Message message = Message.obtain();
        message.obj = data;
        message.what = MonoHandler.REFRESH_DATA;
        mHandler.sendMessage(message);
    }

    @Override
    public void onMusicSuccess(Music data) {
        next = data.start;
        Message message = Message.obtain();
        message.obj = data;
        message.what = MonoHandler.REFRESH_MUSIC_DATA;
        mHandler.sendMessage(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: " + type);
    }
}

