package com.android.oner0128.doubandemo.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by rrr on 2017/4/26.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    public CustomLinearLayoutManager(Context context) {
        super(context);
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
