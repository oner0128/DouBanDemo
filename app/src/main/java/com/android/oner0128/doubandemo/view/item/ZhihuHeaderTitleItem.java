package com.android.oner0128.doubandemo.view.item;

import com.android.oner0128.doubandemo.util.DateUtil;

import java.util.Date;

/**
 * Created by rrr on 2017/6/6.
 */

public class ZhihuHeaderTitleItem implements ZhihuItem {
    private String strDate;

    private String formatDate;

    public ZhihuHeaderTitleItem(String strDate) {
        this.strDate = strDate;
    }

    public String getFormatDate() {
        if (strDate == null) {
            return null;
        }

        Date date = new Date();
        date = DateUtil.str2date(strDate, "yyyyMMdd");
        formatDate = DateUtil.date2str(date);
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }
}
