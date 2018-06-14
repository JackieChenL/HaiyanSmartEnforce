package com.kas.clientservice.haiyansmartenforce.tcsf.patrol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.SearchListBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.OnItemClickListener;

import java.util.List;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    List<SearchListBean> list;
    Context mContext;
    LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public SearchListAdapter(List<SearchListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_exit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SearchListBean bean = list.get(position);
        holder.tev_cphm.setText(bean.UCarnum);
        holder.tev_pwbh.setText(bean.BerthName);
        holder.tev_trsj.setText(bean.StartTime);
        holder.llt_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tev_pwbh, tev_cphm, tev_trsj;
        LinearLayout llt_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tev_pwbh = (TextView) itemView.findViewById(R.id.tev_pwbh);
            tev_cphm = (TextView) itemView.findViewById(R.id.tev_cphm);
            tev_trsj = (TextView) itemView.findViewById(R.id.tev_trsj);
            llt_item = (LinearLayout) itemView.findViewById(R.id.llt_item);
        }
    }


}
