package videotalk.tree;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import smartenforce.impl.MyStringCallBack;
import videotalk.normal.VideoTalkUtils;

public class TreeUtils {

    public boolean isSuccessLogin() {
        return isSuccessLogin;
    }

    public String getErroMsg() {
        return erroMsg;
    }

    private boolean isSuccessLogin = false;
    private String erroMsg = "视屏登录中，请稍后";

    private static TreeUtils utils;

    public static TreeUtils getInstance() {
        if (utils == null) {
            synchronized (TreeUtils.class) {
                if (utils == null) {
                    utils = new TreeUtils();
                }
            }
        }
        return utils;
    }


    public  List<TreeBean> bulid(List<TreeBean> TreeBeans) {

        List<TreeBean> rootNodes = new ArrayList<>();

        for (TreeBean TreeBean : TreeBeans) {
            //一级节点
            if (TreeBean.level==0) {
                rootNodes.add(TreeBean);
            }
            for (TreeBean it : TreeBeans) {
                if (it.pid == TreeBean.id) {
                    if (TreeBean.childList == null) {
                        TreeBean.childList=new ArrayList<>();
                    }
                    TreeBean.childList.add(it);
                }
            }

        }
        return rootNodes;
    }


    /**
     * 使用递归方法建树
     * @param TreeBeans
     * @return
     */
    public  List<TreeBean> buildByRecursive(List<TreeBean> TreeBeans) {
        List<TreeBean> trees = new ArrayList<>();

        for (TreeBean TreeBean : TreeBeans) {
            if (TreeBean.level==0) {
                trees.add(findChildren(TreeBean,TreeBeans));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param TreeBeans
     * @return
     */
    public  TreeBean findChildren(TreeBean TreeBean,List<TreeBean> TreeBeans) {
        for (TreeBean it : TreeBeans) {
            if(TreeBean.id==it.pid) {
                if (TreeBean.childList == null) {
                    TreeBean.childList=new ArrayList<>();
                }
                TreeBean.childList.add(it);
            }
        }
        return TreeBean;
    }

    public  void getNetData(final String userName,final onSuccess onSuccess){
        OkHttpUtils.get().url("http://117.149.146.131/system/theme/anjuan/WX/GetForMobile.ashx")
                .addParams("name", userName)
                .build()
                .execute(new MyStringCallBack() {


            @Override
            public void onResponse(String response, int id) {
                List<TreeBean> list= JSON.parseArray(response,TreeBean.class);
                onSuccess.data(list);
            }
        });
    }


    public void connectVideoTalk(final Context context, final String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                erroMsg = "onTokenIncorrect:"+token;
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










    public interface  onSuccess{
        void data(List<TreeBean> list);
    }

    public  List<TreeBean> test(){

        List<TreeBean> treeBeans=new ArrayList<>();
        treeBeans.add(new TreeBean(1,1,0,"name"));
        treeBeans.add(new TreeBean(2,5,1,"name1-5"));
        treeBeans.add(new TreeBean(2,6,1,"name1-6"));
        treeBeans.add(new TreeBean(3,7,5,"name1-7"));
        treeBeans.add(new TreeBean(3,8,5,"name1-8"));

        treeBeans.add(new TreeBean(1,2,0,"name"));
        treeBeans.add(new TreeBean(1,3,0,"name"));
        treeBeans.add(new TreeBean(1,4,0,"name"));

        List<TreeBean> tree=bulid(treeBeans);
        Log.e("node_tag", new Gson().toJson(tree));
        return tree;

    }















}
