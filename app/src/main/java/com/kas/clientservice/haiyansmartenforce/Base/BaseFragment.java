package com.kas.clientservice.haiyansmartenforce.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    public abstract int getContentViewId();
    protected Context context;
    protected Context mContext;
    protected View mRootView;
    protected abstract String getTAG();
    public String TAG = getTAG();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView =inflater.inflate(getContentViewId(),container,false);
        ButterKnife.bind(this,mRootView);//绑定framgent
        this.context = getActivity();
        this.mContext = getActivity();
        initAllMembersView(savedInstanceState);
        initResAndListener();
        return mRootView;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    protected abstract void initAllMembersView(Bundle savedInstanceState);

    //空方法 规定子类 初始化监听器 和定义显示资源 的步骤
    protected void initResAndListener() {

    }

}