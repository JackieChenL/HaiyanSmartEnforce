package com.kas.clientservice.haiyansmartenforce.Module.PersonalCredit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Module.PersonalCreditListEntity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-08-03
 * 公司：COMS
 */

public class PersonalCreditAdapter extends BaseAdapter {
    Context mContext;
    List<PersonalCreditListEntity.GetDetailBean> list;

    public PersonalCreditAdapter(Context mContext, List<PersonalCreditListEntity.GetDetailBean> list) {
        this.mContext = mContext;
        this.list = list;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_personal_credit, null);
            vh.tv_content = (TextView) view.findViewById(R.id.tv_item_personalCredit_content);
            vh.tv_score = (TextView) view.findViewById(R.id.tv_item_personalCredit_score);
            vh.tv_time = (TextView) view.findViewById(R.id.tv_item_personalCredit_time);

            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        if (list.get(i).getType() == 3) {
            vh.tv_score.setText("-" + list.get(i).getScore());

        } else
            vh.tv_score.setText(list.get(i).getScore()+"");

        vh.tv_time.setText(list.get(i).getCreateDate());
        vh.tv_content.setText(list.get(i).getName());

        return view;
    }

    class ViewHolder {
        TextView tv_time, tv_content, tv_score;
    }
}
