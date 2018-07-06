package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * Created by DELL_Zjcoms02 on 2018/7/3.
 */

public class HuZhuAdapter extends BaseAdapter {
    Context context;
    List<HuZhuBean> list;
    public HuZhuAdapter(List<HuZhuBean> list, Context context){
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
            view = LayoutInflater.from(context).inflate(R.layout.huzhu_list,null);
            vh.tev_adapter_name=(TextView) view.findViewById(R.id.tev_adapter_name);
            vh.tev_adapter_addressw=(TextView) view.findViewById(R.id.tev_adapter_addressw);
            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        vh.tev_adapter_name.setText(list.get(i).Name);
        vh.tev_adapter_addressw.setText(list.get(i).Address);
        return view;
    }
    class ViewHolder{
        TextView tev_adapter_name,tev_adapter_addressw;

    }
}
