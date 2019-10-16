package com.utte.growon.mono;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.utte.growon.R;
import com.utte.growon.mono.model.Meow;

import java.util.List;

/**
 * GridView的适配器
 */
public class GridViewAdapter extends BaseAdapter {
    private List<Meow.Pic> data;
    private Context context;

    GridViewAdapter(List<Meow.Pic> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.mono_rv_item_3_img, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.iv_pic);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(data.get(position).raw).into(viewHolder.imageView);

        return view;
    }

    private class ViewHolder {
        ImageView imageView;
    }
}