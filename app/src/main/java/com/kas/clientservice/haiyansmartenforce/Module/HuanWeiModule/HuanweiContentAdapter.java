package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.HuanweiAPI;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-15
 * 公司：COMS
 */

public class HuanweiContentAdapter extends BaseAdapter {
    List<HuanweiAPI.HuanweiContentEntity> list;
    Context mContext;

    public HuanweiContentAdapter(List<HuanweiAPI.HuanweiContentEntity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_text,null);
            vh.textView = (TextView) view.findViewById(R.id.tv_item_text);
            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        vh.textView.setText(list.get(i).jcnr);
        return view;
    }

    class  ViewHolder{
        TextView textView;
    }

}
