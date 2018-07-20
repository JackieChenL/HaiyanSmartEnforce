package com.kas.clientservice.haiyansmartenforce.Module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jorge.circlelibrary.ImageCycleView;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.BannerAdvertisementEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.VerticalBannerEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.MainModuleRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Module.CaseCommit.CaseCommitActivity;
import com.kas.clientservice.haiyansmartenforce.Module.CaseCommit.CaseSearchActivity;
import com.kas.clientservice.haiyansmartenforce.Module.CauseSearch.Cause_Query;
import com.kas.clientservice.haiyansmartenforce.Module.CityCheck.CityCheckSearchActivity;
import com.kas.clientservice.haiyansmartenforce.Module.FaceCompare.FaceCompareActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Garbage.GarbageMainActivity;
import com.kas.clientservice.haiyansmartenforce.Module.History.HistoryActivity;
import com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule.HuanWeiEntryActivity;
import com.kas.clientservice.haiyansmartenforce.Module.HuochaiCredit.HuochaiCreditActivity;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.IllegalParkingCommitActivity;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ParkingRecordSearchActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Laws.LawsActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Leader.CaseClassify.Case_Classify;
import com.kas.clientservice.haiyansmartenforce.Module.Leader.DepartmentCaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision.Decision;
import com.kas.clientservice.haiyansmartenforce.Module.Leader.LeaderCheckCaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.News.AdvDetailActivity;
import com.kas.clientservice.haiyansmartenforce.Module.PersonalInfo.PersonalInfoActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Vedio.VedioListActivity;
import com.kas.clientservice.haiyansmartenforce.Module.XieTong.XieTongActivity;
import com.kas.clientservice.haiyansmartenforce.Module.XieTongList.XieTongList;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserInfo;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.AppParameter;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.UPMarqueeView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import smartenforce.aty.function1.NewQueryActivity;
import smartenforce.aty.function2.QueryListActivity;
import smartenforce.aty.function4.RecipientActivity;
import smartenforce.aty.noise_wellshutter.NoiseWellshutterActivity;
import smartenforce.aty.parking.CenterActivity;
import smartenforce.aty.patrol.SearchActivity;
import videotalk.UserVideoLoginActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainModuleRvAdapter.OnModuleClickListener {
    @BindView(R.id.ll_main_caseSearch)
    LinearLayout ll_caseSearch;
    @BindView(R.id.ll_main_quickCommit)
    LinearLayout ll_quickCommit;
    @BindView(R.id.cycleView_main)
    ImageCycleView imageCycleView;
    @BindView(R.id.ll_wentishangbao)
    LinearLayout ll_wentishangbao;
    @BindView(R.id.banner_main)
    UPMarqueeView upview1;
    @BindView(R.id.ll_lishijilu)
    LinearLayout ll_lishijilu;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.iv_main_search)
    ImageView iv_search;
    @BindView(R.id.llt_tcjf)
    LinearLayout llt_tcjf;
    @BindView(R.id.riv_main)
    ImageView iv_mine;


    ArrayList<String> list_detail;
    ArrayList<String> list_imageURL;
    List<String> list_headline = new ArrayList<>();
    List<UserInfo.UIBean> list_module;
    private List<View> views = new ArrayList<>();
    List<BannerAdvertisementEntity.RtnBean> list_adv = new ArrayList<>();
    List<VerticalBannerEntity.RtnBean> list_vertical = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        Log.i(TAG, "initResAndListener: " + AppParameter.getApplicationId(mContext));
        ll_caseSearch.setOnClickListener(this);
        ll_quickCommit.setOnClickListener(this);
        ll_wentishangbao.setOnClickListener(this);
        ll_lishijilu.setOnClickListener(this);
        llt_tcjf.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        iv_mine.setOnClickListener(this);
        loadBanner();
        loadVerticalBanner();
