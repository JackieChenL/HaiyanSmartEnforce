package smartenforce.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

import smartenforce.bean.GroupBean;



public class Groupadapter extends BaseExpandableListAdapter {
    private Context context;
    private List<GroupBean> list;
    private LayoutInflater inflater;
    private MyApplication app;
    public Groupadapter(Context context, List<GroupBean> list) {
        this.context = context;
        app= (MyApplication) ((Activity)context).getApplication();
        for (GroupBean groupBean :list){
            if (String.valueOf(groupBean.departMentBean.DepartmentID)==app.DepartmentID){
                for (GroupBean.UserBean userBean :groupBean.userList){
                    if (String.valueOf(userBean.UserID).equals(app.userID)){
                        groupBean.userList.remove(userBean) ;
                        break;
                    }
                }
                break;

            }

        }
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).userList.size();
    }

    @Override
    public GroupBean.DepartMentBean getGroup(int groupPosition) {
        return list.get(groupPosition).departMentBean;
    }

    @Override
    public GroupBean.UserBean getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).userList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return list.get(groupPosition).departMentBean.DepartmentID;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return list.get(groupPosition).userList.get(childPosition).UserID;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_group_parent, parent, false);
            holder = new GroupHolder();
            holder.tev_departName = (TextView) convertView.findViewById(R.id.tev_departName);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.tev_departName.setText(list.get(groupPosition).departMentBean.NameDep);
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_group_child, parent, false);
            holder = new ChildHolder();
            holder.tev_userName = (TextView) convertView.findViewById(R.id.tev_userName);
            holder.cbx_user = (ImageView) convertView.findViewById(R.id.cbx_user);
            convertView.setTag(holder);

        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.tev_userName.setText(list.get(groupPosition).userList.get(childPosition).NameEmp);
        boolean isSelect = list.get(groupPosition).userList.get(childPosition).isSelect;
        holder.cbx_user.setBackgroundResource(isSelect ? R.drawable.checked_bg : R.drawable.unchecked_bg);
        return convertView;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    static class ChildHolder {
        TextView tev_userName;
        ImageView cbx_user;
    }

    static class GroupHolder {
        TextView tev_departName;
    }
}
