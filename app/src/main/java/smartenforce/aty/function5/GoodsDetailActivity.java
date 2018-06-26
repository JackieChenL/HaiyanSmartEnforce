package smartenforce.aty.function5;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.aty.ViewPhotoActivity;
import smartenforce.base.BaseBean;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.GoodListBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;

public class GoodsDetailActivity extends ShowTitleActivity {
    private TextView tev_GoodsClassifyIDWit, tev_NameWit, tev_CountWit, tev_ModelWit, tev_UnitWit, tev_RemarkWit;
    private TextView tev_sqlx, tev_address, tev_status;
    private ImageView imv_goods;
    private LinearLayout llt_action_footer,llt_nomal,llt_release;
    private TextView tev_repeating, tev_processing, tev_destroy;
    private GoodListBean goodListBean;
    private String state = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_action);
    }

    @Override
    protected void findViews() {
        tev_GoodsClassifyIDWit = (TextView) findViewById(R.id.tev_GoodsClassifyIDWit);
        tev_NameWit = (TextView) findViewById(R.id.tev_NameWit);
        tev_CountWit = (TextView) findViewById(R.id.tev_CountWit);
        tev_ModelWit = (TextView) findViewById(R.id.tev_ModelWit);
        tev_UnitWit = (TextView) findViewById(R.id.tev_UnitWit);
        tev_RemarkWit = (TextView) findViewById(R.id.tev_RemarkWit);
        tev_sqlx = (TextView) findViewById(R.id.tev_sqlx);
        tev_address = (TextView) findViewById(R.id.tev_address);
        tev_status = (TextView) findViewById(R.id.tev_status);
        imv_goods = (ImageView) findViewById(R.id.imv_goods);

        llt_action_footer = (LinearLayout) findViewById(R.id.llt_action_footer);
        llt_nomal = (LinearLayout) findViewById(R.id.llt_nomal);
        llt_release = (LinearLayout) findViewById(R.id.llt_release);
        tev_repeating = (TextView) findViewById(R.id.tev_repeating);
        tev_processing = (TextView) findViewById(R.id.tev_processing);
        tev_destroy = (TextView) findViewById(R.id.tev_destroy);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("物品详情");
        goodListBean = (GoodListBean) getIntent().getSerializableExtra("GoodListBean");
        tev_GoodsClassifyIDWit.setText(goodListBean.NameGoC);
        tev_sqlx.setText(goodListBean.NameIAT);
        tev_address.setText(goodListBean.NameWar);
        tev_status.setText(goodListBean.NameCuS);
        tev_NameWit.setText(goodListBean.NameWit);
        tev_CountWit.setText(goodListBean.CountWit);
        tev_ModelWit.setText(goodListBean.ModelWit);
        tev_UnitWit.setText(goodListBean.UnitWit);
        tev_RemarkWit.setText(goodListBean.RemarkWit);
        tev_repeating.setOnClickListener(noFastClickLisener);
        tev_processing.setOnClickListener(noFastClickLisener);
        tev_destroy.setOnClickListener(noFastClickLisener);
        String status = goodListBean.NameCuS;
        if (status.equals("待处理")) {

        } else if (status.equals("待解除")){
            llt_nomal.setVisibility(View.GONE);
            llt_release.setVisibility(View.VISIBLE);
            llt_release.setOnClickListener(noFastClickLisener);
        }else if (status.equals("已出库")){
            tev_repeating.setEnabled(false);
            tev_repeating.setBackgroundColor(Color.parseColor("#c7c7c7"));
        } else {
            llt_action_footer.setVisibility(View.GONE);
        }
        String uploadImg = goodListBean.GoodsPicWit;
        if (!isEmpty(uploadImg)) {
            final String url_1 = uploadImg.split("\\|")[0];
            Glide.with(aty).load(HttpApi.URL_IMG_HEADER + url_1).into(imv_goods);
            imv_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(aty, ViewPhotoActivity.class).putExtra("Url", url_1));

                }
            });
        }
    }


    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            switch (v.getId()) {
                case R.id.tev_repeating:
                    state = "入库";
                    break;
                case R.id.tev_processing:
                    state = "工艺处理";
                    break;
                case R.id.tev_destroy:
                    state = "销毁";
                    break;
                case R.id.llt_release:
                    state = "解除扣押";
                    break;
            }

            doHandleWithGoods();

        }
    };


    private void doHandleWithGoods() {
        GoodsHandlerBean goodsHandlerBean = new GoodsHandlerBean();
        goodsHandlerBean.UserID = app.userID;
        goodsHandlerBean.WithholdGoodsList.add(new WithholdGoodsListBean(goodListBean.WithholdGoodsID, state));
        String HandlePostData= JSON.toJSONString(goodsHandlerBean);
        OkHttpUtils.post().url(HttpApi.URL_WITHHOLDGOODSHANDLE)
                .addParams("HandlePostData", HandlePostData)
                .build().execute(new BeanCallBack(aty, "提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("提交成功");
                    finish();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });

    }


    public class GoodsHandlerBean extends BaseBean {


        /**
         * UserID : 1
         * WithholdGoodsList : [{"id":18,"state":"解除扣押"}]
         */

        public String UserID;
        public List<WithholdGoodsListBean> WithholdGoodsList = new ArrayList<>();


    }

    public class WithholdGoodsListBean {
        /**
         * id : 18
         * state : 解除扣押
         */

        public int id;
        public String state;

        public WithholdGoodsListBean(int id, String state) {
            this.id = id;
            this.state = state;
        }
    }


}




















