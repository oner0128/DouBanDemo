package com.android.oner0128.doubandemo.view.item;

import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rrr on 2017/6/6.
 */

public class ZhihuBannerItem implements ZhihuItem{
    private List<ZhihuLatestNewsBean.TopStoriesBean> topStories;
    private List<String> images;
    private List<String> titles;


    public ZhihuBannerItem(List<ZhihuLatestNewsBean.TopStoriesBean> topStories) {
        this.topStories = topStories;
        initData();
    }

    private void initData() {
        images = new ArrayList<>();
        titles = new ArrayList<>();

        for (ZhihuLatestNewsBean.TopStoriesBean topStory : topStories) {
            images.add(topStory.getImage());
            titles.add(topStory.getTitle());
        }
    }

    public List<ZhihuLatestNewsBean.TopStoriesBean> getTopStories() {
        return topStories;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getTitles() {
        return titles;
    }

}
