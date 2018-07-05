package smartenforce.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;

import smartenforce.util.UIUtil;


public class WaterMaskLayout extends LinearLayout {
    private Context context;

    public WaterMaskLayout(Context context) {
        this(context, null);
    }

    public WaterMaskLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterMaskLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        addWaterMaskView();
    }

    private void addWaterMaskView() {
        String userName = UserSingleton.getInstance().getUserAccount(context);
        int W = UIUtil.getScreenWidth(context);
        int H = UIUtil.getScreenHeight(context);
        int WaterTextH = UIUtil.dip2px(context, 60);
        int WaterTextW = UIUtil.dip2px(context, W / 3);
        int count = H / WaterTextH + 1;
        for (int i = 0; i < count; i++) {
            int currentWaterTextW = (i % 3 + 1) * WaterTextW;
            WaterMaskTextView textView = new WaterMaskTextView(context);
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(currentWaterTextW, WaterTextH));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            textView.setTextColor(Color.parseColor("#00BFFF"));
            textView.setText(userName);
            this.addView(textView, i);
        }

    }
}
