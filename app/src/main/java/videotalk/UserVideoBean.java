package videotalk;


public class UserVideoBean {

    public String userID;
    public String userToken;
    public boolean isSelect=false;

    public UserVideoBean(String userID, String userToken) {
        this.userID = userID;
        this.userToken = userToken;
    }
}
