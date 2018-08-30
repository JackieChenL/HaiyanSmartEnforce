package com.kas.clientservice.haiyansmartenforce.Module.DocumentSearch;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.TabLayout_line;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DocumentSearchActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.vp_documentSearch)
    ViewPager viewPager;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_header;

    List<DocSearchFragment> list_fragment;
    List<String> list_title;
    MyFragmentPagerAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_search;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("公文查询");
        iv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewPager();


    }
    private void initViewPager() {
        list_fragment = new ArrayList<DocSearchFragment>();
        for (int i = 0; i < 2; i++) {
            DocSearchFragment DocSearchFragment = new DocSearchFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position",i);
            DocSearchFragment.setArguments(bundle);
            list_fragment.add(DocSearchFragment);
        }
        list_title = new ArrayList<String>();
        list_title.add("已读公文");
        list_title.add("未读公文");

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),list_title,list_fragment);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout_line.setIndicator(this,tabLayout,15,15);

    }
}
