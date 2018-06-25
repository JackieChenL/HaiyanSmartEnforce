package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.HuanweiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Base.StringAdapter;
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

public class HuanweiCheckDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_huanwei_check_detail_content)
    TextView tv_content;
    @BindView(R.id.tv_huanwei_check_detail_describe)
    TextView tv_describe;
    @BindView(R.id.tv_huanwei_check_detail_name)
    TextView tv_name;
    @BindView(R.id.tv_huanwei_check_detail_position)
    TextView tv_position;
    @BindView(R.id.tv_huanwei_check_detail_project)
    TextView tv_project;
    @BindView(R.id.tv_huanwei_check_detail_time)
    TextView tv_time;
    @BindView(R.id.rv_huanwei_check_detail)
    RecyclerView recyclerView;
    @BindView(R.id.tv_huanwei_check_detail_town)
    TextView tv_town;
    @BindView(R.id.ll_huanwei_check_detail)
    LinearLayout ll_main;
    @BindView(R.id.tv_huanwei_check_detail_chexiao)
    TextView tv_chexiao;
    @BindView(R.id.tv_huanwei_check_detail_back)
    TextView tv_back;
    @BindView(R.id.tv_huanwei_check_detail_pass)
    TextView tv_pass;
    @BindView(R.id.tv_huanwei_check_detail_nopass)
    TextView tv_noPass;
    @BindView(R.id.tv_huanwei_check_detail_commit_town)
    TextView tv_commit_town;
    @BindView(R.id.tv_huanwei_check_detail_score)
    TextView tv_score;

    String id = "";
    PopupWindow ppw;
    List<String> list_town = new ArrayList<>();
    StringAdapter stringAdapter;
    String townName;
    List<HuanweiCheckDetailEntity.TownBean> towns = new ArrayList<>();
    HuanweiCheckDetailEntity huanweiCheckDetailEntity;
    String townId = "";
    String status = "";
    boolean needChoseTown = false;
    int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huanwei_check_detail;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();


        iv_back.setOnClickListener(this);
        tv_town.setOnClickListener(this);
        tv_pass.setOnClickListener(this);
        tv_chexiao.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_noPass.setOnClickListener(this);
        id = getIntent().getStringExtra("id");
        status = getIntent().getStringExtra("status");
        Log.i(TAG, "initResAndListener: " + status);
        if (status.equals("1")) {
            tv_back.setVisibility(View.GONE);
        }
        if (status.equals("3") || status.equals("2")) {
            tv_chexiao.setVisibility(View.GONE);
        }
        initList();
        loadData();

    }

    private void loadData() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpGetCheckDetail(UserSingleton.USERINFO.getReviewNameID(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiCheckDetailEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiCheckDetailEntity> s) {
//                        Log.i(TAG, "onNext: "+s);
                        if (s.isState()) {
                            huanweiCheckDetailEntity = s.getRtn();
                            tv_content.setText(huanweiCheckDetailEntity.getJCNR());
                            tv_describe.setText(huanweiCheckDetailEntity.getQKMS());
                            tv_name.setText(huanweiCheckDetailEntity.getOpername());
                            tv_position.setText(huanweiCheckDetailEntity.getJCDD());
                            tv_time.setText(huanweiCheckDetailEntity.getStarttime());
                            tv_project.setText(huanweiCheckDetailEntity.getXM());
                            tv_commit_town.setText(huanweiCheckDetailEntity.getChecktown());
                            tv_score.setText(huanweiCheckDetailEntity.getDeScore()+"");
                            if (huanweiCheckDetailEntity.getImg() != null) {
                                for (int i = 0; i < huanweiCheckDetailEntity.getImg().size(); i++) {
                                    list_img.add(huanweiCheckDetailEntity.getImg().get(i).getImg());
                                }
                                adapter.notifyDataSetChanged();
                                setRecyclerViewHeight(list_img.size());
                            }

//                            list_img.addAll(huanweiCheckDetailEntity.getImg());
                            if (s.getRtn().town.equals("无") || s.getRtn().town.equals("")) {
                                needChoseTown = true;
                                towns.addAll(s.getRtn().getTown());
                                for (int i = 0; i < towns.size(); i++) {
                                    list_town.add(towns.get(i).getTown());
                                }
                                loadPPw();
                            } else {
                                needChoseTown = false;
                                tv_town.setText(s.getRtn().town);
                                townId = s.getRtn().townid;
                            }
                        } else {
                            showNetErrorToast();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_huanwei_check_detail_town:
                if (needChoseTown) {

                    ppw.showAtLocation(ll_main, Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.tv_huanwei_check_detail_chexiao:
                commit(8);
                break;
            case R.id.tv_huanwei_check_detail_pass:
                if (huanweiCheckDetailEntity.getShstate().equals("1")) {
                    commit(1);
                }
                if (huanweiCheckDetailEntity.getShstate().equals("3")) {
                    commit(6);
                }
                break;
            case R.id.tv_huanwei_check_detail_back:
                commit(9);
                break;
            case R.id.tv_huanwei_check_detail_nopass:
                if (huanweiCheckDetailEntity.getShstate().equals("3")) {
                    commit(1);
                }
                break;
        }
    }

    private void commit(int state) {
        Log.i(TAG, "commit: ");
        if (!townId.equals("")) {

            if (huanweiCheckDetailEntity != null) {
                RetrofitClient.createService(HuanweiAPI.class)
                        .httpCheckCommit(huanweiCheckDetailEntity.getHoperid()
                                , townId
                                , huanweiCheckDetailEntity.getID(),
                                state)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new MySubscriber<BaseEntity>(mContext) {
                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                Log.i(TAG, "onError: " + responeThrowable);
                                showNetErrorToast();
                            }

                            @Override
                            public void onNext(BaseEntity baseEntity) {
                                Log.i(TAG, "onNext: " + gson.toJson(baseEntity));
                                if (baseEntity.isState()) {
                                    ToastUtils.showToast(mContext, "成功");
                                    finish();
                                } else {
                                    ToastUtils.showToast(mContext, baseEntity.getErrorMsg());
                                }
                            }
                        });
            }
        } else {
            ToastUtils.showToast(mContext, "请选择辖区");
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

    private void loadPPw() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ppw_project, null);
        ppw = new PopupWindow(view, Dp2pxUtil.dip2px(mContext, 200), LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) view.findViewById(R.id.lv_ppw_project);
//        list_town = new ArrayList<>();
        stringAdapter = new StringAdapter(list_town, mContext);
        lv.setAdapter(stringAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                townName = list_town.get(i);
                tv_town.setText(townName);
                townId = towns.get(i).getTownid();
                ppw.dismiss();
            }
        });
        ppw.setFocusable(true);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new BitmapDrawable());

//        loadProjectData();
    }
}
