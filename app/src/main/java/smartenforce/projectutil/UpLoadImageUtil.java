package smartenforce.projectutil;


import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.impl.BeanCallBack;


public class UpLoadImageUtil {
    //图片统一调用此接口上传
    public static void uploadImage(Context context, String UserID, String UpType, String Img, final onUploadImgCallBack callback) {
        OkHttpUtils.post().url(HttpApi.URL_UPLOADIMG).addParams("UserID", UserID)
                .addParams("UpType", UpType).addParams("Img", Img)
                .build().execute(new BeanCallBack(context, "图片上传中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (callback == null)
                    return;
                if (bean.State) {
                    try {
                        String json = bean.getResultBeanList(String.class).get(0);
                        JSONObject jo = new JSONObject(json);
                        String picArray = jo.getString("picArray");
                        callback.onSuccess(picArray);
                    } catch (JSONException e) {
                        callback.onFail("数据异常");
                    }

                } else {
                    callback.onFail(bean.ErrorMsg);
                }
            }
        });

    }

    public interface onUploadImgCallBack {

        void onSuccess(String picArray);

        void onFail(String msg);
    }


}
