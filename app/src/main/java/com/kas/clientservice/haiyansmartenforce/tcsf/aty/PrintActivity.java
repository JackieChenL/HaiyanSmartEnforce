package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.TitleActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.PermissonCallBack;

import net.xprinter.service.XprinterService;
import net.xprinter.xpinterface.IMyBinder;
import net.xprinter.xpinterface.ProcessData;
import net.xprinter.xpinterface.UiExecute;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 打印页面，接收一段打印数据
 */
public abstract class PrintActivity extends TitleActivity {
    private IMyBinder binder;
    private ServiceConnection conn;
    private BluetoothDevice device;
    private boolean isConnected = false;
    private  List<byte[]> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_print);
//        bodyArray=getIntent().getStringArrayExtra("body");
//        tev_print = (TextView) findViewById(R.id.tev_print);
//        tev_print.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                doCheckConnection();
//            }
//        });
    }


    /**
     * 打印相关信息:打印之前上传数据到服务器
     */
    private void doPrint() {

        binder.writeDataByYouself(new UiExecute() {

            @Override
            public void onsucess() {
                 onPrintSuccess();
//                finish();
            }

            @Override
            public void onfailed() {
                isConnected = false;
                show( "打印失败，请重新打印");
            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {
//                ArrayList<byte[]> list = (new PrintUtil("停车收费小票", null, bodyArray, getFooterString(null))).getData();
                return list;
            }
        });


    }

    protected String[] getFooterString() {

        String[] footer = new String[]{"\n" +
                "温馨提醒：您的车辆已经停入海盐县停车收费点，如您已产生停车管理费，请向附近的收费管理员缴纳相应的停车管理费，并索要发票，服从车辆停放管理，做有道德好公民。",
                "收费标准：盐政发〔2013〕51号及盐政函〔2013〕129号，30分钟内免费停车，第1小时3元，第二小时以后5元/小时",
                "联系电话：" + "0573-86113170", "海盐县停车管理中心"};

        return footer;
    }



    /**
     * 打印之前先连接打印机
     */
    protected void doCheckConnection(List<byte[]> list) {
        this.list=list;
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, Pid.LOCATION, new PermissonCallBack() {
            @Override
            public void onPerMissionSuccess() {
                if (!isConnected) {
                    connectBle();
                } else {
                    doPrint();
                }
            }
        });

    }

    private void connectBle() {
        conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected: ");
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //绑定成功
                binder = (IMyBinder) service;
                Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
                if (devices.size() > 0) {
                    for (Iterator<BluetoothDevice> iterator = devices.iterator(); iterator.hasNext(); ) {
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                        // Log.i(TAG, "设备：" + bluetoothDevice.getName() + " " + bluetoothDevice.getAddress());
                        if (bluetoothDevice.getName().trim().equals("printer001")) {
                            device = bluetoothDevice;
                        }
                    }
                } else {
                    show("请先连接名为“Printer001”匹配码为“0000”的打印机，再进行操作！");
                }
//                    Log.i(TAG, "device: "+device.getAddress());
                if (device != null) {
                    binder.connectBtPort(device.getAddress().trim(), new UiExecute() {
                        @Override
                        public void onsucess() {
                            isConnected = true;
                            binder.acceptdatafromprinter(new UiExecute() {

                                @Override
                                public void onsucess() {
//                                        Intent intent = new Intent(aty, XprinterService.class);
//                                        bindService(intent, conn, BIND_AUTO_CREATE);
                                }

                                @Override
                                public void onfailed() {
                                    isConnected = false;
//                                    ToastUtil.show(aty, "连接已断开");
                                }
                            });
                            doPrint();
                        }

                        @Override
                        public void onfailed() {
                            isConnected = false;
                            show( "打印机连接失败");
                        }
                    });
                } else {
                    isConnected = false;
                    show("请先连接名为“Printer001”匹配码为“0000”的打印机，再进行操作！");
                }
            }
        };

        Intent intent = new Intent(aty, XprinterService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (conn != null) {
            if (isConnected) {
                unbindService(conn);
            }
        }
    }

    public  abstract void onPrintSuccess();


    /**
     * 不提交无法打印，打印完成清屏并改变状态
     * @param isState 提交按钮状态
     * @param left 打印按钮
     * @param right 提交按钮
     */
    protected void changeState(boolean isState ,View left,View right){
        if (isState){
            right.setEnabled(true);
            right.setBackgroundColor(getResources().getColor(R.color.app_original_blue));
            left.setEnabled(false);
            left.setBackgroundColor(getResources().getColor(R.color.grey_100));
        }else{
            left.setEnabled(true);
            left.setBackgroundColor(getResources().getColor(R.color.app_original_blue));
            right.setEnabled(false);
            right.setBackgroundColor(getResources().getColor(R.color.grey_100));
        }

    }

}
