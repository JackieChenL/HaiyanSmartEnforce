package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;


import com.kas.clientservice.haiyansmartenforce.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import smartenforce.adapter.Groupadapter;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.GroupBean;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.projectutil.PersonUtil;

public class SelectEmpNameActivity extends ShowTitleActivity {
    private ExpandableListView expandlist;
    private Groupadapter groupadapter;
    private List<GroupBean> groupBeanList;
    private PersonUtil personUtil;
    private Map<String, GroupBean.UserBean> map = new HashMap<String, GroupBean.UserBean>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_empname);
    }

    @Override
    protected void findViews() {
        expandlist = (ExpandableListView) findViewById(R.id.expandlist);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("协办人");
        tev_title_right.setText("确定");
        personUtil = new PersonUtil(aty, new PersonUtil.onResult() {

            @Override
            public void onErro(String msg) {
                warningShow(msg);
            }

            @Override
            public void onDataResult(List<GroupBean> list) {
                groupBeanList = list;
                groupadapter = new Groupadapter(aty, groupBeanList);
                expandlist.setAdapter(groupadapter);
                initExpandGroup();
            }
        });
        personUtil.start();

        expandlist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                GroupBean.UserBean userBean = groupadapter.getChild(groupPosition, childPosition);
                boolean isSelect = userBean.isSelect;
                userBean.isSelect = !isSelect;
                if (isSelect) {
                    map.remove(groupPosition + "+" + childPosition);
                } else {
                    map.put(groupPosition + "+" + childPosition, userBean);
                }
                groupadapter.notifyDataSetChanged();
                return false;
            }
        });

        tev_title_right.setOnClickListener(new NoFastClickLisener() {
            @Override
            public void onNofastClickListener(View v) {
                doCheck();
            }
        });
    }

    private void initExpandGroup() {
        int count = groupBeanList.size();
        for (int i = 0; i < count; i++) {
            GroupBean groupBean = groupBeanList.get(i);
            if (String.valueOf(groupBean.departMentBean.DepartmentID) == app.DepartmentID) {
                expandlist.expandGroup(i);
                break;

            }
        }

    }


    private void doCheck() {
        if (map.size() == 1) {
            Iterator iter = map.entrySet().iterator();
            Intent intent = new Intent();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                GroupBean.UserBean userBean = (GroupBean.UserBean) entry.getValue();
                intent.putExtra("UserBean", userBean);
            }
            setResult(RESULT_OK, intent);
            finish();
        } else {
            warningShow("还未选择协办人或协办人数量大于1");
        }


    }
}
