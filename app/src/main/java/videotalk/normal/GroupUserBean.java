package videotalk.normal;


public class GroupUserBean {
    //记录是否选中视屏通话
    public boolean isSelect=false;
    public String id;
    public String name;
    public String token;

    public GroupUserBean() {
    }

    public GroupUserBean(int id) {

        this.name="NAME"+id;
        isSelect=false;
    }
}
