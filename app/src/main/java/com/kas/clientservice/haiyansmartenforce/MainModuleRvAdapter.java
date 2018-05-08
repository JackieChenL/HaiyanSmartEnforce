package com.kas.clientservice.haiyansmartenforce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.User.UserInfo;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-07
 * 公司：COMS
 */

public class MainModuleRvAdapter extends RecyclerView.Adapter<MainModuleRvAdapter.ViewHolder>{

    Context mContext;
    List<UserInfo.BoardBean> list;
    LayoutInflater inflater;

    public MainModuleRvAdapter(List<UserInfo.BoardBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_main_module,parent,false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext,120)));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext).load(list.get(position).getUrl()).placeholder(R.drawable.normal_pic).error(R.drawable.normal_pic).into(holder.iv);
        holder.tv.setText(list.get(position).getTitle());

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onModuleClickListener.onModuleClick(list.get(position).getTypeid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        LinearLayout ll;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_item_mainModule);
            tv = (TextView) itemView.findViewById(R.id.tv_item_mianModule);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_item_mainmodule);
        }
    }
    public OnModuleClickListener onModuleClickListener;
    public interface OnModuleClickListener{
        public void onModuleClick(int type);
    }

    public void setOnModuleClickListener(OnModuleClickListener onModuleClickListener) {
        this.onModuleClickListener = onModuleClickListener;
    }
}
