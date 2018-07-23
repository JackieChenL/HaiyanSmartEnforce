package smartenforce.widget.view;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import smartenforce.widget.XlistRecycleView;

public class MyXRecyclerView1 extends XlistRecycleView {


    public MyXRecyclerView1(Context context) {
        this(context, null);
    }

    public MyXRecyclerView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //刷新功能默认不开启
    public MyXRecyclerView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(layoutManager);
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(true);
        setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        setLoadingMoreProgressStyle(ProgressStyle.Pacman);
    }


    //无论请求结果如何调用此方法，避免开启下拉刷新忘记状态恢复
    public void onComplete() {
        refreshComplete();
        loadMoreComplete();
    }


}
