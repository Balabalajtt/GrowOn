package com.utte.growon.douban;

/**
 * Created by 江婷婷 on 2017/11/12.
 */
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class ShowImagesAdapter extends PagerAdapter {

    private List<View> views;
    private List<String> titles;

    public ShowImagesAdapter(List<View> views, List<String> titles) {
        this.views = views;
        this.titles = titles;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles.get(position);
    }

}
