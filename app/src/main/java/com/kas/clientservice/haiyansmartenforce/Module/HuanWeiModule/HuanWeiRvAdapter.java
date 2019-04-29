package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-04-10
 * 公司：COMS
 */

public class HuanWeiRvAdapter extends RecyclerView.Adapter<HuanWeiRvAdapter.ViewHolder> {
    List<HuanWeiImgBean> dataList;
    Context mContext;
    LayoutInflater inflater;
    private OnImageAddClickListener onImageAddClickListener;
    private OnImagelickListener onImagelickListener;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_ADD = 2;

    public HuanWeiRvAdapter(List<HuanWeiImgBean> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NORMAL) {
            view = inflater.inflate(R.layout.item_image, parent, false);

        } else {
            view = inflater.inflate(R.layout.item_image_add_tcsf, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (position <= dataList.size() - 1) {
            HuanWeiImgBean huanWeiImgBean=dataList.get(position);
            holder.imageView.setImageBitmap(huanWeiImgBean.getImageBmp());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onImagelickListener.onImageClick(position);
                }
            });
            holder.tv_time.setText(huanWeiImgBean.getTime());
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onImgDeleteClickListener != null) {
                        onImgDeleteClickListener.onImgDeleteClick(position);
                    }
                }
            });
        } else {
            holder.iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onImageAddClickListener.onImageAddClick();

                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.size() == 0) {
            return TYPE_ADD;
        } else {
            if (position == dataList.size()) {
                return TYPE_ADD;
            } else {
                return TYPE_NORMAL;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, iv_add, iv_delete;
        TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_image);
            iv_add = (ImageView) itemView.findViewById(R.id.iv_item_add);
            tv_time = (TextView) itemView.findViewById(R.id.iv_item_time);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_item_delete);
        }
    }


    public interface OnImageAddClickListener {
        void onImageAddClick();
    }

    public interface OnImagelickListener {
        void onImageClick(int p);
    }

    public void setOnImageAddClickListener(OnImageAddClickListener onImageAddClickListener) {
        this.onImageAddClickListener = onImageAddClickListener;
    }

    public void setOnImagelickListener(OnImagelickListener onImagelickListener) {
        this.onImagelickListener = onImagelickListener;
    }


    public OnImgDeleteClickListener onImgDeleteClickListener;

    public interface OnImgDeleteClickListener {
        void onImgDeleteClick(int position);
    }

    public void setOnImgDeleteClickListener(OnImgDeleteClickListener onImgDeleteClickListener) {
        this.onImgDeleteClickListener = onImgDeleteClickListener;
    }

    public String getBase64Str() {
        if (dataList.size()==0){
            return "";
        }else{
            StringBuffer buffer=new StringBuffer();
            for (int i=0;i<dataList.size();i++){

                buffer.append(dataList.get(i).getBase64Bmp());
                if (i<dataList.size()-1){
                    buffer.append(",");
                }
            }
            return buffer.toString();
        }

    }

}
