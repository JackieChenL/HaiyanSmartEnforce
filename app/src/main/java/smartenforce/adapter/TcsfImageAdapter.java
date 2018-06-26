package smartenforce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.kas.clientservice.haiyansmartenforce.R;
import smartenforce.bean.tcsf.PicBean;

import java.util.List;


public class TcsfImageAdapter extends RecyclerView.Adapter<TcsfImageAdapter.ViewHolder>{
    List<PicBean> arr_bitmap;
    Context mContext;
    LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_ADD = 2;

    public TcsfImageAdapter(List<PicBean> arr_bitmap, Context mContext) {
        this.arr_bitmap = arr_bitmap;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NORMAL) {
            view = inflater.inflate(R.layout.image_item_tcsf,parent,false);
        }else {
            view = inflater.inflate(R.layout.item_image_add_tcsf,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position<=arr_bitmap.size() - 1) {
            holder.imageView.setImageBitmap(arr_bitmap.get(position).getBmp());
            holder.imv_del.setVisibility(arr_bitmap.get(position).isPicFromSm()?View.GONE:View.VISIBLE);
            holder.imv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null)
                        onItemClickListener.onDelImageClick(position);
                }
            });
//            holder.tv_time.setText(DateUtil.currentTime());
        }else {
            holder.iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null)
                        onItemClickListener.onImageAddClick();
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
        ImageView imageView,iv_add,imv_del;
//        TextView tv_time;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_image);
            iv_add = (ImageView) itemView.findViewById(R.id.iv_item_add);
            imv_del = (ImageView) itemView.findViewById(R.id.imv_del);
//            tv_time = (TextView) itemView.findViewById(R.id.iv_item_time);
        }
    }

    public interface OnItemClickListener{
        void onImageAddClick();
//        void onImageClick(int p);
        void onDelImageClick(int p);

    }



    

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
