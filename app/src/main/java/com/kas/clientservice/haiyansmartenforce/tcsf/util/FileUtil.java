/***********************************************************
 * author   colin
 * company  fosung
 * email    wanglin2046@126.com
 * date     16-7-15 下午4:41
 **********************************************************/

package com.kas.clientservice.haiyansmartenforce.tcsf.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 文件操作工具类
 */
public class FileUtil {


    /**
     * 解压一个压缩文档 到指定位置
     * @param zipFilePath 压缩包的路径
     * @param outPath     解压指定的路径
     */
    public static void unZipFolder(String zipFilePath, String outPath) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry;
        String szName = "";

        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPath + File.separator + szName);
                folder.mkdirs();
            } else {

                File file = new File(outPath + File.separator + szName);
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = inZip.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }

        inZip.close();
    }

    /**
     * 关闭输入/输出流，捕获异常不抛出
     */
    public static void colseStreamWithCatchException(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public static String saveBitmap(Bitmap mBitmap) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/kas/img/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        try {
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path=file.getAbsolutePath();
        LogUtil.i("image_file_url", path);
        return path;
    }
}
