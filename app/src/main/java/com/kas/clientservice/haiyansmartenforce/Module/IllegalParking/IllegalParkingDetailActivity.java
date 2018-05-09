package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.ImageListRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Entity.ParkingSearchEntity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import net.posprinter.utils.DataForSendToPrinterPos58;
import net.xprinter.service.XprinterService;
import net.xprinter.xpinterface.IMyBinder;
import net.xprinter.xpinterface.ProcessData;
import net.xprinter.xpinterface.UiExecute;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

public class IllegalParkingDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_parkingDetail_print)
    TextView tv_print;
    @BindView(R.id.tv_parkingDetail_carNum)
    TextView tv_carNum;
    @BindView(R.id.tv_parkingDetail_position)
    TextView tv_position;
    @BindView(R.id.tv_illegalParkingDetail_time)
    TextView tv_time;
    @BindView(R.id.tv_parkingDetail_next)
    TextView tv_next;
    @BindView(R.id.tv_illegalParkingDetail_status)
    TextView tv_status;
    @BindView(R.id.rv_parkingDetail)
    RecyclerView recyclerView;

    IMyBinder binder;
    ServiceConnection conn;
    BluetoothDevice device;
    private boolean isConnected = false;
    String time;
    String position;
    String carNum;
    String code;
    String status;
    String img;
    List<String> list_img;
    ImageListRvAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_illegal_parking_detail;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
//        intent.putExtra("Time",tv_time.getText().toString());
//        intent.putExtra("Position",et_positon.getText().toString());
//        intent.putExtra("CarNum",province+A2Z+et_num.getText().toString());
        time = getIntent().getStringExtra("Time");
        carNum = getIntent().getStringExtra("CarNum");
        position = getIntent().getStringExtra("Position");
        code = getIntent().getStringExtra("Code");
        status = getIntent().getStringExtra("Status");
        img = getIntent().getStringExtra("Img");


        tv_carNum.setText(carNum);
        tv_time.setText(time);
        tv_position.setText(position);

        ParkingSearchEntity.BoardBean bean = gson.fromJson(img, ParkingSearchEntity.BoardBean.class);
        list_img = new ArrayList<>();
        for (int i = 0; i < bean.getWFimg().size(); i++) {
            list_img.add(bean.getWFimg().get(i).getImg());
        }

        if (status.equals("1")) {
            tv_status.setText("已处理");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.green));
        }else {
            tv_status.setText("未处理");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.crimson));
        }

        tv_title.setText("罚单详情");
        iv_back.setOnClickListener(this);
        tv_print.setOnClickListener(this);
        tv_next.setOnClickListener(this);

        initList();
    }

    private void initList() {
        adapter = new ImageListRvAdapter(list_img,mContext);
        RecyclerView.LayoutManager manager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnImagelickListener(new ImageListRvAdapter.OnImagelickListener() {
            @Override
            public void onImageClick(int p) {
                Intent intent = new Intent(mContext,ImageActivity.class);
                intent.putExtra("url",list_img.get(p));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_parkingDetail_print:
                print();
                break;
            case R.id.tv_parkingDetail_next:
                Intent intent = new Intent(mContext, IllegalParkingTakePhotoActivity.class);
                startActivity(intent);
                break;

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
                                }
                            });
                        } else {
                            ToastUtils.showToast(mContext, "请先连接名为“Printer001”匹配码为“0000”的打印机，再进行操作！");
                        }
                    }
                };

                Intent intent = new Intent(this, XprinterService.class);

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
        byte[] num = strTobytes("编号：" + code);
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
        byte[] data_province = strTobytes(String.valueOf(carNum.charAt(0)));
        list.add(data_province);
        //车牌
        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(1));
        byte[] data_carNum = strTobytes(carNum.substring(1));
        list.add(data_carNum);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        byte[] data_1 = strTobytes("号牌号码的机动车驾驶人：");
        list.add(data_1);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));
        byte[] data_content = strTobytes(String.format("    该车辆于%s，在%s实施的停车行为违反了" +
                "《道路交通安全法》第56条之规定。请您持本通知书，驾驶证，行驶证在15日内，到", time, position));
        list.add(data_content);

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
        byte[] data_p = strTobytes("海盐县五原街道出海路与公园路交界口");
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

        list.add(DataForSendToPrinterPos58.printAndFeedForward(7));


        return list;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (conn != null) {
//            conn.onServiceConnected();
            unbindService(conn);
        }
    }

}
