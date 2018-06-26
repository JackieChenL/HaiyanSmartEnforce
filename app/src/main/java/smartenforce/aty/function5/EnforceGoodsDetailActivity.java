package smartenforce.aty.function5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.aty.ViewPhotoActivity;
import smartenforce.base.HttpApi;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.EnforceGoodsBean;

public class EnforceGoodsDetailActivity extends ShowTitleActivity {
    private TextView tev_GoodsClassifyIDWit, tev_NameWit, tev_CountWit, tev_ModelWit, tev_UnitWit, tev_RemarkWit;
    private ImageView imv_goods;
    private EnforceGoodsBean enforceGoodsBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
    }

    @Override
    protected void findViews() {
        tev_GoodsClassifyIDWit = (TextView) findViewById(R.id.tev_GoodsClassifyIDWit);
        tev_NameWit = (TextView) findViewById(R.id.tev_NameWit);
        tev_CountWit = (TextView) findViewById(R.id.tev_CountWit);
        tev_ModelWit = (TextView) findViewById(R.id.tev_ModelWit);
        tev_UnitWit = (TextView) findViewById(R.id.tev_UnitWit);
        tev_RemarkWit = (TextView) findViewById(R.id.tev_RemarkWit);
        imv_goods = (ImageView) findViewById(R.id.imv_goods);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("物品详情");
        enforceGoodsBean = (EnforceGoodsBean) getIntent().getSerializableExtra("EnforceGoodsBean");
        tev_GoodsClassifyIDWit.setText(enforceGoodsBean.NameGoC);
        tev_NameWit.setText(enforceGoodsBean.NameWit);
        tev_CountWit.setText(enforceGoodsBean.CountWit+"");
        tev_ModelWit.setText(enforceGoodsBean.ModelWit);
        tev_UnitWit.setText(enforceGoodsBean.UnitWit);
        tev_RemarkWit.setText(enforceGoodsBean.RemarkWit);
        String uploadImg = enforceGoodsBean.GoodsPicWit;
        if (!isEmpty(uploadImg)) {
            final String url_1=uploadImg.split("\\|")[0];
            Glide.with(aty).load(HttpApi.URL_IMG_HEADER +url_1 ).into(imv_goods);
            imv_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(aty, ViewPhotoActivity.class).putExtra("Url", url_1));
                }
            });
        }
    }


}



