package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class Fragment_ViewPager_Adapter extends FragmentPagerAdapter {

	List<Fragment> listview;

	public Fragment_ViewPager_Adapter(FragmentManager fm,
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