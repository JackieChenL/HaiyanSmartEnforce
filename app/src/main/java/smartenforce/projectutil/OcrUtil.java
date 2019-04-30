package smartenforce.projectutil;


import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Request;
import smartenforce.impl.MyStringCallBack;
import smartenforce.widget.ProgressDialogUtil;


public class OcrUtil {
    private static final String grant_type="client_credentials";
    private static final String API_Key="jMefFwwMoDDRytAVbvEDTN8k";
    private static final String Secret_Key="uMsocF96fScVWQQtD5OOm6DrHHyWLnxr";
    private static final String Token_Url="https://aip.baidubce.com/oauth/2.0/token";
    private static final String IdCard_Url="https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
    private static final String CarNumber_Url="https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";

    private  String accessToken=null;

    private static OcrUtil util=null;


    public interface onIdCardCallBack {
        void onSuccess(String name, String idCardNum, String sex, String nation, String address);

        void onErroMsg(String msg);
    }
    public interface onCarNumberCallBack {
        void onSuccess(String carNumber);

        void onErroMsg(String msg);
    }

    public static OcrUtil getInstance(){
        if (util==null){
            synchronized (OcrUtil.class){
                if (util==null){
                     util=new OcrUtil();
                    }
                }

            }
        return util;
    }

    private OcrUtil(){}


   //获取token,有效期一般默认为一个月，暂不缓存，在应用初始化的 时候调用一次
    public void getAuthToken(){
        OkHttpUtils.post().url(Token_Url)
                .addParams("grant_type", grant_type)
                .addParams("client_id", API_Key)
                .addParams("client_secret", Secret_Key)
                .build().execute(new MyStringCallBack(){

            @Override
            public void onError(Call call, Exception e, int id) {
                  super.onError(call, e, id);
                   accessToken=null;
            }

            @Override
            public void onResponse(String response, int id) {
                  super.onResponse(response, id);
                    try {
                        JSONObject jo = new JSONObject(response);
                        accessToken=jo.getString("access_token");
                    } catch (Exception e) {
                        accessToken=null;
                    }
            }
        });

    }

    public  void getIdCardInfo(final Activity aty, String base64IdCard, final onIdCardCallBack callBack) {
        if (isTokenUninitialized()){
            callBack.onErroMsg("识别sdk初始化token获取失败");
            return;
        }
            OkHttpUtils.post().url(IdCard_Url)
                    .addParams("access_token", accessToken)
                    .addParams("id_card_side", "front")
                    .addParams("image", base64IdCard)
                    .build().execute(new MyStringCallBack(){
                @Override
                public void onBefore(Request request, int id) {
                    super.onBefore(request, id);
                    ProgressDialogUtil.show(aty,"解析中...");
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call, e, id);
                    callBack.onErroMsg("身份证解析失败");
                    ProgressDialogUtil.hide();
                }

                @Override
                public void onResponse(String response, int id) {
                    super.onResponse(response, id);
                    CardBean cardBean= JSON.parseObject(response,CardBean.class);
                    if (cardBean.error_code!=0){
                        callBack.onErroMsg(cardBean.error_msg);
                    }else{
                        try{
                            String name=cardBean.words_result.getName();
                            String idCardNum=cardBean.words_result.getNumber();
                            String sex=cardBean.words_result.getSex();
                            String nation=cardBean.words_result.getNation();
                            String address=cardBean.words_result.getAddress();
                            callBack.onSuccess(name,idCardNum,sex,nation,address);
                        }catch (Exception e){
                            callBack.onErroMsg("身份证解析失败");
                        }
                    }
                    ProgressDialogUtil.hide();
                }
            });


    }

    private boolean isTokenUninitialized(){
       return TextUtils.isEmpty(accessToken);
    }

    public  void getCarNumberInfo(final Activity aty, String bmp, final onCarNumberCallBack callBack) {
        if (isTokenUninitialized()){
            callBack.onErroMsg("识别sdk初始化token获取失败");
            return;
        }
            OkHttpUtils.post().url(CarNumber_Url)
                    .addParams("access_token", accessToken)
                    .addParams("multi_detect", "false")
                    .addParams("image", bmp)
                    .build().execute(new MyStringCallBack(){
                @Override
                public void onBefore(Request request, int id) {
                    super.onBefore(request, id);
                    ProgressDialogUtil.show(aty,"解析中...");
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call, e, id);
                    ProgressDialogUtil.hide();
                    callBack.onErroMsg("车牌解析异常");

                }

                @Override
                public void onResponse(String response, int id) {
                    super.onResponse(response, id);
                    ProgressDialogUtil.hide();
                    CarNumberBean carNumberBean= JSON.parseObject(response,CarNumberBean.class);
                    if (carNumberBean.error_code!=0){
                        callBack.onErroMsg(carNumberBean.error_msg);
                    }else{
                        try{
                            callBack.onSuccess(carNumberBean.words_result.number);
                        }catch (Exception e){
                            callBack.onErroMsg("车牌解析失败");
                        }
                    }

                }
            });


    }
}