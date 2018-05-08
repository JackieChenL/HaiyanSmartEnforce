package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.ReplacementTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.CarNumRecgnizeEntity;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.TiandiMapActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.posprinter.utils.DataForSendToPrinterPos58;
import net.xprinter.service.XprinterService;
import net.xprinter.xpinterface.IMyBinder;
import net.xprinter.xpinterface.ProcessData;
import net.xprinter.xpinterface.UiExecute;

import org.feezu.liuli.timeselector.TimeSelector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import okhttp3.Call;

public class IllegalParkingCommitActivity extends BaseActivity implements IllegalParkingCommitImgRvAdapter.OnImageAddClickListener, IllegalParkingCommitImgRvAdapter.OnImagelickListener, TakePhoto.TakeResultListener, View.OnClickListener {
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.sp_province)
    Spinner sp_province;
    @BindView(R.id.sp_ABC)
    Spinner sp_A2Z;
    @BindView(R.id.et_illegalparkingcommit_num)
    EditText et_num;
    @BindView(R.id.rv_illegalParkingCommit)
    RecyclerView recyclerView;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.tv_illegalParking_time)
    TextView tv_time;
    @BindView(R.id.iv_illefalParking_location)
    ImageView iv_location;
    @BindView(R.id.et_illegalparkingcommit_position)
    EditText et_positon;
    @BindView(R.id.tv_illegalParking_code)
    TextView tv_code;
    @BindView(R.id.tv_commitIllegalParking_print)
    TextView tv_back;
    @BindView(R.id.tv_commitIllegalParking_next)
    TextView tv_next;
    @BindView(R.id.iv_illegalParking_choseTime)
    ImageView iv_choseTime;
    @BindView(R.id.iv_commitParking_takePhoto)
    ImageView iv_takePhoto;
    String[] arr_province;
    String[] arr_abc;
    String longitude = "";
    String latitude = "";


    List<Bitmap> arr_image;
    IllegalParkingCommitImgRvAdapter adapter;
    TakePhoto takePhoto;
    private String time_chose;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig; //压缩参数
    String[] list_province;
    String[] list_A2Z;
    String province = "浙";
    String A2Z = "F";
    Uri uri;

    IMyBinder binder;
    ServiceConnection conn;
    BluetoothDevice device;
    private boolean isConnected = false;

    //    String time;
//    String position;
//    String carNum;
//    String code;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_illegal_parking_commit;
    }

    @Override
    protected String getTAG() {
        return "IllegalParkingCommit";
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = new TakePhotoImpl(this, this);
        }
        return takePhoto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
