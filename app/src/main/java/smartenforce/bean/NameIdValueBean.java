package smartenforce.bean;


import smartenforce.base.BaseBean;

public class NameIdValueBean extends BaseBean {


    /**
     * name : 异地暂存-暂存仓库(金利路8号)
     * id : 1
     */

    public String name;
    public int id;

    public NameIdValueBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public NameIdValueBean() {
    }
}
