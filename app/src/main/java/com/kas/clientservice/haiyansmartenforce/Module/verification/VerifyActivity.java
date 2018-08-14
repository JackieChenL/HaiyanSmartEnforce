package com.kas.clientservice.haiyansmartenforce.Module.verification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.MyStringCallBack;


public class VerifyActivity extends ShowTitleActivity {


    private RecyclerView rcv_list;
    private VerifyAdapter adapter;
    private ArrayList<VerifyBean> list=new ArrayList<>();
    private int currentPostion=-1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_list);
    }

    @Override
    protected void findViews() {
        rcv_list=(RecyclerView) findViewById(R.id.rcv_list);
        rcv_list.setLayoutManager(new LinearLayoutManager(this));
        rcv_list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("任务列表");
        doGetListAction();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if (resultCode==RESULT_OK&&requestCode==100&&currentPostion!=-1){
          list.remove(currentPostion);
          adapter.notifyDataSetChanged();
      }

    }

    @Override
    protected void onDestroy() {
        changeState();
        super.onDestroy();
    }

  private void changeState(){
      if (list.size()>0){
        for(int i=0;i<list.size();i++){
           VerifyBean verifyBean=list.get(i);
            OkHttpUtils.post()
                    .url(IpUtils.URL_HOST)
                    .addParams("optionName", "pdamodifypdamsg")
                    .addParams("projcode", verifyBean.getProjcode())
                    .addParams("collcode", "1628")
                    .addParams("msgcontent", "已完成")
                    .addParams("ePicArry","")
                    .build().execute(new MyStringCallBack() {
            });

        }

      }


  }




    private void doGetListAction() {
        OkHttpUtils.post().url(IpUtils.URL_HOST)
                .addParams("collcode", "1628")
                .addParams("optionName","pdagetpdamsg")
                .build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                 if (bean.State){
                     list.clear();
                     list.addAll(bean.getResultBeanList(VerifyBean.class));
                  if (list.size()==0){
                        show("暂无任务");
//                       list.add(new VerifyBean());
                  }
                      if (adapter==null){
                          adapter=new VerifyAdapter(list);
                          rcv_list.setAdapter(adapter);
                      }else{
                          adapter.notifyDataSetChanged();
                      }

                 }else{
                     warningShow("查询失败");
                 }


            }
        });

    }



    private class VerifyAdapter extends RecyclerView.Adapter<VerifyActivity.ViewHolder> {

        private LayoutInflater inflater;
        private List<VerifyBean> list;
        public VerifyAdapter(List<VerifyBean> list) {
            this.list = list;
            inflater = LayoutInflater.from(aty);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_verify_adapter, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
           final  VerifyBean verifyBean=list.get(position);
            holder.imv_status.setBackgroundResource(verifyBean.getState().equals("0")?R.drawable.status_unhandle:R.drawable.status_handled);
            holder.tev_title.setText("绍兴数管[2018]第"+verifyBean.getProjcode()+"号");
            holder.tev_time.setText(verifyBean.getCu_date());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     currentPostion=position;
                    startActivityForResult(new Intent(aty,VerifyDetailActivity.class).putExtra("VerifyBean",verifyBean),100);
                }
            });
        }





        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }




    }

    private class ViewHolder extends RecyclerView.ViewHolder {
          private ImageView imv_status;
          private TextView tev_title,tev_time;

        public ViewHolder(View itemView) {
            super(itemView);
            imv_status=(ImageView)itemView.findViewById(R.id.imv_status);
            tev_title=(TextView)itemView.findViewById(R.id.tev_title);
            tev_time=(TextView)itemView.findViewById(R.id.tev_time);

        }
    }
}
