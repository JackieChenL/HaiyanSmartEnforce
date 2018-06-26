package smartenforce.projectutil;


import android.content.Context;

import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.GroupBean;
import smartenforce.impl.BeanCallBack;


public class PersonUtil {
    private List<GroupBean> groupBeanList;
    private Context context;
    private onResult onResultListener;
    private int size;
    private List<GroupBean.DepartMentBean> departList;
    private int CURRENT;

    public PersonUtil(Context context, onResult onResultListener) {
        this.groupBeanList = new ArrayList<GroupBean>();
        this.context = context;
        this.onResultListener = onResultListener;
    }


    private void getUserList(final GroupBean.DepartMentBean departMentBean, final int num) {
         CURRENT = num;
        OkHttpUtils.post().url(HttpApi.URL_EMPLOYEE_LIST)
                .addParams("DepartmentID", departMentBean.DepartmentID + "").build().execute(new BeanCallBack(context,null) {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    List<GroupBean.UserBean> userList = bean.getResultBeanList(GroupBean.UserBean.class);
                    groupBeanList.add(new GroupBean(departMentBean, userList));
                    if (num == size - 1) {
                        onResultListener.onDataResult(groupBeanList);
                    } else {
                        CURRENT++;
                        getUserList(departList.get(CURRENT), CURRENT);

                    }
                } else {
                    onResultListener.onErro(bean.ErrorMsg);
                }
            }
        });

    }

    private void getDepartList() {
        OkHttpUtils.get().url(HttpApi.URL_DEPARTMENT_LIST).build().execute(new BeanCallBack(context, "获取部门信息中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total >0) {
                    size = bean.total;
                    departList = bean.getResultBeanList(GroupBean.DepartMentBean.class);
                    getUserList(departList.get(0), 0);
                } else {
                    onResultListener.onErro(bean.ErrorMsg);
                }
            }
        });

    }


    public interface onResult {
        void onErro(String msg);

        void onDataResult(List<GroupBean> groupBeanList);
    }

    public void start() {
        departList=new ArrayList<GroupBean.DepartMentBean>();
        MyApplication application= (MyApplication) context.getApplicationContext();
        int DepartmentID= Integer.valueOf(application.DepartmentID);
        String NameDep=application.NameDep;
        departList.add(new GroupBean.DepartMentBean(DepartmentID,NameDep));
        if (DepartmentID!=8){
            departList.add(new GroupBean.DepartMentBean(8,"直属中队"));
        }
        size = departList.size();
        getUserList(departList.get(0), 0);
    }
}
