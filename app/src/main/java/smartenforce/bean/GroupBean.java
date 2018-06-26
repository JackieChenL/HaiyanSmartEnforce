package smartenforce.bean;


import java.util.List;

import smartenforce.base.BaseBean;


public class GroupBean extends BaseBean {
    public DepartMentBean departMentBean;
    public List<UserBean> userList;

    public GroupBean(DepartMentBean departMentBean, List<UserBean> userList) {
        this.departMentBean = departMentBean;
        this.userList = userList;
    }

    public static class DepartMentBean extends BaseBean {
        public int DepartmentID;
        public String NameDep;

        public DepartMentBean(int departmentID, String nameDep) {
            DepartmentID = departmentID;
            NameDep = nameDep;
        }

        public DepartMentBean() {
        }
    }

    public static class UserBean extends BaseBean {
        public int UserID;
        public String NameEmp;
        public boolean isSelect = false;
    }
}
