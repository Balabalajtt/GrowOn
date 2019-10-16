package com.utte.growon.mono;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utte.growon.R;
import com.utte.growon.mono.model.Meow;
import com.utte.growon.mono.model.Morning_tea;
import com.utte.growon.mono.model.Music;

import java.util.List;

/**
 * Created by 江婷婷 on 2017/12/2.
 */

public class MonoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private MonoAdapter.OnItemClickListener mOnItemClickListener;
    private MonoData mMonoData;
    private Music mMusicData;
    private int type = 0;

    public void setMonoData(MonoData monoData) {
        mMonoData = monoData;
        type = 0;
    }

    public void setMonoMusicData(Music data) {
        mMusicData = data;
        type = 1;
    }
    public void addMonoMusicData(Music data) {
        if (mMusicData != null) {
            mMusicData.entity_list.addAll(data.entity_list);
            mMusicData.start = data.start;
            mMusicData.is_last_page = data.is_last_page;
            type = 1;
        }
    }

    private final int ITEM_TYPE_ONE = 0;
    private final int ITEM_TYPE_NORMAL = 1;
    private static final String TAG = "MonoAdapter";

    public MonoAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                // 日签
                return new FirstHolder(mLayoutInflater.inflate(R.layout.mono_rv_item_1, parent, false));
            case 2:
                // 一张大图
                return new SecondHolder(mLayoutInflater.inflate(R.layout.mono_rv_item_2, parent, false));
            case 3:
                // 多图 嵌套GridView
                return new ThirdHolder(mLayoutInflater.inflate(R.layout.mono_rv_item_3, parent, false));
            case 4:
                return new FirstHolder(mLayoutInflater.inflate(R.layout.one_rv_item_1, parent, false));
            case 5:
                // 普通文章
                return new FifthHolder(mLayoutInflater.inflate(R.layout.mono_rv_item_5, parent, false));
            case 6:
                return new FirstHolder(mLayoutInflater.inflate(R.layout.one_rv_item_1, parent, false));
            case 7:
                // 视频 显示一个gif预览
                return new SeventhHolder(mLayoutInflater.inflate(R.layout.mono_rv_item_7, parent, false));
            case 8:
                // 音乐
                return new EighthHolder(mLayoutInflater.inflate(R.layout.mono_rv_item_8, parent, false));
            case 9:
                // 图集
                return new NinthHolder(mLayoutInflater.inflate(R.layout.mono_rv_item_9, parent, false));
            default:
                return new FirstHolder(mLayoutInflater.inflate(R.layout.one_rv_item_1, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final Meow data;
        int morningCount = 0;
        if (type == 0) {
            morningCount = mMonoData.mMorningTea.entity_list.size();
            if (position < morningCount) {
                data = mMonoData.mMorningTea.entity_list.get(position).meow;
            } else {
                data = mMonoData.mAfternoonTea.entity_list.get(position - morningCount).meow;
            }
        } else {
            data = mMusicData.entity_list.get(position).meow;
        }
        if (holder instanceof FirstHolder) {
            String date;
            String info;
            Morning_tea tea;
            if (position < morningCount) {
                tea = mMonoData.mMorningTea;
                date = "早茶 ".concat(mMonoData.mMorningTea.release_date);
            } else {
                tea = mMonoData.mAfternoonTea;
                date = "下午茶 ".concat(tea.release_date);
            }
            info = "星期六 / ".concat(String.valueOf(tea.entity_list.size()))
                    .concat("篇内容，").concat(tea.read_time);
            ((FirstHolder) holder).tvDate.setText(date);
            ((FirstHolder) holder).tvInfo.setText(info);
            Glide.with(mContext).load(data.thumb.raw).into(((FirstHolder) holder).ivRaw);

        } else if (holder instanceof SecondHolder) {
            Glide.with(mContext).load(data.thumb.raw).into(((SecondHolder) holder).ivRaw);
            ((SecondHolder) holder).tvText.setText(data.text);

        } else if (holder instanceof ThirdHolder) {
            ((ThirdHolder) holder).tvText.setText(data.text);
            ((ThirdHolder) holder).tvTitle.setText(data.title);
            GridViewAdapter adapter = new GridViewAdapter(data.pics, mContext);
            ((ThirdHolder) holder).gvPics.setAdapter(adapter);

        } else if (holder instanceof FifthHolder) {
            Glide.with(mContext).load(data.thumb.raw).into(((FifthHolder) holder).ivRaw);
            ((FifthHolder) holder).tvText.setText(data.text);
            ((FifthHolder) holder).tvTitle.setText(data.title);
            ((FifthHolder) holder).tvDesc.setText(data.description);

        } else if (holder instanceof SeventhHolder) {
            Glide.with(mContext).load(data.thumb.raw).asGif().into(((SeventhHolder) holder).ivRaw);
            ((SeventhHolder) holder).tvTitle.setText(data.title);
            ((SeventhHolder) holder).tvDesc.setText(data.description);

        } else if (holder instanceof EighthHolder) {


            Glide.with(mContext).load(data.thumb.raw).into(((EighthHolder) holder).ivRaw);
            ((EighthHolder) holder).tvTitle.setText(data.title);
            ((EighthHolder) holder).tvDesc.setText(data.description);

        } else if (holder instanceof NinthHolder) {
            ((NinthHolder) holder).tvTitle.setText(data.title);
            ((NinthHolder) holder).tvDesc.setText(data.description);
            Glide.with(mContext).load(data.images.get(0).raw).into(((NinthHolder) holder).ivImg);
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, data);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1) {
            return mMusicData.entity_list.get(position).meow.meow_type;
        }
        if (mMonoData.mAfternoonTea == null) {
            return mMonoData.mMorningTea.entity_list.get(position).meow.meow_type;
        } else {
            int morningCount = mMonoData.mMorningTea.entity_list.size();
            if (position < morningCount) {
                return mMonoData.mMorningTea.entity_list.get(position).meow.meow_type;
            } else {
                return mMonoData.mAfternoonTea.entity_list.get(position - morningCount).meow.meow_type;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            if (mMusicData == null) {
                return 0;
            } else {
                return mMusicData.entity_list.size();
            }
        }
        if (mMonoData == null ) {
            return 0;
        }
        if ( mMonoData.mMorningTea == null) {
            return 0;
        } else if (mMonoData.mAfternoonTea == null) {
            return mMonoData.mMorningTea.entity_list.size();
        } else {
            return mMonoData.mMorningTea.entity_list.size() + mMonoData.mAfternoonTea.entity_list.size();
        }

    }

    // 一张大图
    public static class SecondHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        ImageView ivRaw;

        public SecondHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
            ivRaw = itemView.findViewById(R.id.iv_raw);
        }
    }

    // 日签ViewHolder
    public static class FirstHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvInfo;
        ImageView ivRaw;

        public FirstHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvInfo = itemView.findViewById(R.id.tv_info);
            ivRaw = itemView.findViewById(R.id.iv_raw);
        }
    }

    // 普通文章ViewHolder
    public static class FifthHolder extends RecyclerView.ViewHolder {
        ImageView ivRaw;
        TextView tvText;
        TextView tvTitle;
        TextView tvDesc;

        public FifthHolder(View itemView) {
            super(itemView);
            ivRaw = itemView.findViewById(R.id.iv_raw);
            tvText = itemView.findViewById(R.id.tv_text);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }

    public static class SeventhHolder extends RecyclerView.ViewHolder {
        ImageView ivRaw;
        TextView tvTitle;
        TextView tvDesc;

        public SeventhHolder(View itemView) {
            super(itemView);
            ivRaw = itemView.findViewById(R.id.iv_raw);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }

    public static class EighthHolder extends RecyclerView.ViewHolder {
        ImageView ivRaw;
        TextView tvTitle;
        TextView tvDesc;

        public EighthHolder(View itemView) {
            super(itemView);
            ivRaw = itemView.findViewById(R.id.iv_raw);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }


    public static class ThirdHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvText;
        MonoGridView gvPics;

        public ThirdHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
            tvTitle = itemView.findViewById(R.id.tv_title);
            gvPics = itemView.findViewById(R.id.gv_pics);
        }
    }

    public static class NinthHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvCount;
        TextView tvTitle;
        TextView tvDesc;

        public NinthHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Meow meow);
    }

    public void setOnItemClickListener(final Context context) {
        this.mOnItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Meow meow) {
                Intent intent;
                if (meow.meow_type == 9) {
                    String[] urls = new String[meow.images.size()];
                    for (int i = 0; i < meow.images.size(); i++) {
                        urls[i] = meow.images.get(i).raw;
                    }
                    intent = new Intent(context, NinthActivity.class);
                    intent.putExtra("urls", urls);
                } else {
                    intent = new Intent(context, MonoActivity.class);
                    intent.putExtra("id", meow.id);
                    intent.putExtra("url", meow.rec_url);
                }
                context.startActivity(intent);
            }
        };
    }




}
