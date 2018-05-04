package com.kas.clientservice.haiyansmartenforce.tcsf.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kas.clientservice.haiyansmartenforce.tcsf.util.CameraManager;


/**
 * 车牌预览view
 */
public class EasyPRPreSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Camera.PictureCallback rawCallback;
    private Camera.ShutterCallback shutterCallback;
    private Camera.PictureCallback pictureCallback;

    private Rect                   mastLayerFrame;//选取框Rect
    private OnPictureTakenListener pictureTakenListener;    //拍照回调

    private boolean isOnRecognizedRestart = true;//识别完成是否重新开始
    private boolean isRecognized          = true;//防止连续识别，做个标识
    private boolean isInit;
    private Context context;

    public EasyPRPreSurfaceView(Context context) {
        this(context, null);
    }

    public EasyPRPreSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyPRPreSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        setDrawingCacheEnabled(false);

    }

    /**
     * 设置拍照监听
     */
    public EasyPRPreSurfaceView setPictureTakenListener(OnPictureTakenListener pictureTakenListener) {
        this.pictureTakenListener = pictureTakenListener;
        return this;
    }

    /**
     * 获取结果后是否重新开始取景
     */
    public EasyPRPreSurfaceView setIsOnRecognizedRestart(boolean isOnRecognizedRestart) {
        this.isOnRecognizedRestart = isOnRecognizedRestart;
        return this;
    }

    /**
     * 设置选取框，选取框大小需要和{@link EasyPRPreViewMaskLayer#setMastLayerFrame(Rect)}的Rect大小一样
     */
    public EasyPRPreSurfaceView setMastLayerFrame(Rect frame) {
        this.mastLayerFrame = frame;
        return this;
    }



    /**
     * 车牌识别执行
     */
    public void recognize() {
        if (isRecognized) {
            if (shutterCallback == null || rawCallback == null || pictureCallback == null) {
                throw new IllegalStateException("没有调用initPreView()");
            }

            isRecognized = false;
            if (CameraManager.get().getCamera() != null) {
                CameraManager.get().getCamera().takePicture(shutterCallback, rawCallback, pictureCallback);
            }
        }
    }

    /**
     * 开始执行， 在Activity或者Fragment 的 onStart中调用
     */
    public void onStart() {
        if (!isInit) {
            init();
        }

        CameraManager.get().openDevice(getHolder(),context);
    }

    /**
     * 释放Camera，在onStop中调用
     */
    public void onStop() {
        CameraManager.get().closeDvice();
    }




    /**
     * 开始执行取景
     */
    private void init() {
        if (!isInit) {
            isInit = true;

            if (mastLayerFrame == null) {
                mastLayerFrame = EasyPrBiz.getDefRectFrame(context);
            }

            rawCallback = new Camera.PictureCallback() {
                public void onPictureTaken(byte[] data, Camera camera) {

                }
            };

            shutterCallback = new Camera.ShutterCallback() {
                public void onShutter() {

                }
            };

            pictureCallback = new Camera.PictureCallback() {
                public void onPictureTaken(byte[] data, Camera camera) {
                    if (CameraManager.get().getCamera() == null || getContext() == null || ((Activity) getContext()).isFinishing()) {
                        return;
                    }

                    if (CameraManager.get().getCamera() != null) {
                        CameraManager.get().getCamera().stopPreview();
                    }
                    if (pictureTakenListener != null) {
                        pictureTakenListener.onPictureTaken(data);
                    }

                        }
                    } ;
                    getHolder().addCallback(this);
                }
            }

            @Override
            public void surfaceCreated (SurfaceHolder holder){
                onStart();
            }

            @Override
            public void surfaceChanged (SurfaceHolder holder,int format, int w, int h){
                if (CameraManager.get().getCamera() != null) {
                    try {
                        // 实现自动对焦
                        CameraManager.get().getCamera().autoFocus(new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean success, Camera arg1) {
                                if (success) {
                                    CameraManager.get().reStartPreView(getHolder());
                                    CameraManager.get().getCamera().cancelAutoFocus();// 只有加上了这一句，才会自动对焦。
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceDestroyed (SurfaceHolder holder){
                onStop();
            }



      public void onFail(){
          isRecognized = true;
          if (isOnRecognizedRestart && CameraManager.get().getCamera() != null) {
              CameraManager.get().getCamera().startPreview();
          }

      }

            public interface OnPictureTakenListener {
                //拍照完成
                void onPictureTaken(byte[] data);
            }

        }
