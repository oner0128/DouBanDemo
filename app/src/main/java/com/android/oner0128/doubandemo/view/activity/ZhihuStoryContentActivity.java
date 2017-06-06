package com.android.oner0128.doubandemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.ZhihuStoryContentBean;
import com.android.oner0128.doubandemo.util.HtmlUtil;
import com.android.oner0128.doubandemo.util.IntenetUtils;
import com.android.oner0128.doubandemo.util.SharePreferencesHelper;
import com.android.oner0128.doubandemo.util.ToastUtils;
import com.android.oner0128.doubandemo.view.BaseView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ZhihuStoryContentActivity extends AppCompatActivity implements BaseView {
    private static final String TAG = "ZhihuStoryContentActivity";

    @BindView(R.id.detail_bar_image)
    ImageView detailBarImg;
    @BindView(R.id.detail_bar_title)
    TextView detailBarTitle;
    @BindView(R.id.detail_bar_copyright)
    TextView detailBarCopyright;
    @BindView(R.id.wv_detail_content)
    WebView detailContentWV;
    private int storyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_story_content);
        ButterKnife.bind(this);
        initData();
    }

    public void initData() {
        Intent intent = getIntent();
        storyId = intent.getIntExtra("storyId", 0);
        if (storyId != 0) {
            getStoryContent(storyId);

        } else {
            ToastUtils.showToast(this, TAG + "数据加载出错");
        }
    }

    public void initView() {
        initWebViewClient();
    }

    String SP_NO_IMAGE = "no_image";
    String SP_AUTO_CACHE = "auto_cache";

    private void initWebViewClient() {
        WebSettings settings = detailContentWV.getSettings();
        if (SharePreferencesHelper.getInstance(this).getBoolean(SP_NO_IMAGE, false)) {
            settings.setBlockNetworkImage(true);
        }
        if (SharePreferencesHelper.getInstance(this).getBoolean(SP_AUTO_CACHE, true)) {
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (IntenetUtils.isInternetAvailable(this)) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        detailContentWV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void getStoryContent(int storyId) {
        APIService.getINSTANCE().getZhihuService()
                .getStoryContent(storyId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuStoryContentBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ZhihuStoryContentBean zhihuStoryContentBean) {
                        showContent(zhihuStoryContentBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.v("detail onError", e.toString());
                        showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void showContent(ZhihuStoryContentBean storyContentBean) {
        String imgUrl = storyContentBean.getImage();
        Glide.with(this)
                .load(imgUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.mipmap.ic_launcher)
                .into(detailBarImg);
        detailBarTitle.setText(storyContentBean.getTitle());
        detailBarCopyright.setText(storyContentBean.getImage_source());
        String htmlData = HtmlUtil.createHtmlData(storyContentBean.getBody(), storyContentBean.getCss(), storyContentBean.getJs());
        detailContentWV.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }
}
