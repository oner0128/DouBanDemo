package com.android.oner0128.doubandemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.oner0128.doubandemo.R;
import com.android.oner0128.doubandemo.adapter.InTheatersAdapter3;
import com.android.oner0128.doubandemo.adapter.OnLoadMoreListener;
import com.android.oner0128.doubandemo.bean.Contact;
import com.android.oner0128.doubandemo.bean.MovieBean;
import com.android.oner0128.doubandemo.presenter.InTheatersPresentImpl3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InTheatersFragment3 extends Fragment implements InTheatersView {
    @BindView(R.id.fragment_in_theaters_3)
    FrameLayout fragment_in_theaters;
    @BindView(R.id.recycler_in_theaters)
    RecyclerView recycler_in_theaters;
    //    InTheatersAdapter inTheatersAdapter;
    InTheatersAdapter3 inTheatersAdapter;
    GridLayoutManager gridLayoutManager;
    InTheatersPresentImpl3 inTheatersPresentImpl;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<Contact> contacts;
    private List<MovieBean.Subjects> movies;
    private InTheatersAdapter3 contactAdapter;
    private Random random;

    public int start, count, total;
    static InTheatersFragment3 INSTANCE;

    public static InTheatersFragment3 newINSTANCE() {
        if (INSTANCE == null) {
            synchronized (InTheatersFragment3.class) {
                if (INSTANCE == null) INSTANCE = new InTheatersFragment3();
            }
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_theaters_3, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenterAndAdapter();
        initView();
    }

    private void initPresenterAndAdapter() {
//        inTheatersAdapter = new InTheatersAdapter(
//                R.layout.menuitem_in_theaters_recyclerview,
//                new ArrayList<MovieBean.Subjects>());
        inTheatersPresentImpl = new InTheatersPresentImpl3(this);
        contacts = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.setPhone(phoneNumberGenerating());
            contact.setEmail("DevExchanges" + i + "@gmail.com");
            contacts.add(contact);
        }
        movies = new ArrayList<>();
    }

    private String phoneNumberGenerating() {
        int low = 100000000;
        int high = 999999999;
        int randomNumber = random.nextInt(high - low) + low;

        return "0" + randomNumber;
    }

    private void initView() {
        recycler_in_theaters.setLayoutManager(new LinearLayoutManager(getContext()));
//        inTheatersAdapter = new InTheatersAdapter3(recycler_in_theaters, movies, getContext());
//        loadMovies();
//        recycler_in_theaters.setAdapter(inTheatersAdapter);
        contactAdapter = new InTheatersAdapter3(recycler_in_theaters, contacts, getContext());
        recycler_in_theaters.setAdapter(contactAdapter);
        contactAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (contacts.size() <= 20) {
                    contacts.add(null);
                    contactAdapter.notifyItemInserted(contacts.size() - 1);
//                    inTheatersAdapter.notifyItemInserted(start);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            contacts.remove(contacts.size() - 1);
                            contactAdapter.notifyItemRemoved(contacts.size());
                            //Generating more data
                            int index = contacts.size();
                            int end = index + 10;
                            for (int i = index; i < end; i++) {
                                Contact contact = new Contact();
                                contact.setPhone(phoneNumberGenerating());
                                contact.setEmail("DevExchanges" + i + "@gmail.com");
                                contacts.add(contact);
                            }
                            contactAdapter.notifyDataSetChanged();
                            contactAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
//set load more listener for the RecyclerView adapter
//        inTheatersAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                if (movies.size() < total) {
//                    movies.add(null);
//                    inTheatersAdapter.notifyItemInserted(movies.size() - 1);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            movies.remove(movies.size() - 1);
//                            inTheatersAdapter.notifyItemRemoved(movies.size());
//                            //Generating more data
//                            start+=count+1;
//                            count=6;
//                            inTheatersPresentImpl.getMoreMovies(start, count);
//                            inTheatersAdapter.notifyDataSetChanged();
//                            inTheatersAdapter.setLoaded();
//                        }
//                    }, 5000);
//                } else {
//                    Toast.makeText(getContext(), "Loading data completed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void loadMovies() {
//        if (inTheatersAdapter.getItemCount() > 0) {
//            inTheatersAdapter.clearData();
//        }
        start = 0;
        count = 16;
        inTheatersPresentImpl.getInTheatersMovies(start, count);
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

    @Override
    public void updateInTheatersItems(MovieBean movieBean) {
////        inTheatersAdapter.addData(movieBean.getSubjects());
//        movies.add(null);
//        inTheatersAdapter.notifyItemInserted(movies.size() - 1);
//        movies.remove(movies.size() - 1);
//        inTheatersAdapter.notifyItemRemoved(movies.size());
        total = movieBean.getTotal();
        Log.v("total",total+"");
        movies.addAll(movieBean.getSubjects());
        Log.v("movies size",movies.size()+"");
        inTheatersAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateMoreItems(MovieBean movieBean) {
        total = movieBean.getTotal();
        movies.addAll(movieBean.getSubjects());
        Log.v("updateMoreItems-total",total+"");
        Log.v("updateMoreItems-size",movies.size()+"");
    }
}
