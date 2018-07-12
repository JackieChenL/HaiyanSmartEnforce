package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL_Zjcoms02 on 2018/6/25.
 */

public class XieTongListAdapter extends RecyclerView.Adapter<XieTongListAdapter.ViewHolder>{
    Context context;
    List<Project_Info.RtnBean> list;
    LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    public XieTongListAdapter(List<Project_Info.RtnBean> list, Context context){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.xietongadapter_item, parent, false);
    return new ViewHolder(view);
}

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tev_list_ajbh_code.setText(list.get(position).getProjname());
        holder.tv_list_time.setText(list.get(position).getStartdate());
        holder.llt_xtadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        if(list.get(position).getState().equals("已处理")){
            holder.iv_list_state.setBackgroundResource(R.drawable.zt_ywc);
        }else if(list.get(position).getState().equals("未处理")){
            holder.iv_list_state.setBackgroundResource(R.drawable.zt_dcl);
        }else if(list.get(position).getState().equals("已退回")){
            holder.iv_list_state.setBackgroundResource(R.drawable.huitui);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tev_list_ajbh_code,tv_list_time;
        LinearLayout llt_xtadapter;
        ImageView iv_list_state;
        public ViewHolder(View itemView) {
            super(itemView);
            tev_list_ajbh_code=(TextView) itemView.findViewById(R.id.tev_list_ajbh_code);
            tv_list_time=(TextView) itemView.findViewById(R.id.tv_list_time);
            llt_xtadapter=(LinearLayout) itemView.findViewById(R.id.llt_xtadapter);
            iv_list_state=(ImageView) itemView.findViewById(R.id.iv_list_state);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int p);

    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
