package smartenforce.bean;



public class AppVersionBean {
    public String Url;
    public int versionCode;
    public String versionName;
    public String updateMessage;
    public int versionStatus;//判断该版本是否需要强制更新（大于0必须强制更新）

}
