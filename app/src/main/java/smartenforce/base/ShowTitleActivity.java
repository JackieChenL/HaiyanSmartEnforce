package smartenforce.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.R;

import java.io.File;

import smartenforce.intf.PermissonCallBack;
import smartenforce.util.FileProvider7;


public abstract class ShowTitleActivity extends CommonActivity {
    protected TextView tev_title, tev_title_right;
    protected ImageView imv_back;
    protected File file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        findViews();
        tev_title = (TextView) findViewById(R.id.tev_title);
        imv_back = (ImageView) findViewById(R.id.imv_back);
        tev_title_right = (TextView) findViewById(R.id.tev_title_right);
        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initDataAndAction();
    }

    protected abstract void findViews();

    protected abstract void initDataAndAction();


    protected void takePhoto(final int CODE) {
        requestPermissionGroup(Pid.FILE, new PermissonCallBack() {
            @Override
            public void onPerMissionSuccess() {
                requestPermissionGroup(Pid.CAMERA, new PermissonCallBack() {
                    @Override
                    public void onPerMissionSuccess() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
//                                + "/kas/img/" + System.currentTimeMillis() + ".jpg");
//                        file.getParentFile().mkdirs();
                        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                                + "/haiyan/img");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        file = new File(dir, System.currentTimeMillis() + ".jpg");
                        Uri uri = FileProvider7.getUriForFile(aty, file);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, CODE);
                    }
                });
            }
        });

    }


//    protected void openAlbum(final int CODE, final ArrayList<String> list, final int Count) {
//        Album.album(aty)
//                .requestCode(CODE)
//                .toolBarColor(ContextCompat.getColor(aty, R.color.app_original_blue))
//                .statusBarColor(ContextCompat.getColor(aty, R.color.black))
//                .selectCount(Count)
//                .columnCount(3)
//                .camera(true)
//                .checkedList(list)
//                .start();
//    }
    //定义的时候打开album请求码和相机拍照码差3
//    protected void openPhotoStyle(final int CODE, final ArrayList<String> list,final int Count) {
//        new AlertView(null, null, "取消", null, new String[]{"拍照", "相册+拍照"}, aty, AlertView.Style.ActionSheet, new OnItemClickListener() {
//            @Override
//            public void onItemClick(Object o, int position) {
//                log("P:"+position);
//                if (position==0){
//                    takePhoto(CODE);
//                }else if (position==1){
//                    openAlbum(CODE+3,list,Count);
//                }
//
//            }
//        }).show();
//    }


    protected AlertView getShowAlert(String headerTxt, final String[] array, final TextView tev) {
        return new AlertView(headerTxt, null, null, null, array, aty, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                tev.setText(array[position]);
            }
        });
    }

    //级联的静态选择，每次选择一级列表，要把后面级联关系清空
    protected AlertView getShowAlertRelevance(String headerTxt, final String[] array, final TextView tev, final TextView tev_next) {
        return new AlertView(headerTxt, null, null, null, array, aty, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                tev.setText(array[position]);
                tev_next.setText("");
            }
        });
    }


}
