package smartenforce.aty.patrol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;
import smartenforce.bean.tcsf.PatrolDetailBean;

import java.util.List;


public class ShowImageAdapter extends RecyclerView.Adapter<ShowImageAdapter.ViewHolder> {
    private List<PatrolDetailBean.ImglistBean> arr_bitmap;
    private Context mContext;
    private LayoutInflater inflater;

    public ShowImageAdapter(List<PatrolDetailBean.ImglistBean> arr_bitmap, Context mContext) {
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
        Glide.with(mContext).load(arr_bitmap.get(position).img).into(holder.imageView);
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
