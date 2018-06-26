package smartenforce.bean;


import java.util.List;

import smartenforce.base.BaseBean;


public class PointUpLoadBean extends BaseBean {


    public int TerminalID;
    public String UserID;
    public List<TrajectorArryBean> TrajectorArry;

    public static class TrajectorArryBean {
        public String LngTra;
        public String LatTra;
        public String TimeTra;

        public TrajectorArryBean(String lngTra, String latTra, String timeTra) {
            LngTra = lngTra;
            LatTra = latTra;
            TimeTra = timeTra;
        }
    }
}
