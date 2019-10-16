package com.utte.growon.douban;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.utte.growon.R;
import com.utte.growon.douban.model.DoubanData;

/**
 * Created by 江婷婷 on 2017/11/30.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private PhotoAdapter.OnItemClickListener mOnItemClickListener;

    public PhotoAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoAdapter.ItemHolder(mLayoutInflater.inflate(R.layout.douban_photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Glide.with(mContext).load(DoubanData.photos.get(position).normal).into(((PhotoAdapter.ItemHolder) holder).image);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return DoubanData.photos.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ItemHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(PhotoAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}

