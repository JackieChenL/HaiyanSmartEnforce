package videotalk.tree;


import java.util.ArrayList;

public class TreeBean {
    public String Token;
    public int level;
    public int  id;
    public int  pid;
    public String name;
    public boolean isChecked=false;
    public ArrayList<TreeBean> childList;

    public TreeBean() {
    }

    public TreeBean(int level, int id, int pid, String name) {
        this.level = level;
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TreeBean{" +
                "Token='" + Token + '\'' +
                ", level=" + level +
                ", id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", childList=" + childList +
                '}';
    }
}
