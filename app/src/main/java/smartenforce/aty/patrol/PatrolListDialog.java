package smartenforce.aty.patrol;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import smartenforce.intf.OnItemClickListener;
import smartenforce.util.UIUtil;

import java.util.List;

public class PatrolListDialog extends Dialog {
    private TextView titleTxt;
    private RecyclerView rcy_list;

    private Context mContext;
    private String title;
    private PatrolTitleAdapter adapter;

    public PatrolListDialog(Context context, List<Object> objList, final OnItemClickListener onItemClickListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        adapter = new PatrolTitleAdapter(objList, context);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int p, Object obj) {
                dismiss();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(p, obj);

                }
            }
        });
    }


    public PatrolListDialog setTitle(String title) {
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
        rcy_list.setAdapter(adapter);
    }


}
