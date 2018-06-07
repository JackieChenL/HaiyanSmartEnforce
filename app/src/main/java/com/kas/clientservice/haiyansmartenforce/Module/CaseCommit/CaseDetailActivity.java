package com.kas.clientservice.haiyansmartenforce.Module.CaseCommit;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Entity.CaseSearchEntity;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class CaseDetailActivity extends BaseActivity {
    @BindView(R.id.tv_caseDetail_smallClass)
    TextView tv_class;
    @BindView(R.id.tv_caseDetail_time)
    TextView tv_time;
    @BindView(R.id.tv_caseDetail_position)
    TextView tv_postion;
    @BindView(R.id.tv_caseDetail_describe)
    TextView tv_des;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.rv_caseDetail)
    RecyclerView recyclerView;

    CaseSearchEntity caseSearchEntity;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_detail;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("案件详情");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String entity = getIntent().getStringExtra("entity");
        caseSearchEntity = gson.fromJson(entity,CaseSearchEntity.class);

        tv_class.setText(caseSearchEntity.getSmallclassname());
        tv_postion.setText(caseSearchEntity.getAddress());
        tv_des.setText(caseSearchEntity.getProbdesc());
        tv_time.setText(caseSearchEntity.getStartdate().replace("T"," ").substring(0,caseSearchEntity.getStartdate().indexOf(".")));


        initList();
        if (caseSearchEntity.files!=null) {
            String[] imgs = caseSearchEntity.files.split(",");
            Collections.addAll(list_img,imgs);
            adapter.notifyDataSetChanged();
            setRecyclerViewHeight(list_img.size());
        }
    }

    List<String> list_img = new ArrayList<>();
    ImageListRvAdapter adapter;

    private void initList() {
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
                startActivity(intent);
            }
        });
    }
    public void setRecyclerViewHeight(int size) {
        int height = ((size / 2) + 1) * 140 + 30;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
//        layoutParams.setMargins(0, Dp2pxUtil.dip2px(mContext, 5), 0, Dp2pxUtil.dip2px(mContext, 50));
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }
}
