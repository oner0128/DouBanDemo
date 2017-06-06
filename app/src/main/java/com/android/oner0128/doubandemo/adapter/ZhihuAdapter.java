package com.android.oner0128.doubandemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.bean.ZhihuLatestNewsBean;
import com.android.oner0128.doubandemo.view.item.ZhihuBannerItemDelegate;
import com.android.oner0128.doubandemo.view.item.ZhihuHeaderTitleItemDelegate;
import com.android.oner0128.doubandemo.view.item.ZhihuItem;
import com.android.oner0128.doubandemo.view.item.ZhihuStoriesItemDelegate;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrr on 2017/6/5.
 */

public class ZhihuAdapter extends MultiItemTypeAdapter<ZhihuItem> {
    public ZhihuAdapter(Context context, List<ZhihuItem> datas) {
        super(context, datas);
        addItemViewDelegate(new ZhihuBannerItemDelegate());
        addItemViewDelegate(new ZhihuHeaderTitleItemDelegate());
        addItemViewDelegate(new ZhihuStoriesItemDelegate());
    }
//    private ArrayList<ZhihuLatestNewsBean.StoriesBean> mData;//用于储存数据
//    private Context mContext;
//    ZhihuViewHolder holder=null;
//
//
//
//
//    public  ZhihuAdapter(ArrayList<ZhihuLatestNewsBean.StoriesBean> data, Context context) {
//        this.mData = data;
//        this.mContext=context;
//    }
//
//    @Override
//    public ZhihuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //这里进行过改动
//        if (headView!=null && viewType==TYPE_HEADER) return new ZhihuViewHolder(headView);
//        holder=new ZhihuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_zhihu,parent,false));
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ZhihuViewHolder holder, int position) {
//        //此方法内可以对布局中的控件进行操作
//        //这里进行过改动
//        if (getItemViewType(position)==TYPE_HEADER) return;
//        final int pos=getRealPosition(holder);
//
//        holder.title.setText(mData.get(pos).getTitle());//
//
//        Glide.with(mContext).load(mData.get(pos).getImages().get(0)).into(holder.img);
//    }
//
//
//    @Override
//    public int getItemCount() {
//
//        //获取数据长度
//        //这里进行过改动
//        return headView==null? mData.size():mData.size()+1;
//    }
//
//    class ZhihuViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_title)
//        TextView title;//标题
//        @BindView(R.id.imageView)
//        ImageView img;//显示的图片
//
//
//        public ZhihuViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
//
//
//    //下面的是新添加的
//
//
//    public static final int TYPE_HEADER = 0;//显示headvuiew
//    public static final int TYPE_NORMAL = 1;//显示普通的item
//    private View headView;//这家伙就是Banner
//
//    public void setHeadView(View headView){
//        this.headView=headView;
//        notifyItemInserted(0);
//    }
//
//    public View getHeadView(){
//        return headView;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (headView==null)
//            return TYPE_NORMAL;
//        if (position==0)
//            return TYPE_HEADER;
//        return TYPE_NORMAL;
//    }
//
//    private int getRealPosition(RecyclerView.ViewHolder holder) {
//        int position=holder.getLayoutPosition();
//        return headView==null? position:position-1;
//    }
}
