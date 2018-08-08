package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import java.util.ArrayList;
import java.util.List;

public class Decision extends FragmentActivity implements View.OnClickListener {
	TextView tv_title_name;
	ImageView iv_title_back;
	TextView tv_decision_totality;
	TextView tv_decision_area;
	ImageView iv_Line;
	ViewPager vp_viewpager;
	List<Fragment> listview = new ArrayList<Fragment>();
	/** 获取的屏幕宽度 */
	int widthX;
	/** 图片的偏移量 */
	int moveX;
	/** 获取的图片宽度 */
	int ImageWidth;
	int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.decision);
		initRes();
		tv_title_name.setText("业务决策");
		Vp_Totality_Fragment fragment1=new Vp_Totality_Fragment();
		Vp_Area_Fragment fragment2=new Vp_Area_Fragment();
		listview.add(fragment1);
		listview.add(fragment2);
		Fragment_ViewPager_Adapter adapter = new Fragment_ViewPager_Adapter(getSupportFragmentManager(), listview);
		vp_viewpager.setAdapter(adapter);
		ViewPagerShow();
	}
	
	/** 获取屏幕的宽度和图片的宽度 */
	public void ViewPagerShow() {
		vp_viewpager.setOnPageChangeListener(new PageChangeListener());
	}
	
	// viewpager监听事件
		class PageChangeListener implements ViewPager.OnPageChangeListener {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					tv_decision_totality.setTextColor(getResources().getColor(R.color.white));
					tv_decision_area.setTextColor(getResources().getColor(R.color.grey_400));
					break;
				case 1:
					tv_decision_totality.setTextColor(getResources().getColor(R.color.grey_400));
					tv_decision_area.setTextColor(getResources().getColor(R.color.white));
					break;

				default:
					break;
				}

			}

		}

	private void initRes() {
		tv_title_name=(TextView) findViewById(R.id.tv_header_title);
		iv_title_back=(ImageView) findViewById(R.id.iv_heaer_back);
		tv_decision_totality=(TextView) findViewById(R.id.tv_decision_totality);
		tv_decision_area=(TextView) findViewById(R.id.tv_decision_area);
		vp_viewpager=(ViewPager) findViewById(R.id.vp_viewpager);
		iv_Line=(ImageView) findViewById(R.id.iv_Line);
		iv_title_back.setOnClickListener(this);
		tv_decision_totality.setOnClickListener(this);
		tv_decision_area.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_back:
			finish();
			break;
		case R.id.tv_decision_totality:
			vp_viewpager.setCurrentItem(0);
			tv_decision_totality.setTextColor(getResources().getColor(R.color.white));
			tv_decision_area.setTextColor(getResources().getColor(R.color.grey_400));
			break;
		case R.id.tv_decision_area:
			vp_viewpager.setCurrentItem(1);
			tv_decision_totality.setTextColor(getResources().getColor(R.color.grey_400));
			tv_decision_area.setTextColor(getResources().getColor(R.color.white));
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
	}
}