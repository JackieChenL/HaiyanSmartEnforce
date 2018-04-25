package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
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
    private static final int BLUETOOTH_SEARCH = 100;
    private static final int BLUETOOTH_SEARCH_TIMEOUT = 10000;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_parkingDetail_print)
    TextView tv_print;
    @BindView(R.id.tv_parkingDetail_baclk)
    TextView tv_back;

    IMyBinder binder;
    ServiceConnection conn;
    BluetoothDevice device;
    private boolean isConnected = false;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    adapter.stopLeScan(mLeScanCallback);
                    break;
            }
        }
    };

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

        tv_title.setText("罚单详情");
        iv_back.setOnClickListener(this);
        tv_print.setOnClickListener(this);
        tv_back.setOnClickListener(this);
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
            case R.id.tv_parkingDetail_baclk:
                finish();
                break;

        }
    }

    BluetoothAdapter adapter;

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
            }else {
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
    public static byte[] strTobytes(String str){
        byte[] b=null,data=null;
        try {
            b = str.getBytes("utf-8");
            data=new String(b,"utf-8").getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }
    public List<byte[]> formAndPrint(){
        ArrayList<byte[]> list = new ArrayList<byte[]>();
        list.add(DataForSendToPrinterPos58.initializePrinter());
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
        byte[] num = strTobytes("编号：0000001");
        list.add(DataForSendToPrinterPos58.selectAlignment(1));
        list.add(num);
//                list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        list.add(DataForSendToPrinterPos58.printAndFeedLine());
        //空一行
        list.add(DataForSendToPrinterPos58.printAndFeedForward(1));

        //省
        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        byte[] data_province = strTobytes("浙");
        list.add(data_province);
        //车牌
        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(1));
        byte[] data_carNum= strTobytes("FF0001");
        list.add(data_carNum);

        list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
        list.add(DataForSendToPrinterPos58.selectOrCancelUnderlineModel(0));
        byte[] data_1= strTobytes("号牌号码的机动车驾驶人：");
        list.add(data_1);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());

        String time = "2018-1-1";
        String position = "南湖大道";
        byte[] data_content= strTobytes(String.format("  该车辆于%s，在%s实施的停车行为违反了《道路交通安全法》第56条之规定。请您持本通知书，驾驶证，行驶证在15日内，到嘉兴市南湖区综合行政执法局或通过微信公众号关注“南湖综合执法”及南湖区各乡镇交警中队违法处理点处理。",time,position));
        list.add(data_content);
        list.add(DataForSendToPrinterPos58.printAndFeedLine());


        return list;
    }




    @Override
    protected void onStop() {
        super.onStop();
        if (conn != null) {
            unbindService(conn);
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice d, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String address = d.getAddress();
                            Log.i("BlueTooth", "name=" + d.getName() + "   address=" + address);
                        }
                    });
                }
            };
}
