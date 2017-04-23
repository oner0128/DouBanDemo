package com.android.oner0128.doubandemo.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by rrr on 2017/4/22.
 */

public class ActivtyUtils {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,int frameId){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(frameId,fragment).commit();
    }
    public static void switchFragment(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,int frameId){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(frameId,fragment).commit();
    }
}
