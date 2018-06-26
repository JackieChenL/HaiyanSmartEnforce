package smartenforce.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;


import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

import smartenforce.adapter.ShowTitleAdapter;
import smartenforce.intf.ItemListener;
import smartenforce.intf.ListItemClickLisener;
import smartenforce.util.UIUtil;


public class ListDialog extends Dialog {
    private TextView titleTxt;
    private RecyclerView rcy_list;
    private List<Object> objList;

    private Context mContext;
    private ListItemClickLisener itemListener;
    private String title;

    private ShowTitleAdapter adapter;

    public ListDialog(Context context, List<Object> objList, ListItemClickLisener itemListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.objList = objList;
        this.itemListener = itemListener;
    }


    public ListDialog setTitle(String title) {
        this.title = title;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_dialog);
        this.getWindow().setLayout((int) (UIUtil.getScreenWidth(mContext) * 0.85), this.getWindow().getAttributes().height);
        setCanceledOnTouchOutside(false);
        titleTxt = (TextView) findViewById(R.id.tev_title);
        rcy_list = (RecyclerView) findViewById(R.id.rcy_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rcy_list.setLayoutManager(layoutManager);
        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);

        }
        adapter = new ShowTitleAdapter(objList, mContext);
        adapter.setListener(new ItemListener() {
            @Override
            public void onItemClick(int P) {
                if (itemListener != null) {
                    itemListener.onItemClick(P, objList.get(P));
                }
                dismiss();
            }
        });
        rcy_list.setAdapter(adapter);
    }


}
