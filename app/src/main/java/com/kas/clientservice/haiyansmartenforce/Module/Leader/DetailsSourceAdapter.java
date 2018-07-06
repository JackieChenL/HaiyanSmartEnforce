package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.SourceDetailsInfo;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.ArrayList;

public class DetailsSourceAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SourceDetailsInfo.KsBean> mArrayList;

    public DetailsSourceAdapter(Context mContext, ArrayList<SourceDetailsInfo.KsBean> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mArrayList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mArrayList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    public void upDate(ArrayList<SourceDetailsInfo.KsBean> mArrayList) {
        this.mArrayList = mArrayList;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        takeView take;
        SourceDetailsInfo.KsBean details = mArrayList.get(arg0);
        if (arg1 == null) {
            take = new takeView();
            arg1 = LayoutInflater.from(mContext).inflate(R.layout.mine_viewpage_source_item, arg2, false);
            take.DetailsSourceTvTitile = (TextView) arg1.findViewById(R.id.DetailsSourceTvTitile);
            take.DetailsSourceTvNumber = (TextView) arg1.findViewById(R.id.DetailsSourceTvNumber);
            take.DetailsSourceTvState = (TextView) arg1.findViewById(R.id.DetailsSourceTvState);
            take.DetailsSourceTvNowLink = (TextView) arg1.findViewById(R.id.DetailsSourceTvNowLink);
            take.DetailsSourceTvNnlawful = (TextView) arg1.findViewById(R.id.DetailsSourceTvNnlawful);
            take.DetailsSourceTvTime = (TextView) arg1.findViewById(R.id.DetailsSourceTvTime);
            take.DetailsSourceTvAddress = (TextView) arg1.findViewById(R.id.DetailsSourceTvAddress);
            arg1.setTag(take);
        } else {
            take = (takeView) arg1.getTag();
        }
        if (details.getNameECSou().equals("")) {
            take.DetailsSourceTvTitile.setVisibility(View.GONE);
        } else
            take.DetailsSourceTvTitile.setText(details.getNameECSou());
        take.DetailsSourceTvNumber.setText(details.getSouNumberSou());
        take.DetailsSourceTvState.setText(details.getState());
        take.DetailsSourceTvNowLink.setText(details.getNameSoF());
        take.DetailsSourceTvNnlawful.setText(details.getNameFiL());
        take.DetailsSourceTvTime.setText(details.getEntryTimeSou());
        take.DetailsSourceTvAddress.setText(details.getAddressSou());
        return arg1;
    }

    class takeView {
        TextView DetailsSourceTvTitile;
        TextView DetailsSourceTvNumber;
        TextView DetailsSourceTvState;
        TextView DetailsSourceTvNowLink;
        TextView DetailsSourceTvNnlawful;
        TextView DetailsSourceTvTime;
        TextView DetailsSourceTvAddress;
    }
}