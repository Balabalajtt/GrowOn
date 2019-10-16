package com.utte.growon.mono;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.utte.growon.R;

import java.util.List;

public class NinthActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth);

        String[] urls = getIntent().getStringArrayExtra("urls");
        mListView = findViewById(R.id.lv);
        final ImageListAdapter adapter = new ImageListAdapter(this, 0, urls);
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //停止滚动
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //设置为停止滚动
                        adapter.setScrollState(false);
                        int count = view.getChildCount();
                        for (int i = 0; i < count; i++) {
                            //获取到item的图片显示的Imageview控件
                            View itemView = view.getChildAt(i);
                            adapter.loadChild(itemView);

                        }
                        break;
                    //滚动做出了抛的动作
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        //设置为正在滚动
                        adapter.setScrollState(true);
                        break;
                    //正在滚动
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        //设置为正在滚动
                        adapter.setScrollState(true);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }
}
