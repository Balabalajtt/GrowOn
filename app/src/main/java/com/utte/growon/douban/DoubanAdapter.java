package com.utte.growon.douban;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utte.growon.R;
import com.utte.growon.douban.model.DoubanData;
import com.utte.growon.douban.model.HotMovie;

import java.util.List;

/**
 * Created by 江婷婷 on 2017/11/29.
 */

public class DoubanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private DoubanAdapter.OnItemClickListener mOnItemClickListener;

    private List<HotMovie> subjects;

    public DoubanAdapter(Context context, List<HotMovie> subjects) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.subjects = subjects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DoubanAdapter.ItemHolder(mLayoutInflater.inflate(R.layout.fg_douban_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((DoubanAdapter.ItemHolder)holder).title.setText(subjects.get(position).title);
        String rr = "豆瓣评分：" + subjects.get(position).rate;
        ((DoubanAdapter.ItemHolder)holder).rate.setText(rr);
        Glide.with(mContext).load(subjects.get(position).cover).into(((DoubanAdapter.ItemHolder) holder).image);
//        Log.d(TAG, "onBindViewHolder: " + );

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
        return subjects.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView rate;
        TextView title;
        public ItemHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            image = itemView.findViewById(R.id.image);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(DoubanAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
