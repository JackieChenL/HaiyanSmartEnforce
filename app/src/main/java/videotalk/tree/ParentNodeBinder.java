package videotalk.tree;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import smartenforce.impl.MyStringCallBack;
import smartenforce.util.LogUtil;
import videotalk.normal.SHA1;
import videotalk.normal.UserLiveStatusBean;
import videotalk.widget.TreeNode;
import videotalk.widget.TreeViewBinder;


public class ParentNodeBinder extends TreeViewBinder<ParentNodeBinder.ViewHolder> {
    private String url="http://api.cn.ronghub.com/user/checkOnline.json";
    private String APP_KEY="m7ua80gbmj3om";
    private String APP_SECRET="PTIEotqkVnfcV";
    private Map<String,Boolean> map=new LinkedHashMap<>();



    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        ParentNode parentNodeNode = (ParentNode) node.getContent();
        TreeBean treeBean=parentNodeNode.treeBean;
        holder.tev_userName.setText(treeBean.name);
        holder.tev_userName.setTextColor(Color.parseColor("#333333"));
        if (node.isLeaf()){
            holder.cbx_user.setVisibility(View.VISIBLE);
            holder.cbx_user.setChecked(treeBean.isChecked);
            if (map.containsKey(treeBean.id+"_"+treeBean.pid)){
                boolean isOnLine=map.get(treeBean.id+"_"+treeBean.pid);
                holder.imv_live_status.setBackgroundResource(isOnLine?R.drawable.video_online:R.drawable.video_offline);
                holder.imv_live_status.setVisibility(View.VISIBLE);
                holder.tev_userName.setTextColor(Color.parseColor(isOnLine?"#333333":"#cccccc"));

            }else {
                getNetLiveStatus(treeBean, holder.imv_live_status,holder.tev_userName);
            }
        }else{
            holder.imv_live_status.setVisibility(View.GONE);
            holder.cbx_user.setVisibility(View.GONE);

        }





    }

    @Override
    public int getLayoutId() {
        return R.layout.item_user_list_dir;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        TextView tev_userName;
        CheckBox cbx_user;
        LinearLayout llt_item;
        ImageView imv_live_status;

        public ViewHolder(View rootView) {
            super(rootView);
            llt_item= (LinearLayout) rootView.findViewById(R.id.llt_item);
            tev_userName= (TextView) rootView.findViewById(R.id.tev_userName);
            cbx_user= (CheckBox) rootView.findViewById(R.id.cbx_user);
            imv_live_status= (ImageView) rootView.findViewById(R.id.imv_live_status);
        }

        public CheckBox getCheckBox(){
            return cbx_user;
        }
    }





    private void getNetLiveStatus(final TreeBean treeBean, final ImageView imv,final TextView tev) {
        String Nonce=String.valueOf(new Random().nextInt(10000));
        String Timestamp=String.valueOf(System.currentTimeMillis());
        OkHttpUtils.post().url(url)
                .addParams("userId",treeBean.id+"")
                .addHeader("App-Key",APP_KEY)
                .addHeader("Nonce",Nonce)
                .addHeader("Timestamp",Timestamp)
                .addHeader("Signature", SHA1.encode(APP_SECRET+Nonce+Timestamp))
                .build().execute(new MyStringCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                imv.setVisibility(View.VISIBLE);
                imv.setBackgroundResource(R.drawable.video_offline);
                tev.setTextColor(Color.parseColor("#cccccc"));
            }

            @Override
            public void onResponse(String response, int id) {
                super.onResponse(response,id);
                UserLiveStatusBean userLiveStatusBean= JSONObject.parseObject(response,UserLiveStatusBean.class);
//                Log.e("userLiveStatusBean",userLiveStatusBean.toString());
//                Log.e("treeBean",treeBean.toString());

                boolean isOnline=userLiveStatusBean.status.equals("1");
                 map.put(treeBean.id+"_"+treeBean.pid,isOnline);
                imv.setVisibility(View.VISIBLE);
                imv.setBackgroundResource(isOnline?R.drawable.video_online:R.drawable.video_offline);
                tev.setTextColor(Color.parseColor(isOnline?"#333333":"#cccccc"));


            }
        });

    }
}