package com.kas.clientservice.haiyansmartenforce.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-08
 * 公司：COMS
 */

public class ImageListRvAdapter extends RecyclerView.Adapter<ImageListRvAdapter.ViewHolder>{
    List<String> list;
    Context mContext;
    LayoutInflater inflater;

    public ImageListRvAdapter(List<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_img,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onImagelickListener!=null) {
                    onImagelickListener.onImageClick(position);
                }
            }
        });
        Glide.with(mContext).load(list.get(position)).placeholder(R.drawable.normal_pic).error(R.drawable.normal_pic).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_img);
        }
    }

    OnImagelickListener onImagelickListener;
    public interface OnImagelickListener{
        void onImageClick(int p);
    }

    public void setOnImagelickListener(OnImagelickListener onImagelickListener) {
        this.onImagelickListener = onImagelickListener;
    }
}
