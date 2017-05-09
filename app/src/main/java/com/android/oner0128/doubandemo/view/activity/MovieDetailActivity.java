package com.android.oner0128.doubandemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MoiveDetailBean;
import com.android.oner0128.doubandemo.view.BaseView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity implements BaseView {
    @BindView(R.id.image_scrolling_top)
    ImageView iv_scrolling;
    @BindView(R.id.iv_post)
    ImageView iv_post;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_scrolling)
    FloatingActionButton fab;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_alt)
    TextView tv_alt;
    @BindView(R.id.tv_summary)
    TextView tv_summary;
    @BindView(R.id.fragmentlayout)
    FrameLayout frameLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    TextView noInternetText;
    private String title;
    private String id;
    private String share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        Intent intent = getIntent();

        if (intent != null) {
            title = intent.getStringExtra("MovieTitle");
            id = intent.getStringExtra("MovieID");
            Log.v("detail id", id);
            Log.v("detail title", title);
            getSupportActionBar().setTitle(title);
        }
        fab.setBackgroundColor(getResources().getColor(R.color.color_fab_scrolling));
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
    }

    private void getMovieDetail() {
        if (id != null) {
            APIService.getINSTANCE().getMovieDetailSevice()
                    .getMovieDetail(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MoiveDetailBean>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull MoiveDetailBean moiveDetailBean) {
                            hideProgressDialog();
//                            Log.v("detail",moiveDetailBean.getImages().getLarge());
                            Glide.with(getApplicationContext()).
                                    load(moiveDetailBean.getImages().getLarge())
                                    .centerCrop()
                                    .into(iv_scrolling);
                            Glide.with(getApplicationContext()).
                                    load(moiveDetailBean.getImages().getMedium())
                                    .fitCenter()
                                    .into(iv_post);
                            tv_summary.setText(moiveDetailBean.getSummary());
                            tv_title.setText(moiveDetailBean.getOriginal_title());
                            tv_alt.setText(moiveDetailBean.getMobile_url());
                            share = moiveDetailBean.getOriginal_title() + moiveDetailBean.getMobile_url();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.v("detail onError", e.toString());
                            showError(e.toString());
                        }

                        @Override
                        public void onComplete() {
hideProgressDialog();
                        }
                    });
        }
    }

    @Override
    public void showProgressDialog() {
        if (share==null)progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        if (frameLayout != null) {
            progressBar.setVisibility(View.INVISIBLE);
            ViewStub stub_text = (ViewStub) findViewById(R.id.stub_no_internet_text);
            noInternetText = (TextView) stub_text.inflate();
            Snackbar.make(frameLayout, getString(R.string.please_check_your_network), Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getMovieDetail();
                }
            }).show();
        }
    }
}
