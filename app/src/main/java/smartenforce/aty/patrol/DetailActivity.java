package smartenforce.aty.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.NetResultBean;
import smartenforce.bean.tcsf.PatrolDetailBean;

import com.zhy.http.okhttp.OkHttpUtils;

import smartenforce.base.HttpApi;
import smartenforce.base.ShowTitleActivity;
import smartenforce.impl.BeanCallBack;
import smartenforce.widget.FullyGridLayoutManager;


public class DetailActivity extends ShowTitleActivity implements View.OnClickListener{
    private TextView tev_cphm, tev_trsj, tev_pwbh, tev_lksj;
    private RecyclerView rv;
    private ShowImageAdapter adapter;

    private TextView tev_left,tev_right;
    private LinearLayout llt_footer;
    private PatrolDetailBean patrolDetailBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_detail);

    }

    @Override
    protected void findViews() {
        tev_cphm = (TextView) findViewById(R.id.tev_cphm);
        tev_trsj = (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh = (TextView) findViewById(R.id.tev_pwbh);
        tev_lksj = (TextView) findViewById(R.id.tev_lksj);
        tev_left = (TextView) findViewById(R.id.tev_left);
        tev_right = (TextView) findViewById(R.id.tev_right);
        llt_footer = (LinearLayout) findViewById(R.id.llt_footer);
        rv = (RecyclerView) findViewById(R.id.rv);
        FullyGridLayoutManager manager=new FullyGridLayoutManager(aty,2);
        rv.setLayoutManager(manager);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("泊位详情");
        tev_left.setText("督查上报");
        tev_right.setText("返回");
        tev_left.setOnClickListener(this);
        tev_right.setOnClickListener(this);
        queryDetail();
    }




    public void queryDetail() {
        String btid = getIntent().getStringExtra("btid");
        OkHttpUtils.post().url(HttpApi.URL_BERTHDETAIL)
                .addParams("Btid", btid)
                .build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total == 1) {
                     patrolDetailBean = bean.getResultBeanList(PatrolDetailBean.class).get(0);
                    fillContent(patrolDetailBean);
                } else {
                    warningShow("获取泊位详情失败");
                    llt_footer.setVisibility(View.GONE);
                }
            }
        });

    }



    private void fillContent(PatrolDetailBean patrolDetailBean) {
        if (patrolDetailBean == null) {
            warningShow("获取泊位详情失败");
            llt_footer.setVisibility(View.GONE);
        } else {
            tev_cphm.setText(patrolDetailBean.UCarnum);
            tev_trsj.setText(patrolDetailBean.StartTime);
            tev_pwbh.setText(patrolDetailBean.BerthName);
            tev_lksj.setText("暂未离开");
            adapter=new ShowImageAdapter(patrolDetailBean.Imglist,aty);
            rv.setAdapter(adapter);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tev_left:
               startActivity(new Intent(aty,UploadActivity.class).putExtra("PatrolDetailBean",patrolDetailBean));
                break;
            case  R.id.tev_right:
                finish();
                break;


        }



    }
}
