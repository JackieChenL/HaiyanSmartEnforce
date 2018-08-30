package com.kas.clientservice.haiyansmartenforce.Module.DocumentSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.DocSearchListEntity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-08-29
 * 公司：COMS
 */

public class DocSearchListAdapter extends BaseAdapter {
    Context context;
    List<DocSearchListEntity.RtnBean> list;

    public DocSearchListAdapter(Context context, List<DocSearchListEntity.RtnBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_doc_search_list,null);
            vh.tv_time  = convertView.findViewById(R.id.tv_item_doc_time);
            vh.tv_title  = convertView.findViewById(R.id.tv_item_doc_title);
            convertView.setTag(vh);
        }else vh = (ViewHolder) convertView.getTag();

        vh.tv_time.setText(list.get(position).getEntryTimeOff().trim().substring(2));
        vh.tv_title.setText(list.get(position).getTitleOff());
        return convertView;
    }

    class ViewHolder{
        TextView tv_time,tv_title;
    }
}
