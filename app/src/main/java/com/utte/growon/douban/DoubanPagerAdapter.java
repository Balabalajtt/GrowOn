package com.utte.growon.douban;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.utte.growon.PageFragment;
import com.utte.growon.douban.frags.GaofenFragment;
import com.utte.growon.douban.frags.JinDianFragment;
import com.utte.growon.douban.frags.LengmenFragment;
import com.utte.growon.douban.frags.RemenFragment;
import com.utte.growon.douban.frags.ZuixinFragment;
import com.utte.growon.douban.model.DoubanData;

/**
 * Created by 江婷婷 on 2017/12/3.
 */

public class DoubanPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;
    private Context context;
    private String[] tabTitles = null;

    public DoubanPagerAdapter(FragmentManager fm, Context context, String[] tabTitles) {
        super(fm);
        this.context = context;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
//        return DoubanFragment.getDoubanFragment(tabTitles[position]);
        switch (position) {
            case 0:
                return new RemenFragment();
            case 1:
                return new ZuixinFragment();
            case 2:
                return new JinDianFragment();
            case 3:
                return new GaofenFragment();
            case 4:
                return new LengmenFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];

    }
}