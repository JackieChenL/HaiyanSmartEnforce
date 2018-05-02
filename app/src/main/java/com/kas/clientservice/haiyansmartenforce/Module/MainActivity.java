package com.kas.clientservice.haiyansmartenforce.Module;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jorge.circlelibrary.ImageCycleView;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.CaseCommit.CaseCommitActivity;
import com.kas.clientservice.haiyansmartenforce.Module.History.HistoryActivity;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.IllegalParkingCommitActivity;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ParkingRecordSearchActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.AppParameter;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ll_main_caseSearch)
    LinearLayout ll_caseSearch;
    @BindView(R.id.ll_main_quickCommit)
    LinearLayout ll_quickCommit;
    @BindView(R.id.cycleView_main)
    ImageCycleView imageCycleView;
    @BindView(R.id.ll_wentishangbao)
    LinearLayout ll_wentishangbao;
    @BindView(R.id.banner_main)
    UPMarqueeView upview1;
    @BindView(R.id.ll_lishijilu)
    LinearLayout ll_lishijilu;


    ArrayList<String> list_detail;
    ArrayList<String> list_imageURL;
    List<String> list_headline;
    private List<View> views = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        Log.i(TAG, "initResAndListener: "+ AppParameter.getApplicationId(mContext));
        ll_caseSearch.setOnClickListener(this);
        ll_quickCommit.setOnClickListener(this);
        ll_wentishangbao.setOnClickListener(this);
        ll_lishijilu.setOnClickListener(this);
        initBanner();
        initVerticalBanner();
    }

    private void initVerticalBanner() {
        setView();
        upview1.setViews(views);
    }

    private void initBanner() {
        list_detail = new ArrayList<>();
        list_imageURL = new ArrayList<>();

        list_detail.add("测试1");
        list_detail.add("测试2");
        list_detail.add("测试3");
        list_imageURL.add("1");
        list_imageURL.add("1");
        list_imageURL.add("1");

        imageCycleView.setCycle_T(ImageCycleView.CYCLE_T.CYCLE_VIEW_NORMAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, 120));
        layoutParams.setMargins(0, Dp2pxUtil.dip2px(mContext, 15), 0, 0);
        imageCycleView.setLayoutParams(layoutParams);
        ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                imageView.setImageResource(R.drawable.indexbananer);
            }

            @Override
            public void onImageClick(int position, View imageView) {
                ToastUtils.showToast(mContext, "click");
            }
        };
        imageCycleView.setImageResources(list_detail, list_imageURL, mAdCycleViewListener);
        // 是否隐藏底部
        imageCycleView.hideBottom(true);
        imageCycleView.startImageCycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_quickCommit:
                startActivity(new Intent(mContext, IllegalParkingCommitActivity.class));
                break;
            case R.id.ll_main_caseSearch:
                startActivity(new Intent(mContext, ParkingRecordSearchActivity.class));
                break;
            case R.id.ll_wentishangbao:
                startActivity(new Intent(mContext, CaseCommitActivity.class));
                break;
            case R.id.ll_lishijilu:
                startActivity(new Intent(mContext, HistoryActivity.class));
                break;
        }
    }

    private void setView() {
        list_headline = new ArrayList<>();
        list_headline.add("测试");
        list_headline.add("测试2");
        list_headline.add("测试3");
        for (int i = 0; i < list_headline.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_main_banner, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv_item_main_banner);
            //进行对控件赋值
            tv1.setText(list_headline.get(i));

            /**
             * 设置头条的点击事件
             */
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }
}
