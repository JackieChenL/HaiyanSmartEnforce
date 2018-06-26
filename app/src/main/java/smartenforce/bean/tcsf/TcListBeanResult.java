package smartenforce.bean.tcsf;


import smartenforce.base.BaseBean;

public class TcListBeanResult extends BaseBean {

    public String carnum;
    public String starttime;
    public String stoptime;
    public String Berthname;
    public int BerthID;



    public int State;
    public int btid;
    public String money;



    public String getState() {
        String retStr;
        switch (State){
            case 0:
                retStr="离开" ;
                break ;
            case 1:
                retStr="停入" ;
                break ;
            default:
                retStr="异常" ;
        }


        return retStr;
    }


    @Override
    public String toString() {
        return "TcListBeanResult{" +
                "carnum='" + carnum + '\'' +
                ", starttime='" + starttime + '\'' +
                ", stoptime='" + stoptime + '\'' +
                ", Berthname='" + Berthname + '\'' +
                ", BerthID=" + BerthID +
                ", State=" + State +
                ", btid=" + btid +
                ", money=" + money +
                '}';
    }
}
