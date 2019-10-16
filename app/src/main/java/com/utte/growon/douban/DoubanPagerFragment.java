package com.utte.growon.douban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utte.growon.R;

/**
 * Created by 江婷婷 on 2017/12/3.
 */

public class DoubanPagerFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private DoubanPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String[] tabTitles = new String[]{"热门","最新","经典", "高分", "冷门"};

    public static DoubanPagerFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DoubanPagerFragment doubanPagerFragment = new DoubanPagerFragment();
        doubanPagerFragment.setArguments(args);
        return doubanPagerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // ViewPager + TabLayout
        pagerAdapter = new DoubanPagerAdapter(getActivity().getSupportFragmentManager(), getContext(), tabTitles);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

//        TextView textView = (TextView) view;
//        textView.setText("Fragment #");
        return view;
    }

}
