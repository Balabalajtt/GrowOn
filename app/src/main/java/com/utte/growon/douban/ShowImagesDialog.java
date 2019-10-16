package com.utte.growon.douban;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.utte.growon.R;
import com.utte.growon.douban.model.DoubanData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/11/12.
 */

public class ShowImagesDialog extends Dialog {
    private View mView ;
    private Context mContext;
    private ShowImagesViewPager mViewPager;
    private TextView mIndexText;
    private List<String> mTitles;
    private List<View> mViews;
    private ShowImagesAdapter mAdapter;

    private int position;

    public ShowImagesDialog(@NonNull Context context, int position) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.position = position;
        initView();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.height = Config.EXACT_SCREEN_HEIGHT;
        wl.width = Config.EXACT_SCREEN_WIDTH;
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }


    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_images_brower, null);
        mViewPager = mView.findViewById(R.id.vp_images);
        mIndexText = mView.findViewById(R.id.tv_image_index);
        mTitles = new ArrayList<>();
        mViews = new ArrayList<>();
    }


    private void initData() {
        OnPhotoTapListener listener = new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                dismiss();
            }
        };

        for (int i = 0; i < DoubanData.photos.size(); i++) {
            final PhotoView photoView = new PhotoView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200, 200);
            photoView.setLayoutParams(layoutParams);
            photoView.setOnPhotoTapListener(listener);
//            Log.d("ShowImagesDialog", "initData: " + DoubanData.photos.get(i).small);
            Glide.with(mContext)
                    .load(DoubanData.photos.get(i).large)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            photoView.setImageDrawable(resource);
                        }
                    });
            mViews.add(photoView);
            mTitles.add("剧照" + i + "");
        }

        mAdapter = new ShowImagesAdapter(mViews, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
        String indexText = (position + 1) + "/" + DoubanData.photos.size();
        mIndexText.setText(indexText);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                String text = position + 1 + "/" + DoubanData.photos.size();
                mIndexText.setText(text);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
