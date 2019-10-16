package com.utte.growon.kaiyan;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.utte.growon.R;
import com.utte.growon.kaiyan.category.CategoryData;

import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;

/**
 * Created by 江婷婷 on 2017/11/26.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
//    private MultipleItemAdapter.OnItemClickListener mOnItemClickListener;

    public CategoryAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(mLayoutInflater.inflate(R.layout.fg_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ItemHolder)holder).title.setText(CategoryData.categoryItemList.get(position).title);
        ((ItemHolder)holder).subTitle.setText(CategoryData.categoryItemList.get(position).subTitle);

        /**
         * convenient banner
         */
        ((ItemHolder)holder).convenientBanner.setPages(
                new CBViewHolderCreator<ConvenientBannerHolderView>() {
                    @Override
                    public ConvenientBannerHolderView createHolder() {
                        return new ConvenientBannerHolderView();
                    }
                }, CategoryData.categoryItemList.get(position).itemList)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        VideoInfo videoInfo = new VideoInfo(
                                Uri.parse(CategoryData.categoryItemList.get(position).itemList.get(pos).playUrl))
                                .setTitle(CategoryData.categoryItemList.get(position).itemList.get(pos).title);
                        GiraffePlayer.play(mContext, videoInfo);
                    }
                })
                .setPageIndicator(new int[]{R.drawable.dark,
                R.drawable.light})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

    }

    @Override
    public int getItemCount() {
        return CategoryData.categoryItemList.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subTitle;
        ConvenientBanner convenientBanner;
        public ItemHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subtitle);
            convenientBanner = itemView.findViewById(R.id.convenientBanner);
        }
    }

}
