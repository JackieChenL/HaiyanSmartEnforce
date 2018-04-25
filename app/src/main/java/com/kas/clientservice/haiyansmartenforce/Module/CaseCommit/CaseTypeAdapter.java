package com.kas.clientservice.haiyansmartenforce.Module.CaseCommit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.CaseTypeEntity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-04-24
 * 公司：COMS
 */

public class CaseTypeAdapter extends BaseAdapter{
    Context mContext;
    List<CaseTypeEntity> list;
    LayoutInflater inflater;

    public CaseTypeAdapter(List<CaseTypeEntity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null) {
            vh = new ViewHolder();
            view = inflater.inflate(R.layout.item_case_type,null);
            vh.tv = (TextView) view.findViewById(R.id.tv_item_caseType);
            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        vh.tv.setText(list.get(position).getName());

        return view;
    }
    class ViewHolder{
        TextView tv;
    }
}
