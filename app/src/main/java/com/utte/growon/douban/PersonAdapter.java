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

/**
 * Created by 江婷婷 on 2017/11/30.
 */

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private PersonAdapter.OnItemClickListener mOnItemClickListener;

    public PersonAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonAdapter.ItemHolder(mLayoutInflater.inflate(R.layout.douban_person_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ((PersonAdapter.ItemHolder)holder).name.setText(DoubanData.personList.get(position).name);
        ((PersonAdapter.ItemHolder)holder).role.setText(DoubanData.personList.get(position).role);
        Glide.with(mContext).load(DoubanData.personList.get(position).imageUrl).into(((PersonAdapter.ItemHolder) holder).image);

    }

    @Override
    public int getItemCount() {
        return DoubanData.personList.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView role;
        public ItemHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            role = itemView.findViewById(R.id.role);
            image = itemView.findViewById(R.id.image);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(PersonAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}

