package com.kas.clientservice.haiyansmartenforce.Module.News;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.VerticalBannerEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class AdvDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.tv_advDetail_title)
    TextView tv_content_title;
    @BindView(R.id.tv_advDetail_name)
    TextView tv_name;
    @BindView(R.id.tv_advDetail_time)
    TextView tv_time;
    @BindView(R.id.tv_advDetai_content)
    TextView tv_content;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.lv_advDetail)
    ListView listView;

    String id = "";
    int type = 0;
    List<String> list = new ArrayList<>();
    NewsImgAdapter adapter;
    //    List<BannerAdvertisementEntity.RtnBean> list_adv = new ArrayList<>();
    List<VerticalBannerEntity.RtnBean> list_vertical = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adv_detail;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("详情");
        iv_back.setOnClickListener(this);
        listView.setVerticalScrollBarEnabled(false);
        initAdapter();
        type = getIntent().getIntExtra("type", 0);
        id = getIntent().getStringExtra("id");

        loadNews();
//        if (type == 1) {
//            loadAdv();
//        } else if (type == 2) {
//
//        }

    }

    private void initAdapter() {
        adapter = new NewsImgAdapter(list, mContext);
        listView.setAdapter(adapter);

    }

    private void loadNews() {
        OkHttpUtils.get().url(RequestUrl.baseUrl_leader + "mobile/GetNewsList.ashx")
                .addParams("id", id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: " + response);
                VerticalBannerEntity bean = gson.fromJson(response, VerticalBannerEntity.class);
                if (bean.isState() == true) {
                    list_vertical.clear();
                    list_vertical.addAll(bean.getRtn());
                    VerticalBannerEntity.RtnBean entity = list_vertical.get(0);
                    list.clear();

                    String pic = entity.getBroadcastPicture()+entity.getTextPicture();
//                    if (pic.endsWith("\\|")) {
//                        pic.substring(0,pic.length());
//                    }
                    String[] array = pic.split("\\|");
                    for (int i = 0; i < array.length; i++) {
                        if (!array[i].trim().equals("")) {
                            Log.i(TAG, "onResponse: "+RequestUrl.baseUrl_img+array[i]);
                            list.add(RequestUrl.baseUrl_img+array[i]);
                        }
                    }

                    adapter.notifyDataSetChanged();
                    ListViewFitParent.setListViewHeightBasedOnChildren(listView);
                    tv_content_title.setText(entity.getTitle());
                    tv_content.setText(entity.getText());
                    tv_name.setText("来源：" + entity.getNameEmp());
                    tv_time.setText("时间：" + entity.getCreateTime());


                }
            }
        });
    }

//    private void loadAdv() {
//        OkHttpUtils.get().url(RetrofitClient.mBaseUrl + "mobile/GetNewsBroadcastPictureList.ashx")
//                .addParams("id", id)
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Log.i(TAG, "onError: " + e.toString());
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Log.i(TAG, "onResponse: " + response);
//                VerticalBannerEntity bean = gson.fromJson(response, VerticalBannerEntity.class);
//                if (bean.getState().equals("true")) {
//                    list_vertical.clear();
//                    list_vertical.addAll(bean.getRtn());
//                    VerticalBannerEntity.RtnBean entity = list_vertical.get(0);
//                    if (entity.getContentImg() != null && entity.getContentImg().size() > 0) {
//                        list.clear();
//                        for (int i = 0; i < entity.getContentImg().size(); i++) {
//                            list.add(entity.getContentImg().get(i).getImg());
//                        }
//                        adapter.notifyDataSetChanged();
//                        ListViewFitParent.setListViewHeightBasedOnChildren(listView);
//                    }
//                    tv_content_title.setText(entity.getTitle());
//                    tv_content.setText(entity.getContent());
//                    tv_name.setText("来源：" + entity.getOperrealname());
//                    tv_time.setText("时间：" + entity.getAddtime());
//
//
//                }
//            }
//        });
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
        }
    }
}
