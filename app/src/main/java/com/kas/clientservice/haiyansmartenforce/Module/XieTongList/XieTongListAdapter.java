package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL_Zjcoms02 on 2018/6/25.
 */

public class XieTongListAdapter extends BaseAdapter{
    Context context;
    List<Project_Info.RtnBean> list;
    public XieTongListAdapter(List<Project_Info.RtnBean> list, Context context){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.xietongadapter_item,null);
            vh.tev_list_ajbh_code=(TextView) view.findViewById(R.id.tev_list_ajbh_code);
            vh.iv_list_state=(ImageView) view.findViewById(R.id.iv_list_state);
            vh.tv_list_time=(TextView) view.findViewById(R.id.tv_list_time);
            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        vh.tev_list_ajbh_code.setText(list.get(i).getProjname());
        vh.tv_list_time.setText(list.get(i).getStartdate());
        if(list.get(i).getState().equals("已处理")){
            vh.iv_list_state.setBackgroundResource(R.drawable.zt_ywc);
        }else if(list.get(i).getState().equals("未处理")){
            vh.iv_list_state.setBackgroundResource(R.drawable.zt_dcl);
        }else if(list.get(i).getState().equals("已退回")){
            vh.iv_list_state.setBackgroundResource(R.drawable.huitui);
        }
        return view;
    }
    class ViewHolder{
        ImageView iv_list_state;
        TextView tev_list_ajbh_code;
        TextView tv_list_time;
    }
}
