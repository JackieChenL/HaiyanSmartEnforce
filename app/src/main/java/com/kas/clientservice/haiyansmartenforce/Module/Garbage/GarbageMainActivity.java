package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.TiandiMapActivity;
import com.kas.clientservice.haiyansmartenforce.R;

public class GarbageMainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt_qrcode,bt_map,bt_doorplate,bt_huzhu,bt_search_code;
    Loginben loginben;
    Intent intent=new Intent();
    private String userName;
    private  String password;
    public static void main(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage_main);
        initRes();
        String login=getIntent().getStringExtra("loginben");

    }

    private void initRes() {
        bt_qrcode=(Button) findViewById(R.id.bt_qrcode);
        bt_qrcode.setOnClickListener(this);
        bt_map=(Button) findViewById(R.id.bt_map);
        bt_map.setOnClickListener(this);
        bt_doorplate=(Button) findViewById(R.id.bt_doorplate);
        bt_doorplate.setOnClickListener(this);
        bt_huzhu=(Button) findViewById(R.id.bt_huzhu);
        bt_huzhu.setOnClickListener(this);
        bt_search_code=(Button) findViewById(R.id.bt_search_code);
        bt_search_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_qrcode:
                IntentIntegrator integrator = new IntentIntegrator(GarbageMainActivity.this);
                //integrator.initiateScan();
                //IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setCaptureActivity(ScanActivity.class);
                integrator.setPrompt("请扫描二维码"); //底部的提示文字，设为""可以置空
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
                integrator.setBarcodeImageEnabled(true);//是否保留扫码成功时候的截图
                integrator.initiateScan();
                break;
            case R.id.bt_map:
                initGPS(0);
                intent.setClass(this,TiandiMapActivity.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.bt_doorplate:
                intent.setClass(this, Doorplate.class);
                startActivity(intent);
//                finish();
                break;
            case R.id.bt_huzhu:
                intent.setClass(this,HuzhuActivity.class);
                startActivity(intent);
//                finish();
                break;
            case R.id.bt_search_code:
                intent.setClass(this, CodeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String result = scanResult.getContents();
            if (result!=null&&!result.equals("")) {
                Log.e("HYN", result);
                Toast.makeText(GarbageMainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }
    private void initGPS(int value) {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("提示");
            dialog.setMessage("请打开GPS");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                        }
                    }
            );
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        } else {
            if (value == 1) {
                intent.setClass(this, TiandiMapActivity.class);
                startActivity(intent);
            }
        }
    }


}
