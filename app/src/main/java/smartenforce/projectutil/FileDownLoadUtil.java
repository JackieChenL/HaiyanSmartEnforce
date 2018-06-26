package smartenforce.projectutil;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.DownloadFileCallBack;


public class FileDownLoadUtil {
    public static final String File_dir = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/kas/file";

    private static void downLoadFile(final Context context, final String url, final String fileName, final onDownLoadCallback callback) {
        OkHttpUtils.get().url(url).build().execute(new DownloadFileCallBack(context, File_dir, fileName + ".pdf") {

            @Override
            public void onFileDownLoadCallBack(File file, String msg, boolean isSuccess) {
                if (isSuccess) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException exception) {
                        isSuccess = false;
                        callback.onErroMsg("请先安装wps软件再使用打印文书功能");
                    }
                    if (isSuccess) {
                        callback.onSuccess();
                    }
                } else {
                    callback.onErroMsg(msg);
                }
            }
        });
    }

    /**
     * @param context
     * @param id
     * @param EvidenceType {中介报告 = 1, 限期改正 = 2, 扣押申请 = 3, 解除扣押申请 = 4, 土地测绘 = 5, 先行登记保存 = 6, 出库申请 = 7, 调查取证 = 8, 现场勘查 = 9, 其他证据 = 10, 现场照片 = 11, 平面图 = 12}
     * @param callback
     */
    public static void doGetPdfUrl(final Context context, final int id, final int EvidenceType, final String UserID, final onDownLoadCallback callback) {
        if (callback == null) {
            return;
        }

        final String[] arry = new String[]{"", "中介报告", "限期改正", "扣押申请", "解除扣押申请", "土地测绘", "先行登记保存", "出库申请", "调查取证", "现场勘查", "其他证据", "现场照片", "平面图"};
        OkHttpUtils.post().url(HttpApi.URL_PDFURL).addParams("id", id + "")
                .addParams("EvidenceType", EvidenceType + "")
                .addParams("UserID", UserID).build().execute(new BeanCallBack(context, "获取文档地址中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    try {
                        String json = bean.getResultBeanList(String.class).get(0);
                        JSONObject jo = new JSONObject(json);
                        String pdfurl = jo.getString("PDFUrl");
                        downLoadFile(context, HttpApi.URL_HOST + pdfurl, arry[EvidenceType] + id, callback);
                    } catch (JSONException e) {
                        callback.onErroMsg("返回数据格式异常");
                    }
                } else {
                    callback.onErroMsg(bean.ErrorMsg);
                }

            }
        });

    }


    public interface onDownLoadCallback {
        void onErroMsg(String msg);

        void onSuccess();
    }


}
