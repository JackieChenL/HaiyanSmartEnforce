package videotalk.normal;



public class UserLiveStatusBean {
    public int code;
    public String status;

    public String getStatus(){

        return status.equals("1")?"[在线]":"[离线]";
    }

    @Override
    public String toString() {
        return "UserLiveStatusBean{" +
                "code=" + code +
                ", status='" + status + '\'' +
                '}';
    }
}
