package com.kas.clientservice.haiyansmartenforce.Base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

	List<Fragment> listview;

	public FragmentViewPagerAdapter(FragmentManager fm,
									List<Fragment> listview) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.listview = listview;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return listview.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listview.size();
	}
}