//        Log.i(TAG, "onActivityResult: "+data.toString());
        Log.i(TAG, "onActivityResult: " + requestCode + "  " + resultCode);
        if (requestCode == Constants.RESULTCODE_TIANDITU) {
            longitude = data.getStringExtra("Longitude");
            latitude = data.getStringExtra("Latitude");
            Log.i(TAG, "onActivityResult: " + longitude + "  " + latitude);
        }
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("违章停车");
        tv_time.setText(TimeUtils.getFormedTime("yyyy-MM-dd hh:mm"));
        takePhoto = new TakePhotoImpl(this, this);
        arr_province = getResources().getStringArray(R.array.provinceName);
        arr_abc = getResources().getStringArray(R.array.A2Z);


        list_province = getResources().getStringArray(R.array.provinceName);
        list_A2Z = getResources().getStringArray(R.array.A2Z);

        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                province = list_province[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_A2Z.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "onItemSelected: "+list_A2Z[i]);
                Log.i(TAG, "onItemSelected: position="+i);
                A2Z = list_A2Z[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //设置自动大小写转换
        et_num.setTransformationMethod(new UpperCaseTransform());
        iv_back.setOnClickListener(this);
        iv_choseTime.setOnClickListener(this);
        iv_location.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        iv_takePhoto.setOnClickListener(this);

        arr_image = new ArrayList<>();
        adapter = new IllegalParkingCommitImgRvAdapter(arr_image, mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnImageAddClickListener(this);
        adapter.setOnImagelickListener(this);
        setRecyclerViewHeight(arr_image.size());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onImageAddClick() {

        Log.i(TAG, "onImageAddClick: ");
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    100);
        } else {
            //查看图片权限
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        101);
            } else {
                uri = getImageCropUri();
//                cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
                //设置压缩参数
                compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(400).create();
                takePhoto.onEnableCompress(compressConfig, true); //设置为需要压缩
                takePhoto.onPickFromCapture(uri);
            }
        }

    }

    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    @Override
    public void onImageClick(int p) {
        Bitmap bmp = arr_image.get(p);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

//        Bundle b = new Bundle();
//        b.putByteArray("bitmap", bytes);
        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra("image", bytes);
        startActivity(intent);


    }

    @Override
    public void takeSuccess(String imagePath) {
        Log.i(TAG, "takeSuccess: " + imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath

//        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 6, getResources().getColor(R.color.orange), 5, 5);
//        Log.i(TAG, "takeSuccess: length=" + water_bitmap.getByteCount() / 1024);
//        arr_image.add(water_bitmap);
//        setRecyclerViewHeight(arr_image.size());
//        adapter.notifyDataSetChanged();
        carNumRecognize(BitmapToBase64.bitmapToBase64(bmp));
    }

    @Override
    public void takeFail(String msg) {
        ToastUtils.showToast(mContext, "拍摄失败");
    }

    @Override
    public void takeCancel() {
        ToastUtils.showToast(mContext, "取消拍摄");
    }

    public void setRecyclerViewHeight(int size) {
        int height = ((size / 2) + 1) * 140 + 30;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
        layoutParams.setMargins(0, Dp2pxUtil.dip2px(mContext, 5), 0, Dp2pxUtil.dip2px(mContext, 50));
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }

    public void carNumRecognize(String img) {

//        RetrofitClient.createService(CarNumScanApi.class,"http://jisucpsb.market.alicloudapi.com/")
//                .httpCarNumRecognize("24553193","APPCODE 2e476d97d6994a489afb3491b44a2578",img)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MySubscriber<String>(mContext) {
//                    @Override
//                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
//                        Log.i(TAG, "onError: "+responeThrowable.toString());
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.i(TAG, "onNext: "+s);
//                    }
//                });

        OkHttpUtils.post().url("http://jisucpsb.market.alicloudapi.com/licenseplaterecognition/recognize")
                .addHeader("X-Ca-Key", "24553193")
                .addHeader("Authorization", "APPCODE 2e476d97d6994a489afb3491b44a2578")
                .addParams("pic", img).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
            }

            @Override
            public void onResponse(final String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                CarNumRecgnizeEntity carNumRecgnizeEntity = gson.fromJson(response,CarNumRecgnizeEntity.class);
                if (carNumRecgnizeEntity.getStatus().equals("0")) {
                    String carNum = carNumRecgnizeEntity.getResult().getNumber();
                    et_num.setText(carNum.substring(2));

                    String prov = String.valueOf(carNum.charAt(0));
                    String a2z = String.valueOf(carNum.charAt(1));

                    for (int i = 0; i < list_province.length; i++) {
                        if (prov.equals(list_province[i])) {
                            sp_province.setSelection(i);
                            province = prov;
                            break;
                        }
                    }
                    for (int i = 0; i < list_A2Z.length; i++) {
                        if (a2z.equals(list_A2Z[i])) {
                            sp_A2Z.setSelection(i);
                            A2Z = a2z;
                            Log.i(TAG, "onResponse: position="+i);
                            Log.i(TAG, "onResponse: "+list_A2Z[i]);
                            Log.i(TAG, "onResponse: "+a2z);
                            break;
                        }
                    }

                }else {
                    ToastUtils.showToast(mContext,"识别失败，请重新识别");
                }
            }



        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commitIllegalParking_print:
                if (!et_num.getText().toString().equals("")) {
                    if (et_num.getText().toString().length() == 5) {

                        if (!et_positon.getText().toString().equals("")) {
                            print();
                        } else {
                            ToastUtils.showToast(mContext, "请输入地点信息");
                        }
                    }else {
                        ToastUtils.showToast(mContext, "请输入正确的5位车牌号码");
                    }
                } else {
                    ToastUtils.showToast(mContext, "请输入车牌号");
                }
//                commit();

                break;
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.iv_illegalParking_choseTime:
                choseTime();
                break;
            case R.id.iv_illefalParking_location:
                startActivityForResult(new Intent(mContext, TiandiMapActivity.class), Constants.RESULTCODE_TIANDITU);
                break;
            case R.id.tv_commitIllegalParking_next:
                Intent intent = new Intent(mContext, IllegalParkingTakePhotoActivity.class);
                Log.i(TAG, "onNext: " + tv_time.getText().toString());
                intent.putExtra("Time", tv_time.getText().toString());
                intent.putExtra("Position", et_positon.getText().toString());
                intent.putExtra("CarNum", province + A2Z + et_num.getText().toString());
                intent.putExtra("Code", tv_code.getText().toString());
                startActivity(intent);
                break;
            case R.id.iv_commitParking_takePhoto:
                takePhoto();
        }
    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    100);
        } else {
//            url("http://jisucpsb.market.alicloudapi.com/licenseplaterecognition/recognize")
//                    .addHeader("X-Ca-Key", "24553193")
//                    .addHeader("Authorization", "APPCODE 2e476d97d6994a489afb3491b44a2578")
            uri = getImageCropUri();
//                cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
            //设置压缩参数
            compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(400).create();
            takePhoto.onEnableCompress(compressConfig, true); //设置为需要压缩
            takePhoto.onPickFromCapture(uri);

        }
    }

    private void print() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        } else {
            if (!isConnected) {

                Log.i(TAG, "print: ");
                conn = new ServiceConnection() {

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        // TODO Auto-generated method stub
                        Log.i(TAG, "onServiceDisconnected: ");
                    }

                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        // TODO Auto-generated method stub
                        //绑定成功
                        Log.i(TAG, "onServiceConnected: ");
                        binder = (IMyBinder) service;
                        Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
                        if (devices.size() > 0) {
                            for (Iterator<BluetoothDevice> iterator = devices.iterator(); iterator.hasNext(); ) {
                                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                                Log.i(TAG, "设备：" + bluetoothDevice.getName() + " " + bluetoothDevice.getAddress());
                                if (bluetoothDevice.getName().trim().equals("printer001")) {
                                    device = bluetoothDevice;
                                }
                            }
                        } else {
                            ToastUtils.showToast(mContext, "请先连接名为“Printer001”匹配码为“0000”的打印机，再进行操作！");
                        }
//                    Log.i(TAG, "device: "+device.getAddress());
                        if (device != null) {
                            binder.connectBtPort(device.getAddress().trim(), new UiExecute() {
                                @Override
                                public void onsucess() {
                                    Log.i(TAG, "打印机连接成功: ");
                                    isConnected = true;
                                    binder.acceptdatafromprinter(new UiExecute() {

                                        @Override
                                        public void onsucess() {
                                            // TODO Auto-generated method stub
                                            Log.i(TAG, "acceptdatafromprinter onsucess: ");

                                        }

                                        @Override
                                        public void onfailed() {
                                            // TODO Auto-generated method stub
                                            isConnected = false;
                                            Toast.makeText(getApplicationContext(), "连接已断开", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    doPrintWork();
                                }

                                @Override
                                public void onfailed() {
                                    Log.i(TAG, "打印机连接失败: ");
                                    isConnected = false;
                                    ToastUtils.showToast(mContext, "打印机连接失败");
                                }
                            });
                        } else {
                            ToastUtils.showToast(mContext, "请先连接名为“Printer001”匹配码为“0000”的打印机，再进行操作！");
                        }
                    }
                };
                Intent intent = new Intent(mContext, XprinterService.class);
                bindService(intent, conn, BIND_AUTO_CREATE);

            } else {
                doPrintWork();
            }

            //蓝牙
//            startBlueScan();
        }
    }

    public void doPrintWork() {
        binder.writeDataByYouself(new UiExecute() {

            @Override
            public void onsucess() {
                // TODO Auto-generated method stub
                Log.i(TAG, "onsucess: 连接成功");
            }

            @Override
            public void onfailed() {
                // TODO Auto-generated method stub
                isConnected = false;
                Log.i(TAG, "onsucess: 连接失败，请重新连接");
            }
        }, new ProcessData() {//第二个参数是ProcessData接口的实现
            //这个接口的重写processDataBeforeSend这个处理你要发送的指令
            @Override
            public List<byte[]> processDataBeforeSend() {
                // TODO Auto-generated method stub
                //初始化一个list

                return formAndPrint();
            }
        });
    }

    public static byte[] strTobytes(String str) {
        byte[] b = null, data = null;
        try {
            b = str.getBytes("utf-8");
            data = new String(b, "utf-8").getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    public List<byte[]> formAndPrint() {
        ArrayList<byte[]> list = new ArrayList<byte[]>();
        list.add(DataForSendToPrinterPos58.initializePrinter());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(3));
        //标题
        byte[] title = strTobytes("道路交通安全违法行为处理通知书");
        //居中
        list.add(DataForSendToPrinterPos58.selectAlignment(1));
        list.add(DataForSendToPrinterPos58.selectCharacterSize(1));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        list.add(title);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        //空一行
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));

        //编号
        list.add(DataForSendToPrinterPos58.initializePrinter());
        byte[] num = strTobytes("编号：" + tv_code.getText().toString());
        list.add(DataForSendToPrinterPos58.selectAlignment(1));
        list.add(num);
//                list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        //空一行
        list.add(DataForSendToPrinterPos58.printAndFeedForward(2));

        list.add(DataForSendToPrinterPos58.selectAlignment(0));
        //省
        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        byte[] data_province = strTobytes(province);
        list.add(data_province);
        //车牌
        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(1));
        Log.i(TAG, "formAndPrint: "+A2Z);
        byte[] data_carNum = strTobytes(A2Z + ""+et_num.getText().toString());
        list.add(data_carNum);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        byte[] data_1 = strTobytes("号牌号码的机动车驾驶人：");
        list.add(data_1);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));
        byte[] data_content = strTobytes(String.format("    该车辆于%s，在", tv_time.getText().toString()));
        list.add(data_content);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(1));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        byte[] data_Position = strTobytes(et_positon.getText().toString());
        list.add(data_Position);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        byte[] data_content2 = strTobytes("实施的停车行为违反了《道路交通安全法》第56条之规定。请您持本通知书，驾驶证，行驶证在15日内，到");
        list.add(data_content2);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        byte[] data_handlePosition = strTobytes("交通警察服务中心");
        list.add(data_handlePosition);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        byte[] data_2 = strTobytes("接受处理。");
        list.add(data_2);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());

        list.add(DataForSendToPrinterPos58.printAndFeedForward(2));

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        byte[] data_dizhi = strTobytes("地址：");
        list.add(data_dizhi);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        byte[] data_p = strTobytes("海盐县武原街道出海路与公园路交界口");
        list.add(data_p);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        byte[] data_dianhua = strTobytes("咨询电话：");
        list.add(data_dianhua);

