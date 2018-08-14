package com.kas.clientservice.haiyansmartenforce.Module.verification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

import smartenforce.aty.ViewPhotoActivity;
import smartenforce.bean.tcsf.PatrolDetailBean;


public class VerifyDetailAdapter extends RecyclerView.Adapter<VerifyDetailAdapter.ViewHolder> {
    private List<String> arr_bitmap;
    private Context mContext;
    private LayoutInflater inflater;

    public VerifyDetailAdapter(List<String> arr_bitmap, Context mContext) {
        this.arr_bitmap = arr_bitmap;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pic_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(arr_bitmap.get(position))
                .error(R.drawable.normal_pic)
                .placeholder(R.drawable.normal_pic)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,ViewPhotoActivity.class).putExtra("Url", arr_bitmap.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr_bitmap==null?0:arr_bitmap.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_image);
        }
    }


}
