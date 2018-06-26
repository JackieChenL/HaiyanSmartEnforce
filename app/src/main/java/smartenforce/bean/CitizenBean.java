package smartenforce.bean;


import smartenforce.base.BaseBean;

public class CitizenBean extends BaseBean {


    /**
     * CitizenID : 1088
     * IdentityCardCit : 37040619770110671X
     * DriverLicenseCit : 
     * NameCit : 张贯才
     * SexCit : 男
     * CareerCit : 无
     * WorkplaceCit : 无
     * NationCit : 汉族
     * MobileCit : 18767337908
     * AddressCit : 山东省枣庄市山亭区冯卯镇下粉村81号
     * PostcodeCit : 277214
     * CreateTimeCit : 2017-03-07 08:42:40
     * StatusCit : 1
     * IsSysCit : 0
     * CertificateTypeID : 4
     * Code : 37040619770110671X
     */

    public int CitizenID=-1;
    public String IdentityCardCit="";
    public String DriverLicenseCit="";
    public String NameCit="";
    public String SexCit="";
    public String CareerCit="";
    public String WorkplaceCit="";
    public String NationCit="";
    public String MobileCit="";
    public String AddressCit="";
    public String PostcodeCit="";
    public String CreateTimeCit;
    public int StatusCit;
    public int IsSysCit;
    public String CertificateTypeID="";
    public String PhotoCit="";
    public String Code="";

    @Override
    public String toString() {
        return "CitizenBean{" +
                "CitizenID=" + CitizenID +
                ", IdentityCardCit='" + IdentityCardCit + '\'' +
                ", DriverLicenseCit='" + DriverLicenseCit + '\'' +
                ", NameCit='" + NameCit + '\'' +
                ", SexCit='" + SexCit + '\'' +
                ", CareerCit='" + CareerCit + '\'' +
                ", WorkplaceCit='" + WorkplaceCit + '\'' +
                ", NationCit='" + NationCit + '\'' +
                ", MobileCit='" + MobileCit + '\'' +
                ", AddressCit='" + AddressCit + '\'' +
                ", PostcodeCit='" + PostcodeCit + '\'' +
                ", CreateTimeCit='" + CreateTimeCit + '\'' +
                ", StatusCit=" + StatusCit +
                ", IsSysCit=" + IsSysCit +
                ", CertificateTypeID='" + CertificateTypeID + '\'' +
                ", PhotoCit='" + PhotoCit + '\'' +
                ", Code='" + Code + '\'' +
                '}';
    }




}
