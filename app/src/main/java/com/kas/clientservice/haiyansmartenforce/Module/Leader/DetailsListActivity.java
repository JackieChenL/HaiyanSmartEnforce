package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.FragmentViewPagerAdapter;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DetailsListActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{
	private Context mContext = DetailsListActivity.this;
	
	private View mView;
	private List<Fragment> listViews;
	private TextView details_await;
	private TextView details_end;
	private ViewPager details_vg;
	@BindView(R.id.tv_header_title)
	TextView tv_title;
	@BindView(R.id.iv_heaer_back)
	ImageView iv_back;

	@Override
	protected int getLayoutId() {
		return R.layout.mine_viewpage_activity;
	}

	@Override
	protected String getTAG() {
		return this.toString();
	}

	@Override
	protected void initResAndListener() {
		super.initResAndListener();
		initView();
	}

	private void initView(){
		details_vg = (ViewPager)findViewById(R.id.details_vg);
		details_await = (TextView)findViewById(R.id.details_await);
		details_end = (TextView)findViewById(R.id.details_end);
		mView = (View)findViewById(R.id.details_line);
		details_await.setOnClickListener(this);
		details_end.setOnClickListener(this);
		details_vg.setOnPageChangeListener(this);
		iv_back.setOnClickListener(this);
		isWhat();
	}
	/**
	 * 页面头部栏 标题
	 */
	private void isWhat(){
		Intent intent = getIntent();
		int what = intent.getIntExtra("what", 0);
//		if(what == Constants.WhatDecisionCaseDetailsCase){
//			tv_title.setText("案件详情");
//
//		}else if(what == Constants.WhatDecisionCaseDetailsSource){
//			tv_title.setText("案源详情");
//
//		}else if(what == Constants.WhatDecisionTotailsDetailsPersonage){
//			tv_title.setText("业务详情");
//
//		}else if(what == Constants.WhatDecisionAreaDetailsPersonage){
//			tv_title.setText("业务详情");
//
//		}else
		if(what == Constants.WhatDepartmentDetailsCase){
			String type = intent.getStringExtra("type");
			if(type.equals("simple")){
				tv_title.setText("简单程序");
			}else if(type.equals("generic")){
				tv_title.setText("一般程序");
			}
			
		}else if(what == Constants.WhatDepartmentDetailsSource){
			String type = intent.getStringExtra("type");
			if(type.equals("initiative")){
				tv_title.setText("巡查任务");
			}else if(type.equals("passivity")){
				tv_title.setText("线索任务");
			}
			
		}
//		else if(what == Constants.WhatPersonageDetailsSource){
//			String type = intent.getStringExtra("type");
//			if(type.equals("initiative")){
//				tv_title.setText("主动任务");
//			}else if(type.equals("passivity")){
//				tv_title.setText("被动任务");
//			}
//
//		}else if(what == Constants.WhatPersonageDetailsCase){
//			String type = intent.getStringExtra("type");
//			if(type.equals("generic")){
//				tv_title.setText("一般程序");
//			}else if(type.equals("simple")){
//				tv_title.setText("简易程序");
//			}
//
//		}else{
//			Toast.makeText(mContext, "错误", Toast.LENGTH_SHORT).show();
//		}
		loadFragment();
	}
	/**
	 * DecisionAreaDetailsPersonage
	 */
	private void loadFragment(){
		listViews = new ArrayList<Fragment>();
		DetailsAwaitFragment await = new DetailsAwaitFragment();
		DetailsEndFragment end = new DetailsEndFragment();
		listViews.add(await);
		listViews.add(end);
		FragmentViewPagerAdapter fragmentViewPager = new FragmentViewPagerAdapter(getSupportFragmentManager(), listViews);
		details_vg.setAdapter(fragmentViewPager);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.iv_heaer_back:
			finish();
			break;
		case R.id.details_await:
			details_vg.setCurrentItem(0);
			break;
		case R.id.details_end:
			details_vg.setCurrentItem(1);
			break;
		}
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		Line(arg1,arg0);
		if(arg0 == 0){
			details_await.setTextColor(Color.parseColor("#FFFFFF"));
			details_end.setTextColor(Color.parseColor("#EAEAEA"));
		}else if(arg0 == 1){
			details_await.setTextColor(Color.parseColor("#EAEAEA"));
			details_end.setTextColor(Color.parseColor("#FFFFFF"));
		}
	}
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
	private void Line(float position, int page){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int w = displayMetrics.widthPixels;
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mView.getLayoutParams();
		layoutParams.width = (int)(w/listViews.size()-(w*0.148));
		layoutParams.leftMargin =(int)( w/listViews.size()*position + w/listViews.size()*page)+(int)((w*0.148)/2);
		mView.setLayoutParams(layoutParams);
	}
}