//        loadVerticalBanner();
        initVerticalBanner();
        initRv();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(mContext).load(UserSingleton.USERINFO.getPhoto()).asBitmap().error(R.drawable.loginhead).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv_mine.setImageBitmap(resource);
            }
        });
    }

    private void loadVerticalBanner() {
        OkHttpUtils.get().url(RequestUrl.baseUrl_leader + "mobile/GetNewsList.ashx")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: " + response);
                VerticalBannerEntity bean = gson.fromJson(response, VerticalBannerEntity.class);
                if (bean.isState()==true) {
                    list_vertical.clear();
                    list_vertical.addAll(bean.getRtn());
                    if (list_vertical != null && list_vertical.size() > 0) {
                        for (int i = 0; i < list_vertical.size(); i++) {
                            list_headline.add(list_vertical.get(i).getTitle());
                        }
                    }
                    initVerticalBanner();
                }
            }
        });
    }

    private void loadBanner() {
        list_detail = new ArrayList<>();
        list_imageURL = new ArrayList<>();
        OkHttpUtils.get().url(RequestUrl.baseUrl_leader + "mobile/GetNewsBroadcastPictureList.ashx")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                BannerAdvertisementEntity entity = gson.fromJson(response, BannerAdvertisementEntity.class);
                if (entity.getState().equals("true")) {
                    if (entity.getRtn() != null && entity.getRtn().size() > 0) {
                        list_adv.clear();
                        list_adv.addAll(entity.getRtn());
                        for (int i = 0; i < entity.getRtn().size(); i++) {
                            BannerAdvertisementEntity.RtnBean rtnBean = entity.getRtn().get(i);
                            list_detail.add(rtnBean.getID() + "");
                            list_imageURL.add(rtnBean.getLogoImg().replaceAll("\\|",""));
                        }
                        initBanner();
                    }
                }
            }
        });
    }

    private void initRv() {
        list_module = new ArrayList<>();
        MainModuleRvAdapter adapter = new MainModuleRvAdapter(list_module, mContext);
        adapter.setOnModuleClickListener(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 4, LinearLayout.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        list_module.clear();
        list_module.addAll(UserSingleton.USERINFO.getUI());
        adapter.notifyDataSetChanged();
        setRecyclerViewHeight(list_module.size());


    }

    public void setRecyclerViewHeight(int size) {
        int height;
        if (size % 4 == 0) {
            height = (size / 4) * 120 + 10;
        } else {
            height = (size / 4 + 1) * 120 + 10;
        }
        Log.i(TAG, "setRecyclerViewHeight: " + height);
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
        layoutParams.setMargins(Dp2pxUtil.dip2px(mContext, 10), 0, Dp2pxUtil.dip2px(mContext, 10), 0);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }


    private void initVerticalBanner() {
        setView();
        upview1.setViews(views);
    }

    private void initBanner() {

        imageCycleView.setCycle_T(ImageCycleView.CYCLE_T.CYCLE_VIEW_NORMAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, 120));
        layoutParams.setMargins(0, Dp2pxUtil.dip2px(mContext, 15), 0, 0);
        imageCycleView.setLayoutParams(layoutParams);
        ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
//                imageView.setImageResource(R.drawable.indexbananer);
//                Log.i(TAG, "displayImage: "+RequestUrl.baseUrl_img+imageURL);
                Glide.with(mContext).load(RequestUrl.baseUrl_img+imageURL).error(R.drawable.normal_pic).into(imageView);
            }

            @Override
            public void onImageClick(int position, View imageView) {
//                ToastUtils.showToast(mContext, "click");
                Intent intent = new Intent(mContext, AdvDetailActivity.class);
                intent.putExtra("id", list_adv.get(position).getID());
                intent.putExtra("type",1);
                startActivity(intent);
            }
        };
        imageCycleView.setImageResources(list_detail, list_imageURL, mAdCycleViewListener);
        // 是否隐藏底部
        imageCycleView.hideBottom(true);
        imageCycleView.startImageCycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_quickCommit:
                startActivity(new Intent(mContext, IllegalParkingCommitActivity.class));
                break;
            case R.id.ll_main_caseSearch:
                startActivity(new Intent(mContext, ParkingRecordSearchActivity.class));
                break;
            case R.id.ll_wentishangbao:
                startActivity(new Intent(mContext, CaseCommitActivity.class));
                break;
            case R.id.ll_lishijilu:
                startActivity(new Intent(mContext, HistoryActivity.class));
                break;
            case R.id.llt_tcjf:

                startActivity(new Intent(mContext, CenterActivity.class));
                break;
            case R.id.iv_main_search:
                startActivity(new Intent(mContext, FaceCompareActivity.class));
                break;
            case R.id.riv_main:
                startActivity(new Intent(mContext, PersonalInfoActivity.class));
                break;
        }
    }

    private void setView() {
        for (int i = 0; i < list_headline.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_main_banner, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv_item_main_banner);
            //进行对控件赋值
            tv1.setText(list_headline.get(i));

            /**
             * 设置头条的点击事件
             */
            final int finalI = i;
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AdvDetailActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("id", list_vertical.get(finalI).getID()+"");
                    startActivity(intent);
                }
            });
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    @Override
    public void onModuleClick(int type) {
        Log.i(TAG, "onModuleClick: " + type);
        Intent intent;
        switch (type) {
            case Constants.MainModule.TINGCHESHOUFEI:
                startActivity(new Intent(mContext, CenterActivity.class));

                break;
            case Constants.MainModule.WEIZHANGTINGCHE:
                startActivity(new Intent(mContext, IllegalParkingCommitActivity.class));
                break;
            case Constants.MainModule.ANJIANCHAXUN:
                startActivity(new Intent(mContext, ParkingRecordSearchActivity.class));
                break;
            case Constants.MainModule.GABAGE:
                startActivity(new Intent(mContext, GarbageMainActivity.class));
                break;
            case 5://四位一体
                intent = new Intent(mContext, HuanWeiEntryActivity.class);
//                intent.putExtra("TypeId",UserSingleton.USERINFO.getType());
                startActivity(intent);
                break;
            case 6://案件提交
                intent = new Intent(mContext, CaseCommitActivity.class);
                startActivity(intent);
                break;
            case 7://案件查询
                intent = new Intent(mContext, CaseSearchActivity.class);
                startActivity(intent);
                break;
            case 8:
                intent = new Intent(mContext, Case_Classify.class);
                startActivity(intent);
                break;
            case 9:
                intent = new Intent(mContext, Decision.class);
                startActivity(intent);
                break;
            case 10://部门案件
                intent = new Intent(mContext, DepartmentCaseActivity.class);
                startActivity(intent);
                break;
            case 13://法律法规
                intent = new Intent(mContext, LawsActivity.class);
                startActivity(intent);
                break;
            case 14://违法行为
                intent = new Intent(mContext, Cause_Query.class);
                startActivity(intent);
                break;
            case 15://案件审批
                intent = new Intent(mContext, LeaderCheckCaseActivity.class);
                startActivity(intent);
                break;
            case 18://专项政治
                startActivity(new Intent(mContext, XieTongActivity.class));
                break;
            case 19://
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
            case 20://火柴信用
                startActivity(new Intent(mContext, HuochaiCreditActivity.class));
                break;
            case 21://巡查发现
                startActivity(new Intent(mContext, NewQueryActivity.class));
                break;
            case 22://巡查查询
                startActivity(new Intent(mContext, QueryListActivity.class));
                break;
            case 23:
//                startActivity(new Intent(mContext, InvestActivity.class));
                break;
            case 24://服务对象
                startActivity(new Intent(mContext, RecipientActivity.class));
                break;
            case 25:
                startActivity(new Intent(mContext, XieTongList.class));
                break;
            case 30://市容督查
                startActivity(new Intent(mContext, CityCheckSearchActivity.class));
                break;
            case 31://视频监控
                startActivity(new Intent(mContext, VedioListActivity.class));
                break;
            case 100://视频会商
                startActivity(new Intent(mContext, UserVideoLoginActivity.class));
                break;
            case 101://噪声
                startActivity(new Intent(mContext, NoiseWellshutterActivity.class).putExtra("src","noise"));
                break;
            case 102://井盖
                startActivity(new Intent(mContext, NoiseWellshutterActivity.class).putExtra("src","cover"));
//                startActivity(new Intent(mContext, PersonRePayActivity.class));
                break;

        }
    }
}
