package com.kas.clientservice.haiyansmartenforce.Utils;

import android.app.Application;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;


public class AppCrashUtils implements UncaughtExceptionHandler {
    private static AppCrashUtils mCrashHandler = new AppCrashUtils();
    private static Application mApplaction;

    private AppCrashUtils() {
    }

    public static void init(Application applaction) {
        mApplaction = applaction;
        Thread.setDefaultUncaughtExceptionHandler(mCrashHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            if (ex != null) {
               Log.e("CRASH",ex.toString());
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            ex.printStackTrace();

        }finally {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }





    private void showToast(final Throwable ex) throws InterruptedException {
        ex.printStackTrace();
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//
//                Looper.loop();
//            }
//        }.start();
        Thread.sleep(3000);
    }
}
