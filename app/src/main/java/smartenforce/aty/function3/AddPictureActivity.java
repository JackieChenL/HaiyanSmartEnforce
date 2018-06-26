package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import smartenforce.adapter.ImageAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.AddPictureBean;
import smartenforce.bean.PicIchCountBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.projectutil.UpLoadImageUtil;
import smartenforce.util.DateUtil;
import smartenforce.widget.FullyGridLayoutManager;

public class AddPictureActivity extends ShowPdfActivity {
    private TextView tev_ShotManPho, tev_ShotTimePho, tev_ShotAddressPho;
    private ScrollView scv;
    private EditText edt_ExplainPho;
    private boolean isFirstAdd = true;
    private RecyclerView rv_photo;
    private ImageAdapter adapter;
    private ArrayList<String> list = new ArrayList<String>();
    private int ID;
    private AddPictureBean addPictureBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
    }

    @Override
    protected void findViews() {
        tev_ShotManPho = (TextView) findViewById(R.id.tev_ShotManPho);
        tev_ShotTimePho = (TextView) findViewById(R.id.tev_ShotTimePho);
        edt_ExplainPho = (EditText) findViewById(R.id.edt_ExplainPho);
        tev_ShotAddressPho = (TextView) findViewById(R.id.tev_ShotAddressPho);
        rv_photo = (RecyclerView) findViewById(R.id.rv_photo);
        scv = (ScrollView) findViewById(R.id.scv);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("添加图片");
        tev_title_right.setText("提交");
        ID = getIntent().getIntExtra("ID", -1);

        //新增
        if (ID != -1) {
            String address = getIntent().getStringExtra("ADDRESS");
            checkIsfirstAdd();
            tev_ShotAddressPho.setText(address);
            tev_ShotManPho.setText(app.NameEmp);
            tev_ShotTimePho.setText(DateUtil.currentTime());
            FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(aty, 4, LinearLayout.VERTICAL, false);
            adapter = new ImageAdapter(list, aty, scv);
            rv_photo.setLayoutManager(layoutManager);
            rv_photo.setAdapter(adapter);
            adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                @Override
                public void onImageAddClick() {
                    takePhoto(REQUESTCODE.CAMERA);
                }


                @Override
                public void onDelImageClick(int p) {
                    list.remove(p);
                    adapter.notifyChanged();

                }
            });

            tev_title_right.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    closeKeybord();
                    doUpLoadImage();
                }
            });
        } else {
            addPictureBean = (AddPictureBean) getIntent().getSerializableExtra("AddPictureBean");
            ID=addPictureBean.InquestIDPho;
            tev_ShotAddressPho.setText(addPictureBean.ShotAddressPho);
            tev_ShotManPho.setText(addPictureBean.ShotManPho);
            tev_ShotTimePho.setText(addPictureBean.ShotTimePho);
            edt_ExplainPho.setText(addPictureBean.ExplainPho);
            final String[] url_arry = addPictureBean.PictruePho.split("\\|");
            list.addAll(Arrays.asList(url_arry));
            FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(aty, 4, LinearLayout.VERTICAL, false);
            adapter = new ImageAdapter(list, aty, scv);
            adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                @Override
                public void onImageAddClick() {
                    warningShow("修改操作时不允许修改图片证据");
                }


                @Override
                public void onDelImageClick(int p) {
                    warningShow("修改操作时不允许修改图片证据");

                }
            });
            rv_photo.setLayoutManager(layoutManager);
            rv_photo.setAdapter(adapter);
            tev_title_right.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    closeKeybord();
                    doSavePhotograph(addPictureBean.PictruePho);
                }
            });

        }

    }


    private void checkIsfirstAdd() {
        OkHttpUtils.post().url(HttpApi.URL_INQUESTPHICHCOUNT)
                .addParams("UserID", app.userID)
                .addParams("DepartmentID", app.DepartmentID)
                .addParams("InquestID", ID + "")
                .build().execute(new BeanCallBack(aty, null) {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    try {
                        PicIchCountBean picIchCountBean = bean.getResultBeanList(PicIchCountBean.class).get(0);
                        isFirstAdd = picIchCountBean.Photograph.PhCount == 0 ? true : false;
                    } catch (Exception e) {
                        //直接解析，怕出现空对象取值，出现直接默认第一次添加
                    }
                }
            }
        });


    }


    private void doSavePhotograph(String picArray) {
        if (addPictureBean==null){
            addPictureBean=new AddPictureBean();
        }
        addPictureBean.InquestIDPho = ID ;
        addPictureBean.PictruePho = picArray;
        addPictureBean.ExplainPho = getText(edt_ExplainPho);
        addPictureBean.ShotAddressPho = getText(tev_ShotAddressPho);
        addPictureBean.ShotManPho = getText(tev_ShotManPho);
        addPictureBean.ShotTimePho = getText(tev_ShotTimePho);
        addPictureBean.UserIDPho = app.userID;
        String PhotographPostData = JSON.toJSONString(addPictureBean);
        OkHttpUtils.post().url(HttpApi.URL_PHOTOGRAPH_SAVE).addParams("PhotographPostData", PhotographPostData)
                .build().execute(new BeanCallBack(aty, "数据提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    try {
                        String json = bean.getResultBeanList(String.class).get(0);
                        JSONObject jo = new JSONObject(json);
                        int PhotographID = jo.getInt("PhotographID");
                        showPdfChoose(PhotographID, 11);
                    } catch (JSONException e) {
                        finish();
                    }

                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });

    }


    private void doUpLoadImage() {
        if (list.size() == 0) {
            warningShow("请先添加图片再提交");
        } else {
            if (list.size() == 1 && isFirstAdd) {
                warningShow("首次添加现场照片必须要2张");
                return;

            }
            String img = adapter.getImageListStr(true);
            UpLoadImageUtil.uploadImage(aty, app.userID, "inquest", img, new UpLoadImageUtil.onUploadImgCallBack() {
                @Override
                public void onSuccess(String picArray) {
                    doSavePhotograph(picArray);
                }

                @Override
                public void onFail(String msg) {
                    warningShow(msg);
                }
            });

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE.CAMERA) {
                if (list.size() < 2) {
                    list.add(file.getAbsolutePath());
                    adapter.notifyChanged();
                } else {
                    warningShow("最多只能添加两张照片哦");
                }

            }


        }


    }
}




















