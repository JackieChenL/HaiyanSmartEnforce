package smartenforce.bean.tcsf;


import java.util.List;

import smartenforce.base.BaseBean;


public class PatrolDetailBean extends BaseBean {


    /**
     * Btid : 175
     * UCarnum : 浙F11111
     * BerthName : 勤俭路-1号停车位
     * StartTime : 2018/6/11 16:08:50
     * Imglist : [{"img":"http://hywx.hnzhzf.top/MVersion/20180611040850@image.jpg"}]
     * TollCollector : 无
     */

    public String Btid;
    public String UCarnum;
    public String BerthName;
    public String StartTime;
    public String TollCollector;
    public List<ImglistBean> Imglist;

    public static class ImglistBean extends BaseBean {
        /**
         * img : http://hywx.hnzhzf.top/MVersion/20180611040850@image.jpg
         */

        public String img;
    }
}
