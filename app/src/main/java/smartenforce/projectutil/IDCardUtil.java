package smartenforce.projectutil;


import android.app.Activity;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.impl.BeanCallBack;


public class IDCardUtil {


    public interface onIdCardCallBack {
        void onSuccess(String name, String idCardNum, String sex, String nation, String address);

        void onErroMsg(String msg);
    }


    public static void getIdCardInfo(Activity aty, String base64IdCard, final onIdCardCallBack callBack) {
        OkHttpUtils.post().url(HttpApi.URL_IDCARDINFO).addParams("Side", "face")
                .addParams("IdCard", base64IdCard).build().execute(new BeanCallBack(aty, "获取身份证信息中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (callBack == null)
                    return;
                if (bean.State) {
                    try {
                        JSONArray ja = new JSONArray(bean.Rtn);
                        JSONObject jo = new JSONObject(ja.getString(0));
                        JSONArray ja2 = jo.getJSONArray("outputs");
                        JSONObject jo2 = new JSONObject(ja2.getString(0));
                        JSONObject jo3 = jo2.getJSONObject("outputValue");
                        JSONObject CardInfo = new JSONObject(jo3.getString("dataValue"));
                        String name = CardInfo.getString("name");
                        String num = CardInfo.getString("num");
                        String sex = CardInfo.getString("sex");
                        String nation = CardInfo.getString("nationality");
                        String address = CardInfo.getString("address");
                        callBack.onSuccess(name, num, sex, nation, address);
                    } catch (Exception e) {
                        callBack.onErroMsg("身份证解析异常");
                    }
                } else {
                    callBack.onErroMsg("身份证解析异常");
                }
            }
        });
    }
}