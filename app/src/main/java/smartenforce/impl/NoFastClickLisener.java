package smartenforce.impl;

import android.view.View;

import java.util.Calendar;

/**
 * 快速点击事件特殊处理
 */
public abstract  class NoFastClickLisener implements View.OnClickListener {
    private static final long interval = 1500l;
    private long lastClick = 0;

    @Override
    public void onClick(View v) {
        long currentMillis = Calendar.getInstance().getTimeInMillis();
        if (currentMillis - lastClick > interval) {
            onNofastClickListener(v);
            lastClick = currentMillis;
        }else {
//            ToastUtil.show(v.getContext(),"请不要重复快速点击");
        }

    }

    public abstract void onNofastClickListener(View v);
}
