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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import smartenforce.aty.personrepay.MD5;
import smartenforce.bean.CaseBean;
import smartenforce.bean.CitizenBean;
import smartenforce.bean.EnforceListBean;
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.bean.GoodListBean;
import smartenforce.bean.InvestigateBean;
import smartenforce.bean.SourseBean;
import smartenforce.impl.MyStringCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private ItemListener listener;
    private Context context;
    private List<GroupUserBean> list;
    private LayoutInflater inflater;
    private Map<Integer,String> map=new HashMap<>();

    private String url="http://api.cn.ronghub.com/user/checkOnline.json";
    private String APP_KEY="m7ua80gbmj3om";
    private String APP_SECRET="PTIEotqkVnfcV";

    public UserListAdapter(List<GroupUserBean> list, Context ctx) {
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
        GroupUserBean userVideoBean=  list.get(position);
        holder.tev_userName.setText(userVideoBean.name);
        holder.cbx_user.setChecked(userVideoBean.isSelect);
        bindNameWithStatus(position,holder.tev_userName,userVideoBean.name);
        holder.llt_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(position);
                }
            }
        });

    }

    private void bindNameWithStatus(int position, TextView tev_userName,String currentText) {
        if (map.containsKey(position)){
            tev_userName.setText(currentText+map.get(position));
        }else{
            getNetLiveStatus(position,tev_userName,currentText);
        }
    }
    private String genNonceStr() {
        Random random = new Random();
        return String.valueOf(random.nextInt(10000));
    }


    private void getNetLiveStatus(final int position,final TextView tev_userName, final String currentText) {
       String Nonce=genNonceStr();
       String Timestamp=String.valueOf(System.currentTimeMillis());
        OkHttpUtils.post().url(url)
                .addParams("userId",list.get(position).id)
                .addHeader("App-Key",APP_KEY)
                .addHeader("Nonce",Nonce)
                .addHeader("Timestamp",Timestamp)
                .addHeader("Signature",SHA1.encode(APP_SECRET+Nonce+Timestamp))
                .build().execute(new MyStringCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                map.put(position,"[离线]");
                tev_userName.setText(currentText+"[离线]");
            }

            @Override
            public void onResponse(String response, int id) {
                super.onResponse(response,id);
                UserLiveStatusBean  userLiveStatusBean= JSONObject.parseObject(response,UserLiveStatusBean.class);
                map.put(position,userLiveStatusBean.getStatus());
                tev_userName.setText(currentText+userLiveStatusBean.getStatus());

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
