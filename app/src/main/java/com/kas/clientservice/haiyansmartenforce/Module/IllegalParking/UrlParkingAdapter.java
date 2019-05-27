package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;

import java.util.List;

/**
 * 描述：
 * 时间：2018-04-10
 * 公司：COMS
 */

public class UrlParkingAdapter extends RecyclerView.Adapter<UrlParkingAdapter.ViewHolder>{
    List<TimeImgUrlBean> arr_bitmap;
    Context mContext;
    LayoutInflater inflater;


    public IllegalParkingTakePhotoActivity activity;
    private OnImageAddClickListener onImageAddClickListener;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_ADD = 2;

    public UrlParkingAdapter(List<TimeImgUrlBean> arr_bitmap, Context mContext,IllegalParkingTakePhotoActivity activity) {
        this.arr_bitmap = arr_bitmap;
        this.mContext = mContext;
        this.activity=activity;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NORMAL) {
            view = inflater.inflate(R.layout.item_image,parent,false);

        }else {
            view = inflater.inflate(R.layout.item_image_add_tcsf,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position==0){
            activity.tev_alert.setText("请拍摄正面照片");
        }else if(position==1){
            activity.tev_alert.setText("请拍摄告知书特写");
        }else if(position==2){
            activity.tev_alert.setText("请拍摄驾驶室");
        }else if (position>2){
            activity.tev_alert.setText("您已全部拍摄完毕");
        }



        if (position<=arr_bitmap.size() - 1) {
            final String ImageUrl=RequestUrl.baseUrl_img +arr_bitmap.get(position).getUrl();
            Glide.with(mContext).load(ImageUrl).centerCrop()
                    .error(R.drawable.normal_pic)
                    .into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      Intent it=new Intent(mContext,ImageActivity.class);
                      it.putExtra("url",ImageUrl);
                      mContext.startActivity(it);
                }
            });
            holder.tv_time.setText(arr_bitmap.get(position).getTime());
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 arr_bitmap.remove(position);
                 UrlParkingAdapter.this.notifyDataSetChanged();
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


    public void setOnImageAddClickListener(OnImageAddClickListener onImageAddClickListener) {
        this.onImageAddClickListener = onImageAddClickListener;
    }


  public String getUrl(){
      String retStr="";
     if (arr_bitmap.size()==0)  {
         retStr= "";
     }else{

         for (int i=0;i<arr_bitmap.size();i++){
             retStr+=RequestUrl.baseUrl_img+arr_bitmap.get(i).getUrl()+"|";
         }
         retStr=retStr.substring(0,retStr.length()-1);
     }
     return retStr;
  }

}
