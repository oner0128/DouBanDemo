package com.android.oner0128.doubandemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.api.APIService;
import com.android.oner0128.doubandemo.bean.MoiveDetailBean;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.image_scrolling_top)
    ImageView iv_scrolling;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_scrolling)
    FloatingActionButton fab;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private String title;
    private String id;

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
            Log.v("detail id",id);
            Log.v("detail title",title);
            getSupportActionBar().setTitle(title);
        }
        fab.setBackgroundColor(getResources().getColor(R.color.color_fab_scrolling));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, title+" Share from douban");
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
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
//                            Log.v("detail",moiveDetailBean.getImages().getLarge());
                            Glide.with(getApplicationContext()).
                                    load(moiveDetailBean.getImages().getLarge())
                                    .centerCrop()
                            .into(iv_scrolling);
                            tv_title.setText(moiveDetailBean.getSummary());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.v("detail onError",e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
