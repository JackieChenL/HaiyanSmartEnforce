package smartenforce.bean;


import smartenforce.base.BaseBean;

/**
 * 大类实体
 */
public class FirstLevelBean extends BaseBean {
  public String NameFiL;
  public int FirstLevelID;

    @Override
    public String toString() {
        return "FirstLevelBean{" +
                "NameFiL='" + NameFiL + '\'' +
                ", FirstLevelID='" + FirstLevelID + '\'' +
                '}';
    }
}
