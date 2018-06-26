package smartenforce.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

import smartenforce.aty.ViewPhotoActivity;
import smartenforce.base.HttpApi;
import smartenforce.bean.AddGraphBean;
import smartenforce.bean.AddPictureBean;


public class InquestDetailAdapter extends RecyclerView.Adapter<InquestDetailAdapter.ViewHolder> {
    private List<Object> list;
    private Context mContext;
    private LayoutInflater inflater;
    private int PicOrIchID=0;;
    public static final int TYPE_PICTURE = 1;
    public static final int TYPE_GRAPH = 2;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public InquestDetailAdapter(List<Object> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_PICTURE) {
            view = inflater.inflate(R.layout.item_picture, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_graph, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Object obj = list.get(position);
        if (obj instanceof AddPictureBean) {
            final AddPictureBean bean = (AddPictureBean) obj;
            PicOrIchID=bean.PhotographID;
            holder.tev_ShotManPho.setText("拍摄人：" + bean.ShotManPho);
            holder.tev_ShotTimePho.setText("拍摄时间：" + bean.ShotTimePho);
            holder.tev_ShotAddressPho.setText("拍摄地点：" + bean.ShotAddressPho);
            final String[] url_arry = bean.PictruePho.split("\\|");
            if (url_arry != null) {
                if (url_arry.length == 2) {
                    Glide.with(mContext).load(HttpApi.URL_IMG_HEADER + url_arry[1]).into(holder.imv_2);

                    holder.imv_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, ViewPhotoActivity.class).putExtra("Url", url_arry[1]));
                        }
                    });
                }
                Glide.with(mContext).load(HttpApi.URL_IMG_HEADER + url_arry[0]).into(holder.imv_1);
                holder.imv_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, ViewPhotoActivity.class).putExtra("Url", url_arry[0]));
                    }
                });
            }


        } else if (obj instanceof AddGraphBean) {
            final AddGraphBean bean = (AddGraphBean) obj;
            PicOrIchID=bean.IchnographyID;
            final String[] url_arry = bean.SketchMapIch.split("\\|");
            if (url_arry != null) {
                Glide.with(mContext).load(HttpApi.URL_IMG_HEADER + url_arry[0]).into(holder.imv_graph);
                holder.imv_graph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, ViewPhotoActivity.class).putExtra("Url", url_arry[0]));
                    }
                });
            }
        }
        final int ID=PicOrIchID;
        holder.tev_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onPrintClick(ID, 11);
                }
            }
        });
        holder.tev_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onEditClick(ID, position);
                }
            }
        });
        holder.tev_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDelClick(ID, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = list.get(position);
        if (obj instanceof AddGraphBean) {
            return TYPE_GRAPH;
        } else {
            return TYPE_PICTURE;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tev_ShotManPho, tev_ShotTimePho, tev_ShotAddressPho;

        ImageView imv_1, imv_2, imv_graph;
        TextView tev_print, tev_edit, tev_del;

        public ViewHolder(View itemView) {
            super(itemView);
            tev_ShotManPho = (TextView) itemView.findViewById(R.id.tev_ShotManPho);
            tev_ShotTimePho = (TextView) itemView.findViewById(R.id.tev_ShotTimePho);
            tev_ShotAddressPho = (TextView) itemView.findViewById(R.id.tev_ShotAddressPho);
            tev_print = (TextView) itemView.findViewById(R.id.tev_print);
            tev_edit = (TextView) itemView.findViewById(R.id.tev_edit);
            tev_del = (TextView) itemView.findViewById(R.id.tev_del);
            imv_1 = (ImageView) itemView.findViewById(R.id.imv_1);
            imv_2 = (ImageView) itemView.findViewById(R.id.imv_2);
            imv_graph = (ImageView) itemView.findViewById(R.id.imv_graph);
        }

    }

    public interface OnItemClickListener {
        void onPrintClick(int id, int type);

        void onEditClick(int id, int postion);

        void onDelClick(int id, int postion);
    }

}
