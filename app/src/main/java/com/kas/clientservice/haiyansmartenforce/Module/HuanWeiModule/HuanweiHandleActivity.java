package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.HuanweiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiCheckDetailEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiHandleDetailEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuanweiHandleActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_huanwei_handle_detail_content)
    TextView tv_content;
    @BindView(R.id.tv_huanwei_handle_detail_describe)
    TextView tv_describe;
    @BindView(R.id.tv_huanwei_handle_detail_name)
    TextView tv_name;
    @BindView(R.id.tv_huanwei_handle_detail_position)
    TextView tv_position;
    @BindView(R.id.tv_huanwei_handle_detail_project)
    TextView tv_project;
    @BindView(R.id.tv_huanwei_handle_detail_time)
    TextView tv_time;
    @BindView(R.id.rv_huanwei_handle_detail)
    RecyclerView recyclerView;
    //    @BindView(R.id.ll_huanwei_handle_detail)
//    LinearLayout ll_main;
    @BindView(R.id.tv_huanwei_handle_detail_handle)
    TextView tv_zhenggai;
    @BindView(R.id.tv_huanwei_handle_detail_shensu)
    TextView tv_shengsu;

    String id = "";
    List<String> list_town = new ArrayList<>();
    List<HuanweiCheckDetailEntity.TownBean> towns = new ArrayList<>();
    HuanweiHandleDetailEntity huanweiCheckDetailEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huanwei_handle;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("整改");
        iv_back.setOnClickListener(this);
        tv_zhenggai.setOnClickListener(this);
        tv_shengsu.setOnClickListener(this);

        id = getIntent().getStringExtra("id");
        initList();
        loadData();
    }
    private void loadData() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpHandleDetail(UserSingleton.USERINFO.getZFRYID(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiHandleDetailEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiHandleDetailEntity> s) {
//                        Log.i(TAG, "onNext: "+s);
                        if (s.isState()) {
                            huanweiCheckDetailEntity = s.getRtn();
                            tv_content.setText(huanweiCheckDetailEntity.getJCNR());
                            tv_describe.setText(huanweiCheckDetailEntity.getQKMS());
                            tv_name.setText(huanweiCheckDetailEntity.getOpername());
                            tv_position.setText(huanweiCheckDetailEntity.getJCDD());
                            tv_time.setText(huanweiCheckDetailEntity.getStarttime());
                            tv_project.setText(huanweiCheckDetailEntity.getXM());
                            if (huanweiCheckDetailEntity.getImg()!=null) {
                                for (int i = 0; i < huanweiCheckDetailEntity.getImg().size(); i++) {
                                    list_img.add(huanweiCheckDetailEntity.getImg().get(i).getImg());
                                }
                                adapter.notifyDataSetChanged();
                                setRecyclerViewHeight(list_img.size());

                            }
//                            list_img.addAll(huanweiCheckDetailEntity.getImg());
//                            towns.addAll(s.getRtn().getTown());
                            for (int i = 0; i < towns.size(); i++) {
                                list_town.add(towns.get(i).getTown());
                            }
//                            loadPPw();
                        } else {
                            showNetErrorToast();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext,HuanweiHandleCommitActivity.class);
        switch (view.getId()) {

            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_huanwei_handle_detail_handle:
                intent.putExtra("changeState",2);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
            case R.id.tv_huanwei_handle_detail_shensu:
                intent.putExtra("changeState",3);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }


    }

    List<String> list_img;
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
