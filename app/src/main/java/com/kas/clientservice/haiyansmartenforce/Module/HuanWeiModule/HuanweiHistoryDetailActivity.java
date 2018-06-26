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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.HuanweiAPI;
import com.kas.clientservice.haiyansmartenforce.API.TownsAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Base.StringAdapter;
import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiCheckDetailEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.TownEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
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
    @BindView(R.id.et_huanwei_handle_history_detail_describe)
    EditText tv_describe;
    @BindView(R.id.et_huanwei_handle_history_detail_name)
    EditText tv_name;
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
    @BindView(R.id.tv_header_right)
    TextView tv_edit;
    @BindView(R.id.tv_huanwei_history_commit)
    TextView tv_commit;
    @BindView(R.id.tv_huanwei_history_save)
    TextView tv_save;
    @BindView(R.id.tv_huanwei_handle_history_detail_commit_town)
    TextView tv_commit_town;
    @BindView(R.id.et_huanwei_handle_history_detail_score)
    EditText et_score;
    @BindView(R.id.ll_huanwei_history_btn)
    LinearLayout ll_btn;
    @BindView(R.id.ll_huanwei_check_history_detail)
    LinearLayout ll_main;

    boolean isEditMode = false;
    String id = "";
    List<HuanweiAPI.HistoryDetail_checkEntity.BoardBean> list = new ArrayList<>();
    List<HuanweiCheckDetailEntity.TownBean> towns = new ArrayList<>();
    HuanweiHistoryDetailAdapter huanweiHistoryDetailAdapter;

    StringAdapter stringAdapter;
    List<String> list_town = new ArrayList<>();
    List<TownEntity.TownBean> towns_commit = new ArrayList<>();
    String townId;
    String townName;
    List<HuanweiAPI.HuanweiProjectEntity> list_project = new ArrayList<>();
    List<HuanweiAPI.HuanweiContentEntity> list_content = new ArrayList<>();
    PopupWindow ppw;
    HuanweiProjectAdapter huanweiProjectAdapter;
    String projectName = "";
    String projectId = "";
    String contentName = "";
    String contentId = "";
    String jcddzz =  "";
    PopupWindow ppw_content;
    HuanweiContentAdapter huanweiContentAdapter;
    private String caseId;

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
        tv_name.setEnabled(false);
        tv_edit.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        tv_commit_town.setOnClickListener(this);
        tv_project.setOnClickListener(this);
        tv_content.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        id = getIntent().getStringExtra("id");
        initList();
        if (UserSingleton.USERINFO.getSWYTType().equals("6")) {
            loadCommitPeople();
            loadTownPPW();
            loadPPw();
            loadContentPPW();
        }
        if (UserSingleton.USERINFO.getSWYTType().equals("5")) {
            ll_name.setVisibility(View.GONE);
            loadCheckPeople();
        }
        if (UserSingleton.USERINFO.getSWYTType().equals("7")) {

            loadHandlePeople();
        }
    }

    PopupWindow ppw_town;

    private void loadTownPPW() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ppw_project, null);
        ppw_town = new PopupWindow(view, Dp2pxUtil.dip2px(mContext, 200), LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) view.findViewById(R.id.lv_ppw_project);
