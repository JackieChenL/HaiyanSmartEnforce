package com.kas.clientservice.haiyansmartenforce.Module.DocumentSearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by JackieChen on 2017/1/3.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String> list_title;
    private List<DocSearchFragment> list_fragment;
    private DocSearchFragment mCurrentFragment;

    public MyFragmentPagerAdapter(FragmentManager fm, List<String> list_title, List<DocSearchFragment> list_fragment) {
        super(fm);
        this.list_title = list_title;
        this.list_fragment = list_fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_title.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (DocSearchFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public DocSearchFragment getCurrentFragment() {
        return mCurrentFragment;
    }

}
