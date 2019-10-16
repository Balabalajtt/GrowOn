package com.utte.growon.kaiyan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.utte.growon.R;
import com.utte.growon.kaiyan.category.CategoryVideoItem;


/**
 * Created by 江婷婷 on 2017/11/26.
 */

public class ConvenientBannerHolderView implements Holder<CategoryVideoItem> {
    private ImageView imgFeed;
    private TextView tvTitle;
    private TextView tvDescription;

    private LayoutInflater mLayoutInflater;

    public ConvenientBannerHolderView() {
    }

    @Override
    public View createView(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.convenient_banner_item, null, false);
        imgFeed = view.findViewById(R.id.feed);
        tvTitle = view.findViewById(R.id.title);
        tvDescription = view.findViewById(R.id.description);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, CategoryVideoItem data) {
        Glide.with(context).load(data.feed).into(imgFeed);
        tvTitle.setText(data.title);
        tvDescription.setText(data.description);
    }


}