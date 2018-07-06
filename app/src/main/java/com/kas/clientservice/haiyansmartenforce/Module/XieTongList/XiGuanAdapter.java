package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

/**
 * Created by DELL_Zjcoms02 on 2018/7/1.
 */

public class XiGuanAdapter extends BaseAdapter {
    Context context;
    List<XIGuanBean.RtnBean> list;
    public XiGuanAdapter(List<XIGuanBean.RtnBean> list, Context context){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.xiguan_adapter,null);
            vh.tev_xiguan_adapter=(TextView) view.findViewById(R.id.tev_xiguan_adapter);
            view.setTag(vh);
        }else vh = (ViewHolder) view.getTag();

        vh.tev_xiguan_adapter.setText(list.get(position).short_sentence);
        final String word=vh.tev_xiguan_adapter.getText().toString().trim();
        vh.tev_xiguan_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Listeren != null){
                    Listeren.onItem(position,v);
                }
            }
        });
        return view;
    }

    class ViewHolder{
        TextView tev_xiguan_adapter;
    }
    public interface Listeren{
        void onItem(int p, View v);
    }
    public void setListner(Listeren Listeren) {
        this.Listeren = Listeren;

    }

    private Listeren Listeren;

}
