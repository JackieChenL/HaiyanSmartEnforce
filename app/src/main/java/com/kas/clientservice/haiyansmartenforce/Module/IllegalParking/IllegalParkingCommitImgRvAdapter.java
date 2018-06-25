package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;

import java.util.List;

/**
 * 描述：
 * 时间：2018-04-10
 * 公司：COMS
 */

public class IllegalParkingCommitImgRvAdapter extends RecyclerView.Adapter<IllegalParkingCommitImgRvAdapter.ViewHolder>{
    List<Bitmap> arr_bitmap;
    Context mContext;
    LayoutInflater inflater;
    private OnImageAddClickListener onImageAddClickListener;
    private OnImagelickListener onImagelickListener;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_ADD = 2;

    public IllegalParkingCommitImgRvAdapter(List<Bitmap> arr_bitmap, Context mContext) {
        this.arr_bitmap = arr_bitmap;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NORMAL) {
            view = inflater.inflate(R.layout.item_image,parent,false);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onImageLongClickListener!=null) {
                        onImageLongClickListener.onImgLongClick();
                    }
                    return true;
                }
            });
        }else {
            view = inflater.inflate(R.layout.item_image_add,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position<=arr_bitmap.size() - 1) {
            holder.imageView.setImageBitmap(arr_bitmap.get(position));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onImagelickListener.onImageClick(position);
                }
            });
            holder.tv_time.setText(TimeUtils.getFormedTime("MM-dd HH:mm:ss"));
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onImgDeleteClickListener!=null) {
                        onImgDeleteClickListener.onImgDeleteClick(position);
                    }
                }
            });
        }else {
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
        return arr_bitmap.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (arr_bitmap.size() == 0) {
            return TYPE_ADD;
        }else {
            if (position == arr_bitmap.size()) {
                return  TYPE_ADD;
            }else {
                return  TYPE_NORMAL;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView,iv_add,iv_delete;
        TextView tv_time;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_image);
            iv_add = (ImageView) itemView.findViewById(R.id.iv_item_add);
            tv_time = (TextView) itemView.findViewById(R.id.iv_item_time);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_item_delete);
        }
    }
    
    
    public interface OnImageAddClickListener{
        void onImageAddClick();
    }
    public interface OnImagelickListener{
        void onImageClick(int p);
    }

    public void setOnImageAddClickListener(OnImageAddClickListener onImageAddClickListener) {
        this.onImageAddClickListener = onImageAddClickListener;
    }

    public void setOnImagelickListener(OnImagelickListener onImagelickListener) {
        this.onImagelickListener = onImagelickListener;
    }

    public OnImageLongClickListener onImageLongClickListener;
    public interface OnImageLongClickListener{
        void onImgLongClick();
    }

    public void setOnImageLongClickListener(OnImageLongClickListener onImageLongClickListener) {
        this.onImageLongClickListener = onImageLongClickListener;
    }
    public OnImgDeleteClickListener onImgDeleteClickListener;
    public interface OnImgDeleteClickListener{
        void onImgDeleteClick(int position);
    }
    public void setOnImgDeleteClickListener(OnImgDeleteClickListener onImgDeleteClickListener) {
        this.onImgDeleteClickListener = onImgDeleteClickListener;
    }
}
