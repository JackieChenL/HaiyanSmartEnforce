package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import smartenforce.baidu.BaiduMapActivity;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.AddGraphBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.projectutil.UpLoadImageUtil;
import smartenforce.util.ImgUtil;
import smartenforce.widget.ProgressDialogUtil;

public class AddGraphActivity extends ShowPdfActivity {

    private EditText edt_ExplainIch;

    private ImageView iv_item_add_graph;
    private String file_path = null;
    private int ID;

    private AddGraphBean addGraphBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_graph);
    }

    @Override
    protected void findViews() {
        edt_ExplainIch = (EditText) findViewById(R.id.edt_ExplainIch);
        iv_item_add_graph = (ImageView) findViewById(R.id.iv_item_add_graph);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("添加平面图");
        tev_title_right.setText("提交");
        ID = getIntent().getIntExtra("ID", -1);

        if (ID != -1) {
            tev_title_right.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    closeKeybord();
                    doUpLoadImage();
                }
            });
            iv_item_add_graph.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    startActivityForResult(new Intent(aty, BaiduMapActivity.class), REQUESTCODE.TIANDITU_PICTURE);
                }
            });

        }else{
            addGraphBean= (AddGraphBean) getIntent().getSerializableExtra("AddGraphBean");
            ID=addGraphBean.InquestIDIch;
            edt_ExplainIch.setText(addGraphBean.ExplainIch);
            final String[] url_arry = addGraphBean.SketchMapIch.split("\\|");
            Glide.with(aty).load(HttpApi.URL_IMG_HEADER + url_arry[0]).into(iv_item_add_graph);
            tev_title_right.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    closeKeybord();
                    doSaveIchnography(addGraphBean.SketchMapIch);
                }
            });
        }

    }

    private void doSaveIchnography(String picArray) {
        if (addGraphBean == null) {
            addGraphBean = new AddGraphBean();
        }
        addGraphBean.ExplainIch = getText(edt_ExplainIch);
        addGraphBean.InquestIDIch = ID ;
        addGraphBean.SketchMapIch = picArray;
        addGraphBean.UserIDIch = app.userID;
        String IchnographyPostData = JSON.toJSONString(addGraphBean);
        OkHttpUtils.post().url(HttpApi.URL_ICHNOGRAPHY_SAVE).addParams("IchnographyPostData", IchnographyPostData)
                .build().execute(new BeanCallBack(aty, "数据提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    try {
                        String json = bean.getResultBeanList(String.class).get(0);
                        JSONObject jo = new JSONObject(json);
                        int IchnographyID = jo.getInt("IchnographyID");
                        showPdfChoose(IchnographyID, 12);
                    } catch (JSONException e) {
                        finish();
                    }

                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESTCODE.TIANDITU_PICTURE) {
            file_path = data.getStringExtra("path");
            Glide.with(aty).load(new File(file_path)).into(iv_item_add_graph);


        }
    }


    private void doUpLoadImage() {
        if (file_path == null) {
            warningShow("请先添加图片再提交");
        } else {
            ProgressDialogUtil.show(aty, "处理中...");
            String img = ImgUtil.bitmapToBase64(ImgUtil.getPdfImg(file_path, 665, 880));
            ProgressDialogUtil.hide();
            UpLoadImageUtil.uploadImage(aty, app.userID, "inquest", img, new UpLoadImageUtil.onUploadImgCallBack() {
                @Override
                public void onSuccess(String picArray) {
                    doSaveIchnography(picArray);
                }

                @Override
                public void onFail(String msg) {
                    warningShow(msg);
                }
            });


        }


    }


}




















