package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiListEntity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * 描述：
 * 时间：2018-05-15
 * 公司：COMS
 */

public class HuanweiListAdapter extends BaseAdapter {
    List<HuanweiListEntity.BoardBean> list;
    Context mContext;

    public HuanweiListAdapter(List<HuanweiListEntity.BoardBean> list, Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_huanwei_check_list,null);
            vh.imageView = (ImageView) view.findViewById(R.id.iv_item_huanwei_checklist);
            vh.tv_describe = (TextView) view.findViewById(R.id.tv_item_huanwei_checklist_describe);
            vh.tv_position = (TextView) view.findViewById(R.id.tv_item_huanwei_checklist_position);
            vh.tv_type = (TextView) view.findViewById(R.id.tv_item_huanwei_checklist_type);

            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        vh.tv_type.setText(list.get(i).getJCNR());
        vh.tv_describe.setText(list.get(i).getQKMS());
        vh.tv_position.setText(list.get(i).getJCDD());

        if (list.get(i).getShstate()!=null) {
            if (list.get(i).getShstate().equals("1")) {
                if (list.get(i).getTOWNID().equals("0")) {
                    vh.imageView.setImageResource(R.drawable.status_daishenghe);
                }else {
                    vh.imageView.setImageResource(R.drawable.status_daizhegngai);
                }
            }
            if (list.get(i).getShstate().equals("3")) {
                vh.imageView.setImageResource(R.drawable.status_shengsu_back);
            }
            if (list.get(i).getShstate().equals("6")) {
                vh.imageView.setImageResource(R.drawable.status_shegnsu_done);
            }
            if (list.get(i).getShstate().equals("7")) {
                vh.imageView.setImageResource(R.drawable.status_zhenggai_done);
            }
            if (list.get(i).getShstate().equals("8")) {
                vh.imageView.setImageResource(R.drawable.status_chexiao);
            }
            if (list.get(i).getShstate().equals("9")) {
                vh.imageView.setImageResource(R.drawable.status_back);
            }
            if (list.get(i).getShstate().equals("0")) {
                vh.imageView.setImageResource(R.drawable.status_save);
            }
        }

        return view;
    }

    class ViewHolder{
        ImageView imageView;
        TextView tv_type,tv_position,tv_describe;
    }
}
