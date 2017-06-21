package com.android.oner0128.doubandemo.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MovieDetailBean;
import com.android.oner0128.doubandemo.util.StringFormatUtils;
import com.android.oner0128.doubandemo.view.BaseView;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MovieDetailActivity extends AppCompatActivity implements BaseView {

    @BindView(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_scrolling)
    FloatingActionButton fab;

    @BindView(R.id.fragmentlayout)
    FrameLayout frameLayout;

    //header
    @BindView(R.id.image_background_scrolling)
    ImageView iv_background_scrolling;
    @BindView(R.id.iv_header_photo)
    ImageView iv_header_photo;
    @BindView(R.id.tv_header_rating_rate)
    TextView tv_header_rating_rate;
    @BindView(R.id.tv_header_rating_number)
    TextView tv_header_rating_number;
    @BindView(R.id.tv_header_directors)
    TextView tv_header_directors;
    @BindView(R.id.tv_header_genres)
    TextView tv_header_genres;
    @BindView(R.id.tv_header_casts)
    TextView tv_header_casts;
    @BindView(R.id.tv_header_years)
    TextView tv_header_years;
    @BindView(R.id.tv_header_countries)
    TextView tv_header_countries;
    @BindView(R.id.tv_header_title)
    TextView tv_header_title;
    //content
    @BindView(R.id.layout_content)
    LinearLayout layout_content;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title_aka)
    TextView tv_title_aka;
    @BindView(R.id.tv_summary)
    TextView tv_summary;

    private String title;
    private String imgUrl;
    private String id;
    private String share;
    private String MovieUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        Intent intent = getIntent();

        if (intent != null) {
            title = intent.getStringExtra("MovieTitle");
            id = intent.getStringExtra("MovieID");
            imgUrl = intent.getStringExtra("MovieImg");
            Log.v("Moviedetail ", "id:" + id + "-title:" + title);
            tv_header_title.setText(title);
//            getSupportActionBar().setTitle(title);


        }
//        fab.setBackgroundColor(getResources().getColor(R.color.color_fab_scrolling));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (share == null)
                    Toast.makeText(getBaseContext(), "未加载完毕", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, share);
                    intent.setType("text/plain");
                    startActivity(intent);
                }
            }
        });
        getMovieDetail();
        setHeader();
    }

    private void setHeader() {
        // 高斯模糊背景 原来 参数：12,5  23,4
        Glide.with(this).load(imgUrl)
                .bitmapTransform(new BlurTransformation(this, 23, 4))
                .into(iv_background_scrolling);
        toolbar.setAlpha(1.0f);
    }

    private void getMovieDetail() {
        if (id != null) {
            showProgressDialog();
            APIService.getINSTANCE().getDouBanService()
                    .getMovieDetail(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MovieDetailBean>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull MovieDetailBean moiveDetailBean) {
                            hideProgressDialog();
                            //header

                            Glide.with(getApplicationContext()).
                                    load(moiveDetailBean.getImages().getLarge())
                                    .into(iv_header_photo);
                            tv_header_rating_rate.setText("" + moiveDetailBean.getRating().getAverage());
                            tv_header_rating_number.setText(moiveDetailBean.getRatings_count() + "人评分");

                            tv_header_directors.setText(StringFormatUtils.formatCastsToString(moiveDetailBean.getDirectors()));

                           //casts
                            tv_header_casts.setText(StringFormatUtils.formatCastsToString(moiveDetailBean.getCasts()));
                            //geners
                            tv_header_genres.setText(StringFormatUtils.formatListToString(moiveDetailBean.getGenres()));
                            //year
                            tv_header_years.setText(moiveDetailBean.getYear());
                            //countries
                            tv_header_countries.setText(StringFormatUtils.formatListToString(moiveDetailBean.getCountries()));

                            //content
                            //aka
                            tv_title_aka.setText(StringFormatUtils.formatListToString(moiveDetailBean.getAka()));
                            tv_summary.setText(moiveDetailBean.getSummary());
                            MovieUrl = moiveDetailBean.getMobile_url();
                            share = moiveDetailBean.getOriginal_title() + '\n' + moiveDetailBean.getMobile_url();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.v("detail onError", e.toString());
                            showError(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            hideProgressDialog();
                            collapsingToolbarLayout.setTitle(title);
                            //设置当toolbar扩展时为透明，即不可见，只有收缩时可见
                            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent_white));
                            collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                        }
                    });
        }
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
        layout_content.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
        layout_content.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        if (frameLayout != null) {
            Snackbar.make(frameLayout, getString(R.string.please_check_your_network), Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getMovieDetail();
                }
            }).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_moreInfo) {
            showCustomTabs();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCustomTabs() {
        if (MovieUrl == null) return;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(MovieUrl));
    }
}