//        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(1));
        byte[] data_call = strTobytes("0573-86198731");
        list.add(data_call);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));



        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        byte[] beizhu = strTobytes("备注：机动车所有人登记的住所地址或联系电话发生变化的，请及时向登记地车管所申请变更备案。");
        list.add(beizhu);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());

        list.add(DataForSendToPrinterPos58.printAndFeedForward(5));

        list.add(DataForSendToPrinterPos58.selectAlignment(1));
        byte[] type = strTobytes("第一联：车主联");
        list.add(type);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        byte[] line = strTobytes("--------------------------------");
        list.add(line);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());

        printAgain(list);

        return list;
    }

    private void printAgain(ArrayList<byte[]> list) {
        list.add(DataForSendToPrinterPos58.initializePrinter());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(3));
        //标题
        byte[] title = strTobytes("道路交通安全违法行为处理通知书");
        //居中
        list.add(DataForSendToPrinterPos58.selectAlignment(1));
        list.add(DataForSendToPrinterPos58.selectCharacterSize(1));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        list.add(title);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        //空一行
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));

        //编号
        list.add(DataForSendToPrinterPos58.initializePrinter());
        byte[] num = strTobytes("编号：" + tv_code.getText().toString());
        list.add(DataForSendToPrinterPos58.selectAlignment(1));
        list.add(num);
