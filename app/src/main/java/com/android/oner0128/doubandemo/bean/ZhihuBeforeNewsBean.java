package com.android.oner0128.doubandemo.bean;

import com.android.oner0128.doubandemo.view.item.ZhihuItem;

import java.util.List;

/**
 * Created by rrr on 2017/6/6.
 */

public class ZhihuBeforeNewsBean implements ZhihuItem{

    /**
     * date : 20170504
     * stories : [{"images":["https://pic1.zhimg.com/v2-251caac5f32c931380856c98bd28b050.jpg"],"type":0,"id":9400561,"ga_prefix":"050422","title":"小事 · 人言可畏"},{"title":"N 个版本《美女与野兽》，这部我最服气","ga_prefix":"050421","images":["https://pic3.zhimg.com/v2-0114a3c63dcb8f0ef7ccdb36c254077a.jpg"],"multipic":true,"type":0,"id":9398931},{"images":["https://pic2.zhimg.com/v2-ddc6342b758dcd34d6470e7d533e4f79.jpg"],"type":0,"id":9399598,"ga_prefix":"050420","title":"玩扫雷就在瞎点的，不止你一个人"},{"images":["https://pic2.zhimg.com/v2-74ff54a7d75168c4612f0167b8b70bbd.jpg"],"type":0,"id":9393237,"ga_prefix":"050419","title":"厨房怎么装水槽，这里有点小攻略"},{"images":["https://pic3.zhimg.com/v2-0e0b79d75614750f4a9773e53b6b54f6.jpg"],"type":0,"id":9400452,"ga_prefix":"050418","title":"在床上躺一天（什么也不干），还有钱拿，你愿意吗？"},{"images":["https://pic3.zhimg.com/v2-9448958b9432da3d9c57025b4e232bbe.jpg"],"type":0,"id":9392915,"ga_prefix":"050417","title":"写玄幻爽文最有成就的事，就是一百年后翻身成了「正史」"},{"images":["https://pic3.zhimg.com/v2-7643b9864c75b1b83288342d95ee0a8e.jpg"],"type":0,"id":9397441,"ga_prefix":"050416","title":"一个未上市公司的老板说去年赚了 4 个亿，他在说谎吗？"},{"images":["https://pic4.zhimg.com/v2-1d5b5fd01ecbffccd5dd1a7bae2be183.jpg"],"type":0,"id":9399136,"ga_prefix":"050415","title":"来了这些地方，刘慈欣笔下的场景仿佛变成了真的"},{"images":["https://pic4.zhimg.com/v2-915194888cb7a941fca0b944318d30fb.jpg"],"type":0,"id":9400119,"ga_prefix":"050415","title":"别让「多任务工作」谋杀你的效率"},{"images":["https://pic4.zhimg.com/v2-2d67604d3941dc1ea806e3e0f1051a33.jpg"],"type":0,"id":9397454,"ga_prefix":"050413","title":"未来的医疗行业是下一个「房地产」，所以王健林来了"},{"images":["https://pic3.zhimg.com/v2-97775c7df748fcd7fbe97f8a5948ea9e.jpg"],"type":0,"id":9398495,"ga_prefix":"050412","title":"这些词听起来像但根本就是两码事，可别被唬住了"},{"images":["https://pic4.zhimg.com/v2-674b33709d150ba3aa3ba8f401545d3f.jpg"],"type":0,"id":9398870,"ga_prefix":"050412","title":"大误 · 你看我头像萌么"},{"images":["https://pic4.zhimg.com/v2-011555a081e853f1bc6cbb82dedface3.jpg"],"type":0,"id":9397356,"ga_prefix":"050410","title":"小明去了大公司，我去了小公司，怎么\u2026\u2026我们收入差好多"},{"images":["https://pic4.zhimg.com/v2-28a1f0995db906b38f2b8fda0f066bc3.jpg"],"type":0,"id":9397552,"ga_prefix":"050409","title":"「一滴血验癌症」不靠谱，但肿瘤标志物可不能不当回事"},{"images":["https://pic2.zhimg.com/v2-630ae9633f831522bfb1bf212baf43f1.jpg"],"type":0,"id":9397241,"ga_prefix":"050408","title":"学校的卫生让学生无偿负责，这「合法」吗？"},{"images":["https://pic1.zhimg.com/v2-1b620e561af3feb96b95390823f40594.jpg"],"type":0,"id":9397808,"ga_prefix":"050407","title":"转折发生在 1902 年，爱因斯坦通过找关系，做了公务员"},{"images":["https://pic1.zhimg.com/v2-f058eeb1dfd94f518105f2c802e4905c.jpg"],"type":0,"id":9397947,"ga_prefix":"050407","title":"那些看不懂的商店和品牌忽然火了，消费者到底想要什么？"},{"images":["https://pic3.zhimg.com/v2-871fe9bd77ddcc0bf7b534a660bfe432.jpg"],"type":0,"id":9397829,"ga_prefix":"050407","title":"背负巨债、网吧终日、没有身份证、断绝家人往来：「三和大神」们的游戏与人生"},{"images":["https://pic2.zhimg.com/v2-dd2099aff566b37503e6e0aff3e7afad.jpg"],"type":0,"id":9395948,"ga_prefix":"050406","title":"瞎扯 · 如何正确地吐槽"}]
     */

    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean implements ZhihuItem{
        /**
         * images : ["https://pic1.zhimg.com/v2-251caac5f32c931380856c98bd28b050.jpg"]
         * type : 0
         * id : 9400561
         * ga_prefix : 050422
         * title : 小事 · 人言可畏
         * multipic : true
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private boolean multipic;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
