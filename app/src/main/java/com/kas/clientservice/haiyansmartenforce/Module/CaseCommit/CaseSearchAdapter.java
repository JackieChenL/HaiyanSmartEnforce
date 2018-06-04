package com.kas.clientservice.haiyansmartenforce.Module.CaseCommit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.CaseSearchEntity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-31
 * 公司：COMS
 */

public class CaseSearchAdapter extends BaseAdapter {
    List<CaseSearchEntity> list;
    Context mContext;

    public CaseSearchAdapter(List<CaseSearchEntity> list, Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_case_search,null);
            vh.tv_class = (TextView) view.findViewById(R.id.tv_item_caseSearch_class);
            vh.tv_time = (TextView) view.findViewById(R.id.tv_item_caseSearch_time);
            vh.tv_des = (TextView) view.findViewById(R.id.tv_item_caseSearch_describe);
            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        vh.tv_class.setText("类型："+list.get(i).smallclassname);
        String time = list.get(i).getStartdate().replace("T"," ").substring(0,list.get(i).getStartdate().indexOf("."));
        vh.tv_time.setText(time);
        vh.tv_des.setText(list.get(i).getProbdesc());
        return view;
    }

    class ViewHolder{
        TextView tv_class,tv_time,tv_des;
    }
}
