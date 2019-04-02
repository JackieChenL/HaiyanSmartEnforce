package smartenforce.aty.function1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.GeoBean;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.GeoUtils;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.TiandiMapActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.tianditu.android.maps.GeoPoint;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.util.ArrayList;
import java.util.List;

import smartenforce.adapter.ImageAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.CitizenBean;
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.bean.FirstLevelBean;
import smartenforce.bean.SourcePersonAddBean;
import smartenforce.bean.ThirdLevelBean;
import smartenforce.dialog.ListDialog;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ListItemClickLisener;
import smartenforce.intf.PermissonCallBack;
import smartenforce.projectutil.UpLoadImageUtil;
import smartenforce.tianditu.TiandituMapActivity;
import smartenforce.tianditu.TiandituUtil;
import smartenforce.util.DateUtil;
import smartenforce.widget.FullyGridLayoutManager;


public class NewQueryWithAudioActivity extends AudioActivity {
    private int lat = 0, lon = 0;
    private String address;

    private int dsr_type = 3;
    private int dsr_id = -1;
    private String dsr_name;

    private TextView tev_dl, tev_wfxw, tev_afdd, tev_dsr;
    private EditText edt_rwms;
    private ScrollView scv;
    private TextView tev_dl_r, tev_wfxw_r, tev_afdd_r;
    private TextView tev_left, tev_right;
    private RecyclerView rcv_l, rcv_r;
    private ImageAdapter adapter_l, adapter_r;
    private ArrayList<String> list_l = new ArrayList<String>();
    private ArrayList<String> list_r = new ArrayList<String>();

    private List<Object> firstLevelBeanArrayList = new ArrayList<>();
    private List<Object> thildLevelBeanArrayList = new ArrayList<>();
    //定义一个初始大类ID
    private int FIRSTLEVELID = -1;
    //定义一个初始违法行为ID
    private int THIRDLEVELID = -1;

    private int SECONDLEVELID = -1;

    private String ACTION_TYPE = "";
    //处理前照片
    private String UploadOriginSou = "";
    //处理后照片
    private String UploadReturnTas = "";
    private Button btn_voice;
    private ImageView imv_voice;

