package smartenforce.baidu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.ShowTitleActivity;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.PermissonCallBack;
import smartenforce.util.ImgUtil;


public class BaiduMapActivity extends ShowTitleActivity {
  private MapView map_view;
    private BaiduMap baiduMap;
    private MapStatus mMapStatus;
    private MapStatusUpdate mMapStatusUpdate ;
    private BDLocationListener listener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidumap);
    }

    protected void findViews() {
        map_view= (MapView) findViewById(R.id.map_view);
        baiduMap= map_view.getMap();
        listener=new BDLocationListener(aty);
        listener.setListener(new BDLocationListener.onLocationSuccess() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mMapStatus = new MapStatus.Builder()
                        .target(new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude()))
                        .zoom(20)
                        .build();
                mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                baiduMap.setMapStatus(mMapStatusUpdate);
            }
        });
        requestPermissionGroup(Pid.LOCATION, new PermissonCallBack() {
            @Override
            public void onPerMissionSuccess() {
                listener.startLocation();
            }
        });

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("选择截屏区域");
        tev_title_right.setText("确定");
        tev_title_right.setOnClickListener(new NoFastClickLisener() {
            @Override
            public void onNofastClickListener(View v) {
                baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                       String file_path= ImgUtil.saveBitmap(bitmap);
                        Intent it = new Intent();
                        setResult(RESULT_OK, it.putExtra("path", file_path));
                        finish();
                    }
                });
            }
        });



    }

    @Override
    protected void onPause() {
        map_view.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        map_view.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        map_view.onDestroy();
        super.onDestroy();
    }























}
