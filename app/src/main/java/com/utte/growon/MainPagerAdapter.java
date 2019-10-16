package com.utte.growon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.utte.growon.douban.DoubanPagerFragment;

import java.util.List;

/**
 * Created by 江婷婷 on 2017/12/3.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private Context context;
    private int[] imageResId;

    public MainPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragments, int[] imageResId) {
        super(fm);
        mFragments = fragments;
        this.context = context;
        this.imageResId = imageResId;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        Drawable image = context.getResources().getDrawable(imageResId[position]);
//        image.setBounds(0, 0, 40, 40);
//        SpannableString sb = new SpannableString(" ");
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;
//    }

}