package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.HuanweiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiCheckDetailEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuanweiHistoryDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_huanwei_handle_history_detail_content)
    TextView tv_content;
    @BindView(R.id.tv_huanwei_handle_history_detail_describe)
    TextView tv_describe;
    @BindView(R.id.tv_huanwei_handle_history_detail_name)
    TextView tv_name;
    @BindView(R.id.tv_huanwei_handle_history_detail_position)
    TextView tv_position;
    @BindView(R.id.tv_huanwei_handle_history_detail_project)
    TextView tv_project;
    @BindView(R.id.tv_huanwei_handle_history_detail_time)
    TextView tv_time;
    @BindView(R.id.rv_huanwei_handle_history_detail)
    RecyclerView recyclerView;
    @BindView(R.id.lv_huanwei_history_detail_history)
    ListView listView;
    @BindView(R.id.ll_huanwei_handle_history_detail_name)
            LinearLayout ll_name;

    String id = "";
    List<HuanweiAPI.HistoryDetail_checkEntity.BoardBean> list = new ArrayList<>();
    List<HuanweiCheckDetailEntity.TownBean> towns = new ArrayList<>();
    HuanweiHistoryDetailAdapter huanweiHistoryDetailAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_huanwei_history_detail;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("详情");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        id = getIntent().getStringExtra("id");
        initList();
        if (UserSingleton.USERINFO.getType().equals("6")) {

            loadCommitPeople();
        }
        if (UserSingleton.USERINFO.getType().equals("5")) {
            ll_name.setVisibility(View.GONE);
            loadCheckPeople();
        }
        if (UserSingleton.USERINFO.getType().equals("7")) {

            loadHandlePeople();
        }
    }

    private void loadHandlePeople() {

    }

    private void loadCheckPeople() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpGetCheckPeopleHistoryDetai(UserSingleton.USERINFO.getZFRYID(),id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiAPI.HistoryDetail_checkEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiAPI.HistoryDetail_checkEntity> historyDetail_checkEntityBaseEntity) {
                        if (historyDetail_checkEntityBaseEntity.isState()) {
                            HuanweiAPI.HistoryDetail_checkEntity entity = historyDetail_checkEntityBaseEntity.getRtn();
//                            tv_name.setText(entity.);
                            tv_content.setText(entity.JCNR);
                            tv_project.setText(entity.XM);
                            tv_position.setText(entity.JCDD);
                            tv_describe.setText(entity.QKMS);
                            tv_time.setText(entity.starttime);

                            if (entity.Img!=null&&entity.Img.size()!=0) {
                                for (int i = 0; i < entity.Img.size(); i++) {
                                    list_img.add(entity.Img.get(i).img);
                                }
                                adapter.notifyDataSetChanged();
                                setRecyclerViewHeight(list_img.size());
                            }else {
                                recyclerView.setVisibility(View.GONE);
                            }
                            if (entity.board!=null) {

                                list.addAll(entity.board);
                                huanweiHistoryDetailAdapter = new HuanweiHistoryDetailAdapter(list,mContext);
                                listView.setAdapter(huanweiHistoryDetailAdapter);
                                int height = list.size()* 270;
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,Dp2pxUtil.dip2px(mContext,height));
                                listView.setLayoutParams(params);
                                listView.setVerticalScrollBarEnabled(false);
                            }

                        }else {
                            ToastUtils.showToast(mContext,historyDetail_checkEntityBaseEntity.getErrorMsg());
                        }
                    }
                });
    }

    private void loadCommitPeople() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpGetCommitPeopleHistoryDetai(UserSingleton.USERINFO.getZFRYID(),id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiAPI.HistoryDetail_commitEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: "+responeThrowable);
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiAPI.HistoryDetail_commitEntity> historyDetail_commitEntityBaseEntity) {
                        if (historyDetail_commitEntityBaseEntity.isState()) {
                            HuanweiAPI.HistoryDetail_commitEntity entity = historyDetail_commitEntityBaseEntity.getRtn();
                            tv_name.setText(entity.Opername);
                            tv_content.setText(entity.JCNR);
                            tv_project.setText(entity.XM);
                            tv_position.setText(entity.JCDD);
                            tv_describe.setText(entity.QKMS);
                            tv_time.setText(entity.starttime);

                            if (entity.Img!=null) {
                                for (int i = 0; i < entity.Img.size(); i++) {
                                    list_img.add(entity.Img.get(i).getImg());
                                }
                                adapter.notifyDataSetChanged();
                                setRecyclerViewHeight(list_img.size());
                            }
                        }else {
                            ToastUtils.showToast(mContext,historyDetail_commitEntityBaseEntity.getErrorMsg());
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {

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
