package videotalk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

import smartenforce.bean.CaseBean;
import smartenforce.bean.CitizenBean;
import smartenforce.bean.EnforceListBean;
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.bean.GoodListBean;
import smartenforce.bean.InvestigateBean;
import smartenforce.bean.SourseBean;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private ItemListener listener;
    private Context context;
    private List<UserVideoBean> list;
    private LayoutInflater inflater;

    public UserListAdapter(List<UserVideoBean> list, Context ctx) {
        this.list = list;
        this.context = ctx;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user_list_test, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UserVideoBean userVideoBean=  list.get(position);
        holder.tev_userName.setText(userVideoBean.userID);
        holder.cbx_user.setChecked(userVideoBean.isSelect);
        holder.llt_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tev_userName;
        CheckBox cbx_user;
        LinearLayout llt_item;


        public ViewHolder(View itemView) {
            super(itemView);
            llt_item= (LinearLayout) itemView.findViewById(R.id.llt_item);
            tev_userName= (TextView) itemView.findViewById(R.id.tev_userName);
            cbx_user= (CheckBox) itemView.findViewById(R.id.cbx_user);

        }
    }

    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

}
