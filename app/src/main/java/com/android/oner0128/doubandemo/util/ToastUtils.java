package com.android.oner0128.doubandemo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by rrr on 2017/6/6.
 */

public class ToastUtils {
    private static Toast mToast;
    /**
     * 非阻塞式显示Toast，防止出现连续点击Toast时的显示问题
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }
}