//                list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        //空一行
        list.add(DataForSendToPrinterPos58.printAndFeedForward(2));

        list.add(DataForSendToPrinterPos58.selectAlignment(0));
        //省
        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        byte[] data_province = strTobytes(province);
        list.add(data_province);
        //车牌
        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(1));
        Log.i(TAG, "formAndPrint: "+A2Z);
        byte[] data_carNum = strTobytes(A2Z + ""+et_num.getText().toString());
        list.add(data_carNum);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        byte[] data_1 = strTobytes("号牌号码的机动车驾驶人：");
        list.add(data_1);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));
        byte[] data_content = strTobytes(String.format("    该车辆于%s，在", tv_time.getText().toString()));
        list.add(data_content);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(1));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        byte[] data_Position = strTobytes(et_positon.getText().toString());
        list.add(data_Position);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        byte[] data_content2 = strTobytes("实施的停车行为违反了《道路交通安全法》第56条之规定。请您持本通知书，驾驶证，行驶证在15日内，到");
        list.add(data_content2);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        byte[] data_handlePosition = strTobytes("交通警察服务中心");
        list.add(data_handlePosition);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        byte[] data_2 = strTobytes("接受处理。");
        list.add(data_2);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());

        list.add(DataForSendToPrinterPos58.printAndFeedForward(2));

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        byte[] data_dizhi = strTobytes("地址：");
        list.add(data_dizhi);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        byte[] data_p = strTobytes("海盐县武原街道出海路与公园路交界口");
        list.add(data_p);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        byte[] data_dianhua = strTobytes("咨询电话：");
        list.add(data_dianhua);

//        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(1));
        byte[] data_call = strTobytes("0573-86198731");
        list.add(data_call);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));



        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        byte[] beizhu = strTobytes("备注：机动车所有人登记的住所地址或联系电话发生变化的，请及时向登记地车管所申请变更备案。");
        list.add(beizhu);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(5));

        list.add(DataForSendToPrinterPos58.selectAlignment(1));
        byte[] type = strTobytes("第二联：存根联");
        list.add(type);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        byte[] line = strTobytes("--------------------------------");
        list.add(line);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());

        list.add(DataForSendToPrinterPos58.printAndFeedForward(2));
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (conn != null) {
            if (isConnected) {
                unbindService(conn);
            }
//            conn.onServiceConnected();
        }
    }


    private void choseTime() {
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                time_chose = time;
                tv_time.setText(time);
            }
        }, TimeUtils.getFormedTime("yyyy-MM-dd") + " 00:00", TimeUtils.getFormedTime("yyyy-MM-dd hh:mm"));
        timeSelector.setIsLoop(true);
        timeSelector.show();
    }


    public class UpperCaseTransform extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }
    }
}
