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
        int WaterTextH = UIUtil.dip2px(context, 80);
        int WaterTextW =  W / 2;
        int count = H / WaterTextH + 1;
        for (int i = 0; i < count; i++) {
            int currentWaterTextW = (i % 3 + 1) * WaterTextW;
            WaterMaskTextView textView = new WaterMaskTextView(context);
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(currentWaterTextW, WaterTextH));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            textView.setTextColor(Color.parseColor("#AF00BFFF"));
            textView.setDegree(350);
            textView.setText("海盐县综合行政执法局\n 严禁非法获取个人信息["+userName+"]");
            this.addView(textView, i);
        }

    }
}
