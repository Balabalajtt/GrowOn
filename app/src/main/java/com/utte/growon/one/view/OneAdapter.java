package com.utte.growon.one.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utte.growon.R;
import com.utte.growon.one.entity.OneData;
import com.utte.growon.one.entity.OneHomePageData;

/**
 * Created by 江婷婷 on 2017/12/2.
 */

public class OneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private OneAdapter.OnItemClickListener mOnItemClickListener;

    private final int ITEM_TYPE_ONE = 0;
    private final int ITEM_TYPE_NORMAL = 1;

    private OneHomePageData data;

    public OneHomePageData getData() {
        return data;
    }

    public void setData(OneHomePageData data) {
        this.data = data;
    }

    public OneAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ONE) {
            return new FirstHolder(mLayoutInflater.inflate(R.layout.one_rv_item_1, parent, false));
        } else if (viewType == ITEM_TYPE_NORMAL) {
            return new NormalHolder(mLayoutInflater.inflate(R.layout.one_rv_item_normal, parent, false));
        } else {
            return new ItemHolder(mLayoutInflater.inflate(R.layout.fg_one_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        OneHomePageData.Content_list data = getData().content_list.get(position);
        if (holder instanceof FirstHolder) {
            Glide.with(mContext).load(data.img_url).into(((FirstHolder) holder).ivImage);
            ((FirstHolder) holder).tvTitle.setText(data.title.concat(" | ".concat(data.pic_info)));
            ((FirstHolder) holder).tvForward.setText(data.forward);
            ((FirstHolder) holder).tvInfo.setText(data.words_info);
        } else if (holder instanceof NormalHolder) {
            String category = "";
            switch (data.category) {
                case "1":
                    category = "- ONE STORY -";
                    break;
                case "2":
                    category = "- 问答 -";
                    break;
                case "3":
                    category = "- 连载 -";
                    break;
                case "4":
                    category = "- 音乐 -";
                    break;
                case "5":
                    category = "- 影视 -";
                    break;
            }
            ((NormalHolder) holder).tvCategory.setText(category);
            ((NormalHolder) holder).tvTitle.setText(data.title);
            ((NormalHolder) holder).tvAuthor.setText("文 / ".concat(data.author.user_name));
            Glide.with(mContext).load(data.img_url).into(((NormalHolder) holder).ivImage);
            ((NormalHolder) holder).tvForward.setText(data.forward);
            ((NormalHolder) holder).tvDate.setText(data.post_date.substring(0, 11));
        }
        if(position != 0 && mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position - 1);
                }
            });
        }

//        if (position != 1) {
//            OneHomePageData.Content_list data;
//            data = OneData.mOneHomePageData.content_list.get(position > 1 ? position - 1 : position);
//
//            ((OneAdapter.ItemHolder) holder).title.setText(data.title);
//            String au = "文/" + data.words_info;
//            ((OneAdapter.ItemHolder) holder).author.setText(au);
//            ((OneAdapter.ItemHolder) holder).forward.setText(data.forward);
//            Glide.with(mContext).load(data.img_url).into(((OneAdapter.ItemHolder) holder).image);
//
//            if (mOnItemClickListener != null) {
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int pos = holder.getLayoutPosition();
//                        mOnItemClickListener.onItemClick(holder.itemView, pos);
//                    }
//                });
//            }
//
//        } else {
//            OneHomePageData.Menu data = OneData.mOneHomePageData.menu;
//
//
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_ONE;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {

            return data.content_list.size();
        } else {
            return 0;
        }

    }


    // 第一项的ViewHolder
    public static class FirstHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvTitle;
        TextView tvForward;
        TextView tvInfo;
        public FirstHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvForward = itemView.findViewById(R.id.tv_forward);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }
    }

    // 普通项
    public static class NormalHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivImage;
        TextView tvForward;
        TextView tvDate;
        public NormalHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvForward = itemView.findViewById(R.id.tv_forward);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }


    public static class ItemHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        TextView forward;
        ImageView image;
        public ItemHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            forward = itemView.findViewById(R.id.forward);
            image = itemView.findViewById(R.id.image);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}