//        list_town = new ArrayList<>();
        stringAdapter = new StringAdapter(list_town, mContext);
        lv.setAdapter(stringAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                townName = list_town.get(i);
                tv_commit_town.setText(townName);
                townId = towns_commit.get(i).getTownid();
                ppw_town.dismiss();
            }
        });
        ppw_town.setFocusable(true);
        ppw_town.setOutsideTouchable(true);
        ppw_town.setBackgroundDrawable(new BitmapDrawable());

        loadTowns();
    }

    private void loadTowns() {
        RetrofitClient.createService(TownsAPI.class)
                .httpgetTown()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<TownEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<TownEntity> townEntityBaseEntity) {
                        if (townEntityBaseEntity.isState()) {
                            towns_commit.clear();
                            towns_commit.addAll(townEntityBaseEntity.getRtn().getTown());
                            for (int i = 0; i < towns_commit.size(); i++) {
                                list_town.add(towns_commit.get(i).getTown());

                            }
                            stringAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void loadContentPPW() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.ppw_project, null);
        ppw_content = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) view.findViewById(R.id.lv_ppw_project);
        huanweiContentAdapter = new HuanweiContentAdapter(list_content, mContext);
        lv.setAdapter(huanweiContentAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                contentName = list_content.get(i).jcnr;
                contentId = list_content.get(i).jcnrid;
                tv_content.setText(contentName);
                ppw_content.dismiss();
            }
        });
        ppw_content.setFocusable(true);
        ppw_content.setOutsideTouchable(true);
        ppw_content.setBackgroundDrawable(new BitmapDrawable());


    }

    private void loadPPw() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ppw_project, null);
        ppw = new PopupWindow(view, Dp2pxUtil.dip2px(mContext, 200), LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) view.findViewById(R.id.lv_ppw_project);
        list_project = new ArrayList<>();
        huanweiProjectAdapter = new HuanweiProjectAdapter(list_project, mContext);
        lv.setAdapter(huanweiProjectAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                projectName = list_project.get(i).xm;
                projectId = list_project.get(i).xmid;
                tv_project.setText(projectName);
                ppw.dismiss();
            }
        });
        ppw.setFocusable(true);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new BitmapDrawable());

        loadProjectData();
    }

    private void loadProjectData() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpHuanweiProject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiAPI.HuanweiProjectEntity[]>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiAPI.HuanweiProjectEntity[]> s) {
                        list.clear();
                        huanweiProjectAdapter.notifyDataSetChanged();
                        Collections.addAll(list_project, s.getRtn());
                        Log.i(TAG, "onNext: " + list.size());
                        huanweiProjectAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadContentData() {
        list_content.clear();
        huanweiContentAdapter.notifyDataSetChanged();
        RetrofitClient.createService(HuanweiAPI.class)
                .httpHuanweiContent(projectId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiAPI.HuanweiContentEntity[]>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiAPI.HuanweiContentEntity[]> s) {

                        Collections.addAll(list_content, s.getRtn());
                        Log.i(TAG, "onNext: " + list_content.size());
                        huanweiContentAdapter.notifyDataSetChanged();
                        ppw_content.showAtLocation(ll_main,Gravity.CENTER,0,0);
                    }
                });
    }

    private void loadHandlePeople() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpGetHandlePeopleHistoryDetai(UserSingleton.USERINFO.getChangeNameID(), id)
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
                            tv_name.setText(entity.jcryname);
                            tv_content.setText(entity.JCNR);
                            tv_project.setText(entity.XM);
                            tv_position.setText(entity.JCDD);
                            tv_describe.setText(entity.QKMS);
                            tv_time.setText(entity.starttime);
                            et_score.setText(entity.DeScore);
                            tv_commit_town.setText(entity.Checktown);
                            list_img.clear();
                            if (entity.Img != null && entity.Img.size() != 0) {
                                for (int i = 0; i < entity.Img.size(); i++) {
                                    list_img.add(entity.Img.get(i).img);
                                }
                                adapter.notifyDataSetChanged();
                                setRecyclerViewHeight(list_img.size());
                            } else {
                                recyclerView.setVisibility(View.GONE);
                            }
                            if (entity.board != null) {

                                list.addAll(entity.board);
                                huanweiHistoryDetailAdapter = new HuanweiHistoryDetailAdapter(list, mContext);
                                listView.setAdapter(huanweiHistoryDetailAdapter);
                                int height = list.size() * 135;
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
                                listView.setLayoutParams(params);
                                listView.setVerticalScrollBarEnabled(false);
                            }

                        } else {
                            ToastUtils.showToast(mContext, historyDetail_checkEntityBaseEntity.getErrorMsg());
                        }
                    }
                });
    }

    private void loadCheckPeople() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpGetCheckPeopleHistoryDetai(UserSingleton.USERINFO.getReviewNameID(), id)
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
                            tv_name.setText(entity.jcryname);
                            tv_content.setText(entity.JCNR);
                            tv_project.setText(entity.XM);
                            tv_position.setText(entity.JCDD);
                            tv_describe.setText(entity.QKMS);
                            tv_time.setText(entity.starttime);
                            et_score.setText(entity.DeScore);
                            tv_commit_town.setText(entity.Checktown);
                            list_img.clear();
                            if (entity.Img != null && entity.Img.size() != 0) {
                                for (int i = 0; i < entity.Img.size(); i++) {
                                    list_img.add(entity.Img.get(i).img);
                                }
                                adapter.notifyDataSetChanged();
                                setRecyclerViewHeight(list_img.size());
                            } else {
                                recyclerView.setVisibility(View.GONE);
                            }
                            if (entity.board != null) {

                                list.addAll(entity.board);
                                huanweiHistoryDetailAdapter = new HuanweiHistoryDetailAdapter(list, mContext);
                                listView.setAdapter(huanweiHistoryDetailAdapter);
                                int height = list.size() * 135;
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
                                listView.setLayoutParams(params);
                                listView.setVerticalScrollBarEnabled(false);
                            }

                        } else {
                            ToastUtils.showToast(mContext, historyDetail_checkEntityBaseEntity.getErrorMsg());
                        }
                    }
                });
    }

    private void loadCommitPeople() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpGetCommitPeopleHistoryDetai(UserSingleton.USERINFO.getCheckNameID(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiAPI.HistoryDetail_commitEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable);
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
                            et_score.setText(entity.DeScore);
                            jcddzz =  entity.JCDDZB;
                            caseId = entity.ID;
                            contentId = entity.JCNRID;
                            tv_commit_town.setText(entity.Checktown);
                            if (entity.Shstate.equals("0")) {
                                tv_edit.setVisibility(View.VISIBLE);
                                tv_edit.setText("编辑");
                            }

                            list_img.clear();
                            if (entity.Img != null) {
                                for (int i = 0; i < entity.Img.size(); i++) {
                                    list_img.add(entity.Img.get(i).getImg());
                                }
                                adapter.notifyDataSetChanged();
                                setRecyclerViewHeight(list_img.size());
                            }
                        } else {
                            ToastUtils.showToast(mContext, historyDetail_commitEntityBaseEntity.getErrorMsg());
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
            case R.id.tv_header_right:
                if (!isEditMode) {
                    tv_edit.setText("取消");
                    tv_commit.setVisibility(View.VISIBLE);


                } else {
                    tv_edit.setText("编辑");
                    tv_commit.setVisibility(View.VISIBLE);
                    loadCommitPeople();
                }
                setEditable(!isEditMode);
                isEditMode = !isEditMode;
                Log.i(TAG, "onClick: " + isEditMode);
                break;
            case R.id.tv_huanwei_history_commit:
                commit(1);
                break;
            case R.id.tv_huanwei_history_save:
                commit(0);
                break;
            case R.id.tv_huanwei_handle_history_detail_commit_town:
                ppw_town.showAtLocation(ll_main, Gravity.CENTER,0,0);
                break;
            case R.id.tv_huanwei_handle_history_detail_project:
                ppw.showAtLocation(ll_main,Gravity.CENTER,0,0);
                break;
            case R.id.tv_huanwei_handle_history_detail_content:
                loadContentData();
                ;
                break;
        }
    }

    private void setEditable(boolean b) {
        tv_name.setEnabled(b);
        tv_project.setClickable(b);
        tv_content.setClickable(b);
        tv_commit_town.setClickable(b);
        et_score.setEnabled(b);
        tv_position.setEnabled(b);
        tv_describe.setEnabled(b);
        if (b) {

            ll_btn.setVisibility(View.VISIBLE);
        }else {
            ll_btn.setVisibility(View.GONE);
        }
    }

    private void commit(int i) {
//        Log.i(TAG, "commit: " + BitmapToBase64.bitmapListToBase64(arr_image));
        RetrofitClient.createService(HuanweiAPI.class)
                .httpHuanweiReCommit(UserSingleton.USERINFO.getCheckNameID(),
                        caseId,
                        contentId,
                        tv_position.getText().toString(),
                        jcddzz,
                        tv_describe.getText().toString(),
                        "enterprise",
                        townId,
                        et_score.getText().toString(),
                        i+"",
                        "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        ToastUtils.showToast(mContext, "网络错误");
                    }

                    @Override
                    public void onNext(BaseEntity s) {
                        if (s.isState()) {
                            ToastUtils.showToast(mContext, "提交成功");
                            finish();
                        } else {
                            ToastUtils.showToast(mContext, s.getErrorMsg());
                        }
                    }
                });
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
        int height = ((size / 2) + 1) * 130 + 10;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
//        layoutParams.setMargins(0, Dp2pxUtil.dip2px(mContext, 5), 0, Dp2pxUtil.dip2px(mContext, 50));
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }
}
