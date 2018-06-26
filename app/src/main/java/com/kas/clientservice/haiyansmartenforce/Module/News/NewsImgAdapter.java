package com.kas.clientservice.haiyansmartenforce.Module.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-06-25
 * 公司：COMS
 */

public class NewsImgAdapter extends BaseAdapter {
    List<String> list;
    Context mContext;

    public NewsImgAdapter(List<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_news_img,null);
            vh.imageView = (ImageView) view.findViewById(R.id.iv_item_newsImg);
            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        Glide.with(mContext).load(list.get(i)).error(R.drawable.normal_pic).into(vh.imageView);

        return view;
    }
    class ViewHolder{
        ImageView imageView;
    }
}
