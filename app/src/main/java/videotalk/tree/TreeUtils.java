package videotalk.tree;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.impl.MyStringCallBack;

public class TreeUtils {

    public static List<TreeBean> bulid(List<TreeBean> TreeBeans) {

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
    public static List<TreeBean> buildByRecursive(List<TreeBean> TreeBeans) {
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
    public static TreeBean findChildren(TreeBean TreeBean,List<TreeBean> TreeBeans) {
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

    public static void getNetData(final onSuccess onSuccess){
        OkHttpUtils.get().url("http://117.149.146.131/system/theme/anjuan/WX/GetForMobile.ashx")
                .build().execute(new MyStringCallBack() {


            @Override
            public void onResponse(String response, int id) {
                List<TreeBean> list= JSON.parseArray(response,TreeBean.class);
                onSuccess.data(list);
            }
        });
    }


    public interface  onSuccess{
        void data(List<TreeBean> list);
    }

    public static List<TreeBean> test(){

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
