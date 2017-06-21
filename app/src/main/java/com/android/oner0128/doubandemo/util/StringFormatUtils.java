package com.android.oner0128.doubandemo.util;

import com.android.oner0128.doubandemo.bean.PersonBean;

import java.util.List;

/**
 * Created by rrr on 2017/6/21.
 */

public class StringFormatUtils {
    //格式化类型、国家等 List<String>
    public static String formatListToString(List<String> list){
        if (list != null && list.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    stringBuilder.append(list.get(i)).append(" / ");
                } else {
                    stringBuilder.append(list.get(i));
                }
            }
            return stringBuilder.toString();

        } else {
            return "No idea";
        }
    }
    //格式化主演导演等
    public static String formatCastsToString(List<? extends PersonBean> casts){
        if (casts != null && casts.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < casts.size(); i++) {
                if (i < casts.size() - 1) {
                    stringBuilder.append(casts.get(i).getName()).append(" / ");
                } else {
                    stringBuilder.append(casts.get(i).getName());
                }
            }
            return stringBuilder.toString();

        } else {
            return "No idea";
        }
    }
}
