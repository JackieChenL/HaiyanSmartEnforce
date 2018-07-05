package smartenforce.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;


public class WaterMaskTextView extends AppCompatTextView {

    private int mDegree = 315; // 旋转角度

    public WaterMaskTextView(Context context) {
        this(context, null);
    }

    public WaterMaskTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterMaskTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setGravity(Gravity.CENTER); // 获取自定义属性
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight()+20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        // 位移,保持文字居中
        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        // 以文字中心轴旋转
        canvas.rotate(mDegree, this.getWidth() / 2f, this.getHeight() / 2f);
        super.onDraw(canvas);
        canvas.restore();

    }


    /**
     * 设置旋转角度 * @param degree
     */
    public void setDegree(int degree) {
        this.mDegree = degree;
    }
}