    private GeoBean geoBean = new GeoBean(null);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xc_add_audio);
    }

    @Override
    protected void findViews() {
        tev_dl = (TextView) findViewById(R.id.tev_dl);
        tev_wfxw = (TextView) findViewById(R.id.tev_wfxw);
        tev_afdd = (TextView) findViewById(R.id.tev_afdd);
        tev_dsr = (TextView) findViewById(R.id.tev_dsr);
        edt_rwms = (EditText) findViewById(R.id.edt_rwms);
        tev_dl_r = (TextView) findViewById(R.id.tev_dl_r);
        tev_wfxw_r = (TextView) findViewById(R.id.tev_wfxw_r);
        tev_afdd_r = (TextView) findViewById(R.id.tev_afdd_r);
        tev_left = (TextView) findViewById(R.id.tev_left);
        tev_right = (TextView) findViewById(R.id.tev_right);
        rcv_l = (RecyclerView) findViewById(R.id.rcv_l);
        rcv_r = (RecyclerView) findViewById(R.id.rcv_r);
        scv = (ScrollView) findViewById(R.id.scv);
        btn_voice = (Button) findViewById(R.id.btn_voice);
        imv_voice = (ImageView) findViewById(R.id.imv_voice);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("新增巡查");
        tev_left.setText("保存");
        tev_right.setText("提交");
        getFirstLevel();
        initPictureAdapter();
        GeoUtils.getInstance().startLocation(this, new GeoUtils.onLocationSuccessCallback() {
            @Override
            public void onSuccess(GeoBean geo) {
                geoBean = geo;
                tev_afdd.setText(geoBean.address);
            }
        });
        tev_dsr.setOnClickListener(noFastClickLisener);
        tev_dl_r.setOnClickListener(noFastClickLisener);
        tev_dl.setOnClickListener(noFastClickLisener);
        tev_wfxw_r.setOnClickListener(noFastClickLisener);
        tev_wfxw.setOnClickListener(noFastClickLisener);
        tev_afdd_r.setOnClickListener(noFastClickLisener);
        tev_afdd.setOnClickListener(noFastClickLisener);
        tev_left.setOnClickListener(noFastClickLisener);
        tev_right.setOnClickListener(noFastClickLisener);
        btn_voice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startVoiceRecord();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopVoice(imv_voice);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    private void initPictureAdapter() {
        FullyGridLayoutManager layoutManager_l = new FullyGridLayoutManager(aty, 4, LinearLayout.VERTICAL, false);
        FullyGridLayoutManager layoutManager_r = new FullyGridLayoutManager(aty, 4, LinearLayout.VERTICAL, false);
        adapter_l = new ImageAdapter(list_l, aty, scv);
        adapter_r = new ImageAdapter(list_r, aty, scv);
        rcv_l.setLayoutManager(layoutManager_l);
        rcv_r.setLayoutManager(layoutManager_r);
        rcv_l.setAdapter(adapter_l);
        rcv_r.setAdapter(adapter_r);
        adapter_l.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                takePhoto(REQUESTCODE.CAMERA_PRE);
            }

            @Override
            public void onDelImageClick(int p) {
                list_l.remove(p);
                adapter_l.notifyChanged();


            }
        });
        adapter_r.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                takePhoto(REQUESTCODE.CAMERA_AFTER);
            }

            @Override
            public void onDelImageClick(int p) {
                list_r.remove(p);
                adapter_r.notifyChanged();


            }
        });
    }

    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_dl_r:
                    getFirstLevel();
                    break;
                case R.id.tev_dl:
                    getFirstLevel();
                    break;
                case R.id.tev_wfxw:
                    getThirdLevel();
                    break;
                case R.id.tev_wfxw_r:
                    getThirdLevel();
                    break;
                case R.id.tev_afdd_r:
                case R.id.tev_afdd:
                    Intent mapIntent = new Intent(aty, TiandiMapActivity.class);
                    mapIntent.putExtra("GeoBean", geoBean);
                    startActivityForResult(mapIntent, REQUESTCODE.LOCATION);
                    break;
                case R.id.tev_dsr:
                    startActivityForResult(new Intent(aty, EnterpriseOrCitizenActivity.class), REQUESTCODE.DSR);
                    break;
                case R.id.tev_left:
                    ACTION_TYPE = "save";
                    checkInfo();
                    break;
                case R.id.tev_right:
                    ACTION_TYPE = "submit";
                    checkInfo();
                    break;

            }

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE.LOCATION) {
                lat = data.getIntExtra("Latitude", 0);
                lon = data.getIntExtra("Longitude", 0);
                address = data.getStringExtra("Address");
                geoBean = (GeoBean) data.getSerializableExtra("GeoBean");
                tev_afdd.setText(address);
            } else if (requestCode == REQUESTCODE.DSR) {
                dsr_type = data.getIntExtra("TYPE", 3);
                Object obj = data.getSerializableExtra("OBJECT");
                if (dsr_type == 1) {
                    dsr_id = ((EnterpriseDetailBean) obj).EnterpriseID;
                    dsr_name = ((EnterpriseDetailBean) obj).NameEnt;
                } else if (dsr_type == 2) {
                    dsr_id = ((CitizenBean) obj).CitizenID;
                    dsr_name = ((CitizenBean) obj).NameCit;
                } else {
                    dsr_id = -1;
                    dsr_name = "匿名";
                }
                tev_dsr.setText(dsr_name);
            } else if (requestCode == REQUESTCODE.CAMERA_PRE) {
                list_l.add(file.getAbsolutePath());
                adapter_l.notifyChanged();
            } else if (requestCode == REQUESTCODE.CAMERA_AFTER) {
                list_r.add(file.getAbsolutePath());
                adapter_r.notifyChanged();
            }


        }


    }

    //初始化进入界面时最好先请求一下大类
    private void getFirstLevel() {
        if (firstLevelBeanArrayList.size() == 0) {
            OkHttpUtils.get().url(HttpApi.URL_FIRSTLEVEL_LIST).build().execute(new BeanCallBack(aty, null) {
                @Override
                public void handleBeanResult(NetResultBean bean) {
                    if (bean.State) {
                        if (bean.total > 0) {
                            firstLevelBeanArrayList.addAll(bean.getResultBeanList(FirstLevelBean.class));
                        } else {
                            warningShow("获取大类信息为空");
                        }
                    } else {
                        warningShow(bean.ErrorMsg);
                    }
                }
            });
        } else {
            new ListDialog(aty, firstLevelBeanArrayList, new ListItemClickLisener() {
                @Override
                public void onItemClick(int P, Object obj) {
                    FirstLevelBean firstLevelBean = (FirstLevelBean) obj;
                    tev_dl.setText(firstLevelBean.NameFiL);
                    tev_wfxw.setText("");
                    FIRSTLEVELID = firstLevelBean.FirstLevelID;
                    THIRDLEVELID = -1;
                    SECONDLEVELID = -1;
                }
            }).setTitle("请选择大类").show();

        }
    }

    private void getThirdLevel() {
        if (FIRSTLEVELID == -1) {
            warningShow("请先选择大类");
        } else {
            OkHttpUtils.post().url(HttpApi.URL_THIRDLIST).addParams("FirstLevelID", FIRSTLEVELID + "")
                    .build().execute(new BeanCallBack(aty, null) {
                @Override
                public void handleBeanResult(NetResultBean bean) {
                    if (bean.State) {
                        if (bean.total > 0) {
                            thildLevelBeanArrayList.clear();
                            thildLevelBeanArrayList.addAll(bean.getResultBeanList(ThirdLevelBean.class));
                            new ListDialog(aty, thildLevelBeanArrayList, new ListItemClickLisener() {
                                @Override
                                public void onItemClick(int P, Object obj) {
                                    ThirdLevelBean thirdLevelBean = (ThirdLevelBean) obj;
                                    tev_wfxw.setText(thirdLevelBean.NameThL);
                                    THIRDLEVELID = thirdLevelBean.ThirdLevelID;
                                    SECONDLEVELID = thirdLevelBean.SecondLevelID;
                                }
                            }).setTitle("请选择违法行为").show();


                        } else {
                            tev_wfxw.setText("");
                            THIRDLEVELID = -1;
                            SECONDLEVELID = -1;
                            warningShow("该大类下没有定义违法行为");
                        }
                    } else {
                        tev_wfxw.setText("");
                        THIRDLEVELID = -1;
                        SECONDLEVELID = -1;
                        warningShow(bean.ErrorMsg);
                    }

                }
            });
        }
    }

    private void doUploadImgBefore() {
        String base64_l = adapter_l.getImageListStr(false);
        if (!isEmpty(base64_l)) {
            String[] array = new String[]{"enterprise", "citizen", "anonymous"};
            UpLoadImageUtil.uploadImage(aty, app.userID, array[dsr_type - 1], base64_l, new UpLoadImageUtil.onUploadImgCallBack() {
                @Override
                public void onSuccess(String picArray) {
                    UploadOriginSou = picArray;
                    doUploadImgAfter();
                }

                @Override
                public void onFail(String msg) {
                    warningShow(msg);
                }
            });

        } else {
            doUploadImgAfter();
        }


    }

    private void doUploadImgAfter() {
        String base64_r = adapter_r.getImageListStr(false);
        if (!isEmpty(base64_r)) {
            String[] array = new String[]{"enterprise", "citizen", "anonymous"};
            UpLoadImageUtil.uploadImage(aty, app.userID, array[dsr_type - 1], base64_r, new UpLoadImageUtil.onUploadImgCallBack() {
                @Override
                public void onSuccess(String picArray) {
                    UploadReturnTas = picArray;
                    doUploadInfo();
                }

                @Override
                public void onFail(String msg) {
                    warningShow(msg);
                }
            });

        } else {
            doUploadInfo();
        }

    }


    //上传图片
    private void doUploadImg() {
        doUploadImgBefore();
    }


    //新增或保存信息
    private void doUploadInfo() {
        SourcePersonAddBean bean = new SourcePersonAddBean();
        bean.AddressSou = address;
        if (lat != 0 && lon != 0) {
            bean.GpsXYSou = ((double) lat) / 1000000 + "," + ((double) lon) / 1000000;
        } else {
            bean.GpsXYSou = "";
        }
        bean.EntOrCitID = dsr_id;
        bean.SubmitOrSave = ACTION_TYPE;
        bean.FirstLevelIDSou = FIRSTLEVELID;
        bean.SecondLevelIDSou = SECONDLEVELID;
        bean.ThirdLevelIDSou = THIRDLEVELID;
        bean.ContentSou = getText(edt_rwms);
        bean.UploadOriginSou = UploadOriginSou;
        bean.UploadReturnTas = UploadReturnTas;
        bean.EntOrCitiSou = dsr_type;
        bean.EntryTimeSou = DateUtil.currentTime();
        bean.UserID = app.userID;
        bean.DepartmentID = app.DepartmentID;
        bean.NameECSou = getText(tev_dsr);
        bean.NameTas = DateUtil.createXCName();
        String SourcePersonPostData = JSON.toJSONString(bean);
        PostFormBuilder postFormBuilder = OkHttpUtils.post()
                .url(HttpApi.URL_SOURCEPERSONADD)
                .addParams("SourcePersonPostData", SourcePersonPostData);
        if (voice_file != null&&is_voice_valid) {
                log("RECORD", "录音文件：" + voice_file.getName());
                postFormBuilder.addFile("voice", "voice", voice_file);
        }

        postFormBuilder.build().execute(new BeanCallBack(aty, "数据提交中") {

            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("操作成功");
                    finish();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });


    }

    private void checkInfo() {
        if (FIRSTLEVELID == -1) {
            warningShow("大类不能为空");
        } else if (THIRDLEVELID == -1) {
            warningShow("违法行为不能为空");
        } else if (isEmpty(address)) {
            warningShow("案发地点不能为空");
        } else if (ACTION_TYPE.equals("submit") && list_r.size() == 0) {

            new AlertView("提示", "尚未添加处理后图片，请确认是否提交，提交后将不能更改", null, null, new String[]{"取消", "提交"}, aty, AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position == 1) {
                        doUploadImg();
                    }
                }
            }).show();
        } else {
            doUploadImg();
        }


    }

}
















