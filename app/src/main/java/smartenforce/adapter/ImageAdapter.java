package smartenforce.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import smartenforce.aty.ViewPhotoActivity;
import smartenforce.base.HttpApi;
import smartenforce.projectutil.Base64Util;
import smartenforce.util.UIUtil;
import smartenforce.widget.ProgressDialogUtil;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<String> arr_bitmap;
    private Context mContext;
    private LayoutInflater inflater;
    private ScrollView sv;
    private OnItemClickListener onItemClickListener;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_ADD = 2;


    public ImageAdapter(List<String> arr_bitmap, Context mContext, ScrollView sv) {
        this.arr_bitmap = arr_bitmap;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.sv = sv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NORMAL) {
            view = inflater.inflate(R.layout.image_item, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_image_add, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position <= arr_bitmap.size() - 1) {
            if (arr_bitmap.get(position).startsWith("UploadImage")) {
                Glide.with(mContext).load(HttpApi.URL_IMG_HEADER + arr_bitmap.get(position)).into(holder.imageView);
            } else {
                Glide.with(mContext).load(new File(arr_bitmap.get(position))).into(holder.imageView);
            }
            holder.imv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null)
                        onItemClickListener.onDelImageClick(position);
                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, ViewPhotoActivity.class).putExtra("Url", arr_bitmap.get(position)));
                }
            });
        } else {
            holder.iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null)
                        onItemClickListener.onImageAddClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arr_bitmap.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (arr_bitmap.size() == 0) {
            return TYPE_ADD;
        } else {
            return position == arr_bitmap.size() ? TYPE_ADD : TYPE_NORMAL;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, iv_add, imv_del;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_image);
            iv_add = (ImageView) itemView.findViewById(R.id.iv_item_add);
            imv_del = (ImageView) itemView.findViewById(R.id.imv_del);
            initWH(itemView, imageView, iv_add, imv_del);
        }

    }

    private void initWH(View itemView, ImageView iv_add, ImageView imv, ImageView imv_del) {
        int px_w = UIUtil.getScreenWidth(mContext);
        int px10 = UIUtil.dip2px(mContext, 10);
        int px_m_w = (px_w) / 4;
        ViewGroup.LayoutParams llt_parm = itemView.getLayoutParams();
        llt_parm.width = px_m_w;
        llt_parm.height = px_m_w;
        itemView.setLayoutParams(llt_parm);
        itemView.setPadding(px10 / 2, px10 / 2, px10 / 2, px10 / 2);
        if (imv != null) {
            ViewGroup.LayoutParams imv_parms = imv.getLayoutParams();
            imv_parms.width = px_m_w - px10;
            imv_parms.height = px_m_w - px10;
            itemView.setPadding(px10 / 2, px10 / 2, px10 / 2, px10 / 2);
            imv.setLayoutParams(imv_parms);
        }
        if (iv_add != null) {
            ViewGroup.LayoutParams add_parms = iv_add.getLayoutParams();
            add_parms.width = px_m_w - px10;
            add_parms.height = px_m_w - px10;
            itemView.setPadding(px10 / 2, px10 / 2, px10 / 2, px10 / 2);
            iv_add.setLayoutParams(add_parms);
        }
        if (imv_del != null) {
            ViewGroup.LayoutParams add_parms = imv_del.getLayoutParams();
            add_parms.width = (px10 * 3) / 2;
            add_parms.height = (px10 * 3) / 2;
            imv_del.setLayoutParams(add_parms);
        }

    }


    public interface OnItemClickListener {
        void onImageAddClick();

        void onDelImageClick(int p);

    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void notifyChanged() {
        this.notifyDataSetChanged();
        if (sv != null) {
            sv.fullScroll(View.FOCUS_DOWN);
        }
    }

    //如果是新增
    public String getImageListStr(boolean isPdfPic) {
        if (arr_bitmap.size()==0){
            return "";
        }
        ProgressDialogUtil.show(mContext, "处理中...");
        String str = Base64Util.toBase64String(mContext, arr_bitmap, isPdfPic);
        ProgressDialogUtil.hide();
        return str;
    }

    //取网络图片，返回长度不为0说明存在网络图片
    public String getImageUrlStr() {
        String Url = "";
        for (int i = 0; i < arr_bitmap.size(); i++) {
            if (arr_bitmap.get(i).startsWith("UploadImage")) {
                Url = Url + arr_bitmap.get(i) + "|";
            }
        }
        Url = Url.endsWith("|") ? Url.substring(0, Url.length() - 1) : Url;
        return Url;
    }

    // 获取非网络图片
    public String getImageFile() {
        List<String> fileList = new ArrayList<String>();
        for (int i = 0; i < arr_bitmap.size(); i++) {
            if (!arr_bitmap.get(i).startsWith("UploadImage")) {
                fileList.add(arr_bitmap.get(i));
            }
        }
        if (fileList.size()==0){
            return "";
        }
        ProgressDialogUtil.show(mContext, "处理中...");
        String str = Base64Util.toBase64String(mContext, fileList, false);
        ProgressDialogUtil.hide();
        return str;
    }


}
