package smartenforce.widget.view;

import android.view.MotionEvent;

public interface OnSingleFlingListener {
	boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
