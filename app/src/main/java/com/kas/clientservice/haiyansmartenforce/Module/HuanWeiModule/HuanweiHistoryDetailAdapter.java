package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.HuanweiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 时间：2018-05-28
 * 公司：COMS
 */

public class HuanweiHistoryDetailAdapter extends BaseAdapter {
    List<HuanweiAPI.HistoryDetail_checkEntity.BoardBean> list;
    Context mContext;

    public HuanweiHistoryDetailAdapter(List<HuanweiAPI.HistoryDetail_checkEntity.BoardBean> list, Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_history_detail,null);
            vh.tv_name = (TextView) view.findViewById(R.id.tv_item_history_detail_name);
            vh.tv_describe = (TextView) view.findViewById(R.id.tv_item_history_detail_describe);
            vh.recyclerView = (RecyclerView) view.findViewById(R.id.rv_item_history_detail);

            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv_describe.setText(list.get(i).QKMS);
        vh.tv_name.setText("整改人员："+list.get(i).zgryname);

        initList(vh.recyclerView);
        if (list.get(i).Img!=null) {
            for (int j = 0; j < list.get(i).Img.size(); j++) {
                list_img.add(list.get(i).Img.get(j).img);
            }
            adapter.notifyDataSetChanged();
        }
        return view;
    }

    class ViewHolder{
        TextView tv_name,tv_describe;
        RecyclerView recyclerView;
    }

    List<String> list_img;
    ImageListRvAdapter adapter;
    private void initList(RecyclerView recyclerView) {
        list_img = new ArrayList<>();
        adapter = new ImageListRvAdapter(list_img, mContext);
        RecyclerView.LayoutManager manager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnImagelickListener(new ImageListRvAdapter.OnImagelickListener() {
            @Override
            public void onImageClick(int p) {
                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra("url", list_img.get(p));
                mContext.startActivity(intent);
            }
        });
    }
}
