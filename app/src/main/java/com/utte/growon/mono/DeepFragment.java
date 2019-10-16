package com.utte.growon.mono;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utte.growon.R;

import java.util.ArrayList;
import java.util.List;

public class DeepFragment extends Fragment {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deep_frag, container, false);

        initFragment();
        initPager(view);

        return view;

    }


    private void initFragment() {
        mTitles.clear();
        mFragments.clear();
        for (int i = 0; i < 3; i++) {
            MonoFragment fg = new MonoFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            fg.setArguments(bundle);
            mFragments.add(fg);

        }
        mTitles.add("茶");
        mTitles.add("音乐");
        mTitles.add("画册");
    }


    private void initPager(View view) {
        ViewPager pager = view.findViewById(R.id.vp);
        pager.setAdapter(new TabFgAdapter(getChildFragmentManager(), mFragments, mTitles));
        pager.setCurrentItem(0);

        TabLayout tab = view.findViewById(R.id.tab);
        tab.setupWithViewPager(pager);
    }



}
