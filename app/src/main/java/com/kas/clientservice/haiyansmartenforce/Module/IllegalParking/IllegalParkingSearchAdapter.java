package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.ParkingSearchEntity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-07
 * 公司：COMS
 */

public class IllegalParkingSearchAdapter extends BaseAdapter {
    List<ParkingSearchEntity.BoardBean> list;
    Context mContext;
    LayoutInflater inflater;

    public IllegalParkingSearchAdapter(List<ParkingSearchEntity.BoardBean> list, Context mContext) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null) {
            vh = new ViewHolder();
            view = inflater.inflate(R.layout.item_parking_search,null);
            vh.tv_num = (TextView) view.findViewById(R.id.tv_item_search_num);
            vh.tv_time = (TextView) view.findViewById(R.id.tv_item_search_time);
            vh.tv_status = (TextView) view.findViewById(R.id.tv_item_search_status);

            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        vh.tv_num.setText(list.get(i).getCarNum());
        vh.tv_time.setText(list.get(i).getWFtime());
        if (list.get(i).getState().equals("1")) {
            vh.tv_status.setText("已处理");
            vh.tv_status.setTextColor(mContext.getResources().getColor(R.color.green));
        }else {
            vh.tv_status.setText("未处理");
            vh.tv_status.setTextColor(mContext.getResources().getColor(R.color.crimson));
        }
        return view;
    }

    class ViewHolder{
        TextView tv_num,tv_time,tv_status;

    }
}