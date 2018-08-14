package smartenforce.aty.function1;


import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import smartenforce.audio.AudioPlayManager;
import smartenforce.audio.AudioRecordManager;
import smartenforce.audio.IAudioPlayListener;
import smartenforce.base.ShowTitleActivity;
import smartenforce.intf.PermissonCallBack;


public abstract class AudioActivity extends ShowTitleActivity {


   //开始录音毫秒数
    private long start_index=0;
    //结束录音毫秒数
    private long end_index=0;
    //保存语音文件目录
    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/haiyan/voice");



    protected void stopVoice(ImageView imv_voice) {
        AudioRecordManager.getInstance(aty).stopRecord();
        end_index=System.currentTimeMillis();
         final Uri  audioUri=AudioRecordManager.getInstance(aty).getmAudioPath();
        boolean isRecordOK=end_index-start_index>=5000;
        imv_voice.setVisibility(isRecordOK? View.VISIBLE:View.GONE);
        if (isRecordOK){
            imv_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVioce(audioUri);
                }
            });
        }else{
            show("录音时间低于5秒,请重录");

        }

    }

    public  void playVioce(String filePath){
       try{
         playVioce(Uri.parse(filePath));
       } catch(NullPointerException e){
           show("语音地址不存在");
       }

    }

    private void playVioce(Uri uri) {
             if (uri==null||uri.getPath().length()==0){
                 show("语音地址不存在");
             }else{
                 AudioPlayManager.getInstance().startPlay(aty, uri, new IAudioPlayListener() {
                     @Override
                     public void onStart(Uri var1) {
                         //开播（一般是开始语音消息动画）
                     }

                     @Override
                     public void onStop(Uri var1) {
                         //停播（一般是停止语音消息动画）
                     }

                     @Override
                     public void onComplete(Uri var1) {
                         //播完（一般是停止语音消息动画）
                     }
                 });

             }

    }

    protected void startVoiceRecord() {
        requestPermissionGroup(Pid.AUDIO, new PermissonCallBack() {
            @Override
            public void onPerMissionSuccess() {
                requestPermissionGroup(Pid.FILE, new PermissonCallBack() {
                    @Override
                    public void onPerMissionSuccess() {
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        start_index=System.currentTimeMillis();
                        AudioRecordManager.getInstance(aty).setAudioSavePath(dir.getAbsolutePath());
                        AudioRecordManager.getInstance(aty).startRecord();


                        //将要取消录音（参与微信手指上滑）
                       // AudioRecordManager.getInstance(MainActivity.this).willCancelRecord();

//继续录音（参与微信手指上滑后加下滑回到原位）
                       // AudioRecordManager.getInstance(MainActivity.this).continueRecord();

//停止录音
                       // AudioRecordManager.getInstance(MainActivity.this).stopRecord();

//销毁录音
                       // AudioRecordManager.getInstance(MainActivity.this).destroyRecord();


                    }
                });
            }
        });

    }




}
















