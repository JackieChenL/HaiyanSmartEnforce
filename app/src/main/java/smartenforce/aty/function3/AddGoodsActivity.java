package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;

import java.io.File;

import smartenforce.base.HttpApi;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.AddEnforceBean;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.projectutil.UpLoadImageUtil;
import smartenforce.util.ImgUtil;
import smartenforce.widget.ProgressDialogUtil;


public class AddGoodsActivity extends ShowTitleActivity {
    private TextView tev_GoodsClassifyIDWit;

    private EditText edt_NameWit, edt_CountWit, edt_ModelWit, edt_UnitWit, edt_RemarkWit;
    private ImageView imv_goods;

    private TextView tev_del, tev_save;


    private AddEnforceBean.WithholdGoodsValueBean goodsValueBean;

    private String[] array_wpfl;
    private AlertView alertView_wpfl;

    private String goodsName;
    private String ModelWit;
    private String UnitWit;
    private String CountWit;
    private String RemarkWit;
    private String GoodsPicWit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_add_goods);
    }

    @Override
    protected void findViews() {
        tev_GoodsClassifyIDWit = (TextView) findViewById(R.id.tev_GoodsClassifyIDWit);
        tev_del = (TextView) findViewById(R.id.tev_del);
        tev_save = (TextView) findViewById(R.id.tev_save);
        edt_NameWit = (EditText) findViewById(R.id.edt_NameWit);
        edt_CountWit = (EditText) findViewById(R.id.edt_CountWit);
        edt_UnitWit = (EditText) findViewById(R.id.edt_UnitWit);
        edt_ModelWit = (EditText) findViewById(R.id.edt_ModelWit);
        edt_RemarkWit = (EditText) findViewById(R.id.edt_RemarkWit);
        imv_goods = (ImageView) findViewById(R.id.imv_goods);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("物品信息");
        array_wpfl = getResources().getStringArray(R.array.wpfl);
        goodsValueBean = (AddEnforceBean.WithholdGoodsValueBean) getIntent().getSerializableExtra("WithholdGoodsValueBean");
        String goodsName = goodsValueBean.NameWit;
        GoodsPicWit = goodsValueBean.GoodsPicWit;
        String ModelWit = goodsValueBean.ModelWit;
        String UnitWit = goodsValueBean.UnitWit;
        String CountWit = goodsValueBean.CountWit;
        String RemarkWit = goodsValueBean.RemarkWit;
        int GoodsClassifyIDWit = goodsValueBean.GoodsClassifyIDWit;
        if (GoodsPicWit.startsWith("UploadImage")) {
            Glide.with(aty).load(HttpApi.URL_IMG_HEADER + GoodsPicWit).into(imv_goods);
        } else {
            Glide.with(aty).load(new File(GoodsPicWit)).into(imv_goods);
        }
        if (GoodsClassifyIDWit > 0 && GoodsClassifyIDWit < 6) {
            tev_GoodsClassifyIDWit.setText(array_wpfl[GoodsClassifyIDWit - 1]);
        } else {
            tev_GoodsClassifyIDWit.setText(array_wpfl[0]);
        }
        edt_NameWit.setText(goodsName);
        edt_CountWit.setText(CountWit);
        edt_ModelWit.setText(ModelWit);
        edt_UnitWit.setText(UnitWit);
        edt_RemarkWit.setText(RemarkWit);

        tev_GoodsClassifyIDWit.setOnClickListener(noFastClickLisener);
        tev_del.setOnClickListener(noFastClickLisener);
        tev_save.setOnClickListener(noFastClickLisener);
    }


    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_GoodsClassifyIDWit:
                    if (alertView_wpfl == null) {
                        alertView_wpfl = getShowAlert("选择物品分类", array_wpfl, tev_GoodsClassifyIDWit);
                    }
                    alertView_wpfl.show();
                    break;
                case R.id.tev_del:
                    setResult(100);
                    finish();
                    break;
                case R.id.tev_save:
                    doCheck();
                    break;


            }

        }
    };


    private void doCheck() {
        goodsName = getText(edt_NameWit);
        ModelWit = getText(edt_ModelWit);
        UnitWit = getText(edt_UnitWit);
        CountWit = getText(edt_CountWit);
        RemarkWit = getText(edt_RemarkWit);

        if (isEmpty(goodsName)) {
            warningShow("物品名称不能为空");
        } else if (isEmpty(CountWit)) {
            warningShow("物品数量不能为空");
        } else {
            if (GoodsPicWit.startsWith("UploadImage")) {
                saveAndSubmit();
            } else {
                upLoadImg();

            }


        }


    }


    //:TODO 保存信息，返回
    private void saveAndSubmit() {
        goodsValueBean.NameWit = goodsName;
        goodsValueBean.GoodsPicWit = GoodsPicWit;
        goodsValueBean.ModelWit = ModelWit;
        goodsValueBean.UnitWit = UnitWit;
        goodsValueBean.RemarkWit = RemarkWit;
        goodsValueBean.CountWit = CountWit;
        goodsValueBean.NameGoC=getText(tev_GoodsClassifyIDWit);
        goodsValueBean.GoodsClassifyIDWit = getPosionID(getText(tev_GoodsClassifyIDWit), array_wpfl);
        Intent intent = new Intent();
        intent.putExtra("WithholdGoodsValueBean", goodsValueBean);
        setResult(RESULT_OK, intent);
        finish();
    }


    //TODO:图片上传服务器
    private void upLoadImg() {
        ProgressDialogUtil.show(aty, "图片处理中...");
        String str = ImgUtil.bitmapToBase64(ImgUtil.getFileImg(GoodsPicWit));
        ProgressDialogUtil.hide();
        UpLoadImageUtil.uploadImage(aty, app.userID, "withholdgoods", str, new UpLoadImageUtil.onUploadImgCallBack() {
            @Override
            public void onSuccess(String picArray) {
                GoodsPicWit = picArray;
                saveAndSubmit();
            }

            @Override
            public void onFail(String msg) {
                warningShow(msg);
            }
        });
    }

    private int getPosionID(String text, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(text)) {
                return i + 1;
            }
        }
        return -1;
    }


}




















