package com.utte.growon;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.utte.growon.douban.DoubanPagerFragment;
import com.utte.growon.kaiyan.CategoryFragment;
import com.utte.growon.mono.DeepFragment;
import com.utte.growon.one.view.OneFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        initViewPage();

    }

    private void initFragment() {
        mFragments.add(new OneFragment());
        mFragments.add(new DeepFragment());
        mFragments.add(new CategoryFragment());
        mFragments.add(DoubanPagerFragment.newInstance(3));

    }

    private void initViewPage() {
        int[] bgs = new int[]{R.drawable.one, R.drawable.mono, R.drawable.kaiyan, R.drawable.douban};
        ViewPager pager = findViewById(R.id.vp);
        FragmentPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(),
                this, mFragments, bgs);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(pager);

        for (int i = 0; i < 4; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
            TextView tv = view.findViewById(R.id.tv);
            tv.setBackgroundResource(bgs[i]);
            tv.setAlpha((float) 0.3);
            if (i == 0) {
                tv.setAlpha((float) 0.9);
            }
            if (tab != null) {
                tab.setCustomView(view);
            }
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv).setAlpha((float) 0.9);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv).setAlpha((float) 0.3);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.first:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .hide(nowFragment)
//                        .show(oneFragment)
//                        .commitNow();
//                nowFragment = oneFragment;
//                bt1.setAlpha((float) 0.9);
//                bt2.setAlpha((float) 0.3);
//                bt3.setAlpha((float) 0.3);
//                break;
//            case R.id.second:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .hide(nowFragment)
//                        .show(mMonoFragment)
//                        .commitNow();
//                nowFragment = mMonoFragment;
//                bt2.setAlpha((float) 0.9);
//                bt1.setAlpha((float) 0.3);
//                bt3.setAlpha((float) 0.3);
//                break;
//            case R.id.third:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .hide(nowFragment)
//                        .show(categoryFragment)
//                        .commitNow();
//                nowFragment = categoryFragment;
//                bt3.setAlpha((float) 0.9);
//                bt2.setAlpha((float) 0.3);
//                bt1.setAlpha((float) 0.3);
//                break;
//        }
//    }
}
