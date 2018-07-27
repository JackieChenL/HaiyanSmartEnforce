package videotalk;


import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONArray;
import com.kas.clientservice.haiyansmartenforce.User.UserInfo;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;
import smartenforce.impl.MyStringCallBack;
import videotalk.im.SealUserInfoManager;

public class VideoTalkUtils {

    private static VideoTalkUtils videoTalkUtils;
    private boolean isSuccessLogin = false;
    private int index = -1;
    private String erroMsg = "视屏组成员获取中，请稍后";
    private List<GroupUserBean> list = new ArrayList<>();
    private final static String[] arrays = new String[]{"330424", "330424001", "330424002", "330424003", "330424004", "330424101", "330424102", "330424103", "330424106", "330424001"};

    public List<GroupUserBean> getList() {
        return list;
    }


    public boolean isSuccessLogin() {
        return isSuccessLogin;
    }

    public String getErroMsg() {
        return erroMsg;
    }

    public static VideoTalkUtils getInstance() {
        if (videoTalkUtils == null) {
            synchronized (VideoTalkUtils.class) {
                if (videoTalkUtils == null) {
                    videoTalkUtils = new VideoTalkUtils();
                }
            }
        }
        return videoTalkUtils;
    }


    private void connectVideoTalk(final Context context, final String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                erroMsg = "onTokenIncorrect";
                isSuccessLogin = false;
            }

            @Override
            public void onSuccess(String s) {
                SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit().putString("loginid", s).commit();
                erroMsg = "";
                isSuccessLogin = true;
            }

            @Override
            public void onError(final RongIMClient.ErrorCode e) {
                erroMsg = "connect onError=" + e;
                isSuccessLogin = false;
            }
        });

    }


    public void exit() {
        isSuccessLogin = false;
        list.clear();
        SealUserInfoManager.getInstance().closeDB();
        RongIM.getInstance().logout();
    }

    public void getMemberList(final Context context) {
        if (isMatchs()) {
            OkHttpUtils.get().url("https://hyzhcg.hnzhzf.top/webim/userData.json")
                    .build().execute(new MyStringCallBack() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    erroMsg = "getMemberList：onError=" + e;
                    isSuccessLogin = false;
                }

                @Override
                public void onResponse(String response, int id) {
                    list.clear();
                    List<GroupUserBean> retList = JSONArray.parseArray(response, GroupUserBean.class);
                    list.addAll(retList);
                    doPrivateConnect(context);
                }
            });
        }
    }

    private void doPrivateConnect(Context context) {
        try {
            connectVideoTalk(context, list.get(index).token);
            list.remove(index);
        } catch (Exception e) {
            //为了防止以后出现异常，数组越界等崩溃
        }


    }

   //判断是否为视频权限用户组
    private boolean isMatchs() {
        UserInfo.SzcgBean szcg = UserSingleton.USERINFO.szcg;
        if (szcg != null) {
            for (int i = 0; i < arrays.length; i++) {
                if (arrays[i].equals(szcg.areacode)) {
                    index = i;
                    return true;
                }

            }
        }

        return false;
    }

    //视屏结束后需要把发起人列表选中状态重置
    public void resetList() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).isSelect = false;
        }

    }


}
