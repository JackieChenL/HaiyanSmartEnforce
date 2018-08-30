package com.kas.clientservice.haiyansmartenforce.Module.DocumentSearch;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.StringAdapter;
import com.kas.clientservice.haiyansmartenforce.Entity.DocDetailEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.FileOpenUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class DocDetaiActivity extends BaseActivity {
    @BindView(R.id.tv_docDetail_ContentOff)
    TextView tv_contentOff;
    @BindView(R.id.tv_docDetail_EntryTimeOff)
    TextView tv_time;
    @BindView(R.id.tv_docDetail_NumberOff)
    TextView tv_id;
    @BindView(R.id.tv_docDetail_ReferenceNumberOff)
    TextView tv_ref;
    @BindView(R.id.tv_docDetail_RemarkOff)
    TextView tv_remark;
    @BindView(R.id.tv_docDetail_TitleOff)
    TextView tv_tit;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.lv_docDetail_upload)
    ListView listView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_doc_detai;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("公文详情");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadData();
    }

    private void loadData() {
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"mobileapi/staff/OfficeDetail.ashx")
                .addParams("UserID","1259")
                .addParams("OfficeID",getIntent().getIntExtra("id",0)+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                showNetErrorToast();
            }

            @Override
            public void onResponse(String response, int id) {
                DocDetailEntity entity = gson.fromJson(response,DocDetailEntity.class);
                DocDetailEntity.RtnBean rtnBean = entity.getRtn().get(0);
                if (rtnBean!=null) {
                    tv_id.setText(rtnBean.getNumberOff()+"");
                    tv_tit.setText(rtnBean.getTitleOff());
                    tv_contentOff.setText(rtnBean.getContentOff());
                    tv_time.setText(rtnBean.getEntryTimeOff());
                    tv_remark.setText(rtnBean.getRemarkOff());
                    tv_ref.setText(rtnBean.getReferenceNumberOff());

                    if (!rtnBean.getUploadOff().equals("")) {
                        String s = rtnBean.getUploadOff().trim().substring(0,rtnBean.getUploadOff().length()-1);
                        Log.i(TAG, "onResponse: "+s);
                        final String[] upload = rtnBean.getUploadOff().split("\\|");
                        final List<String> list = new ArrayList<String>();
                        Collections.addAll(list,upload);
                        for (int i = 0; i < list.size(); i++) {
                            String tem = list.get(i);
                            list.set(i,tem.substring(tem.lastIndexOf("/")+1));
                        }

                        StringAdapter adapter = new StringAdapter(list,mContext);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Intent intent= new Intent();
//                                intent.setAction("android.intent.action.VIEW");
//                                Uri content_url = Uri.parse(RequestUrl.baseUrl_img+upload[position]);
//                                intent.setData(content_url);
//                                startActivity(intent);
                                download(RequestUrl.baseUrl_img+upload[position],list.get(position));
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    private void download(String url,String fileName) {
        showLoadingDialog();
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(Environment.getExternalStorageDirectory() + "/download",fileName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissLoadingDialog();
                Log.i(TAG, "onError: "+e);
            }

            @Override
            public void onResponse(File response, int id) {
                dismissLoadingDialog();
                Log.i(TAG, "onResponse: "+response);
                FileOpenUtil.openFile(response,mContext);
            }
        });
    }
}
