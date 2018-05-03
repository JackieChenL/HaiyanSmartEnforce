package com.kas.clientservice.haiyansmartenforce.Utils;

import java.util.ArrayList;

/**
 * 描述：
 * 时间：2018-05-02
 * 公司：COMS
 */

public class StringUtils {
    public static String[] split(String str, String splitsign) {
        int index;
        if (str == null || splitsign == null)
            return null;
        ArrayList al = new ArrayList();
        while ((index = str.indexOf(splitsign)) != -1) {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return (String[]) al.toArray(new String[0]);
    }
}
