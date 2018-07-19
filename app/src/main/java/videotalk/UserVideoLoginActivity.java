package videotalk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.kas.clientservice.haiyansmartenforce.R;

import java.util.ArrayList;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import smartenforce.base.ShowTitleActivity;
import smartenforce.intf.ItemListener;
import videotalk.im.SealUserInfoManager;


public class UserVideoLoginActivity extends ShowTitleActivity {

private EditText edt_userId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_video_login_test);

    }




    @Override
    protected void findViews() {
        edt_userId= (EditText) findViewById(R.id.edt_userId);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("视频登录");


    }

    public void videoLogin(View v){
      String userId= getText(edt_userId);
        String token="";
       if (isEmpty(userId)){
           warningShow("请输入用户id");
       }else{
           for (int i=0;i<app.list.size();i++){
               UserVideoBean userVideoBean=app.list.get(i);
               if (userVideoBean.userID.equals(userId)){
                   token=userVideoBean.userToken;
                   break;
               }
           }
           if (isEmpty(token)){
               warningShow("目前测试用户只能为001-009");
           }else{
              login(token);
           }
       }




    }

    private void login(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                warningShow("onTokenIncorrect" );
                SealUserInfoManager.getInstance().reGetToken();
            }

            @Override
            public void onSuccess(String s) {
                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit().putString("loginid", s).commit();

                app.currentUserID=s;
                startActivity(new Intent(aty,UserListActivity.class));
             

            }

            @Override
            public void onError(final RongIMClient.ErrorCode e) {
                warningShow("ConnectCallback connect onError-ErrorCode=" + e);
            }
        });
    }




}
















