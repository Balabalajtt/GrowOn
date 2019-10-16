package com.utte.growon.one.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.utte.growon.R;
import com.utte.growon.one.entity.OneData;
import com.utte.growon.one.entity.OneHomePageData;
import com.utte.growon.one.net.OneNetWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 江婷婷 on 2017/12/1.
 */

public class OneFragment extends Fragment {

    private static final String TAG = "WenFragment";

    private Handler mHandler = new OneHandler();
    private RecyclerView mRecyclerView;
    private OneAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public OneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_category, container, false);
        initView(view);
        requestHomePageData(OneData.getDate());
        return view;
    }

    private void initView(View view) {
        // 刷新
        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(() -> requestHomePageData(OneData.getDate()));

        mRecyclerView = view.findViewById(R.id.recycler_view);
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new OneAdapter(getContext());
        mAdapter.setOnItemClickListener((view1, position) -> {
            Intent intent = new Intent(getContext(), OneContentActivity.class);
            intent.putExtra("id", mAdapter.getData().content_list.get(position).content_id);
            startActivity(intent);
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 加载下一批数据
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState == RecyclerView.SCROLL_STATE_IDLE){
//                    int lastVisiblePosition = mLinearLayoutManager.findLastVisibleItemPosition();
//                    if(lastVisiblePosition >= mLinearLayoutManager.getItemCount() - 1){
//                        OneData.last();
//                        requestHomePageData(OneData.getDate());
//                    }
//                }
            }
        });
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 请求ONE首页几条内容
     *
     * @param date 日期，去拼接url
     */
    private void requestHomePageData(String date) {
        mSwipeRefreshLayout.setRefreshing(true);
        OneNetWork.getOneData(date, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // TODO: 2018/10/27 请求One首页数据失败
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ResponseBody body = response.body();
                if (body == null) {
                    onFailure(call, new IOException());
                    return;
                }

                mAdapter.setData(convertToViewData(body, OneHomePageData.class));
                Message m = Message.obtain(mHandler, () -> {
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                });
                mHandler.sendMessage(m);
            }
        });

    }

    private <T> T convertToViewData(ResponseBody body, Class<T> tClass) {
        T data = null;
        try {
            JSONObject json = new JSONObject(body.string()).getJSONObject("data");
            data = new Gson().fromJson(String.valueOf(json), tClass);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}

