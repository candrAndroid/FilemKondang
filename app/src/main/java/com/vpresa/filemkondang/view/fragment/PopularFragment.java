package com.vpresa.filemkondang.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import com.vpresa.filemkondang.App;
import com.vpresa.filemkondang.R;
import com.vpresa.filemkondang.controller.MovieController;
import com.vpresa.filemkondang.event.MovieErrorEvent;
import com.vpresa.filemkondang.event.MovieEvent;
import com.vpresa.filemkondang.model.Movie;
import com.vpresa.filemkondang.model.MovieResponse;
import com.vpresa.filemkondang.restapi.RestAPIURL;
import com.vpresa.filemkondang.utility.AppConstant;
import com.vpresa.filemkondang.utility.CommonFunction;
import com.vpresa.filemkondang.view.activity.DetailActivity;
import com.vpresa.filemkondang.view.adapter.MoviesAdapter;

/**
 * Created by alodokter-it on 10/05/17 -- PopularFragment.
 */

public class PopularFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout movieLayout;
    private RecyclerView movieRecyclerView;
    private ImageView movieHeaderPic;
    private TextView movieHeaderTitle;
    private RelativeLayout movieLoading;
    private SwipeRefreshLayout movieRefresh;
    private FloatingActionButton movieScrollTop;
    private LinearLayout movieError;
    private ImageView movieErrorPic;
    private TextView movieErrorContent;
    private MoviesAdapter moviesAdapter;
    private ArrayList<Movie> movieArrayList;
    private Handler changeHeaderHandler;
    private Runnable changeHeaderRunnable;
    private int page;
    private int maxPage;
    private MovieController controller;
    private int randomList = -1;
    private EventBus eventBus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        controller = new MovieController();

        movieLayout = (RelativeLayout) view.findViewById(R.id.movie_layout);
        movieRecyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
        movieHeaderPic = (ImageView) view.findViewById(R.id.movie_header_pic);
        movieHeaderTitle = (TextView) view.findViewById(R.id.movie_header_title);
        movieLoading = (RelativeLayout) view.findViewById(R.id.movie_loading);
        movieRefresh = (SwipeRefreshLayout) view.findViewById(R.id.movie_refresh);
        movieScrollTop = (FloatingActionButton) view.findViewById(R.id.movie_scroll_top);
        movieError = (LinearLayout) view.findViewById(R.id.movie_error_layout);
        movieErrorPic = (ImageView) view.findViewById(R.id.movie_error_pic);
        movieErrorContent = (TextView) view.findViewById(R.id.movie_error_content);

        movieScrollTop.setOnClickListener(this);
        movieScrollTop.hide();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                autoLoadMovie(recyclerView);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int verticalOffset = recyclerView.computeVerticalScrollOffset();
                    if (verticalOffset > 550) movieScrollTop.show();
                }
            }
        });

        moviesAdapter = new MoviesAdapter(this.getContext());
        movieRecyclerView.setAdapter(moviesAdapter);

        movieArrayList = new ArrayList<>();

        movieRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        movieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                launchGetMovies();
            }
        });

        changeHeaderHandler = new Handler();

        changeHeaderRunnable = new Runnable() {
            @Override
            public void run() {
                setHeaderLayout();
            }
        };

        launchGetMovies();
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus = App.getInstance().getEventBus();
        eventBus.register(this);
        if (movieArrayList.size() > 0) setHeaderLayout();
    }

    @Override
    public void onPause() {
        changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
        eventBus.unregister(this);
        super.onPause();
    }

    private void autoLoadMovie(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                if (movieArrayList.size() % 20 == 0 && lastVisibleItemPosition != 0) {
                    getMoviesFromBottom();
                }
            }
        }
        movieScrollTop.hide();
    }

    private void getMoviesFromBottom() {
        page += 1;
        if (page < maxPage) {
            changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
            getMovies();
        } else {
            page--;
        }
    }

    private void launchGetMovies() {
        page = 1;
        maxPage = 1;
        movieArrayList.clear();
        moviesAdapter.resetData();
        movieRefresh.setRefreshing(false);
        movieLayout.setVisibility(View.GONE);
        movieError.setVisibility(View.GONE);
        movieLoading.setVisibility(View.VISIBLE);
        changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
        movieRecyclerView.removeAllViews();
        getMovies();
    }

    private void getMovies() {
        controller.getPopularMovies(page);
    }

    private void setDataResponse(MovieResponse body) {
        page = body.getPage();
        maxPage = body.getTotalPages();

        List<Movie> data = new ArrayList<>();
        for (Movie movie : body.getResults()) {
            if (!movieArrayList.contains(movie)) {
                data.add(movie);
            }
        }

        movieArrayList.addAll(data);
        moviesAdapter.addAll(data);

        if (movieArrayList.size() > 0) {
            movieLayout.setVisibility(View.VISIBLE);
            setHeaderLayout();
        } else {
            setErrorLayout(AppConstant.ErrorType.EMPTY, AppConstant.NO_MOVIES);
        }

        movieLoading.setVisibility(View.GONE);
    }

    private void setHeaderLayout() {
        setRandomHeader();
        changeHeaderHandler.postDelayed(changeHeaderRunnable, AppConstant.HEADER_TIME);
    }

    private void setRandomHeader() {
        int tempRandomList;
        do {
            tempRandomList = (int) (Math.random() * movieArrayList.size());
        } while (tempRandomList == randomList);

        randomList = tempRandomList;

        movieHeaderTitle.setText(movieArrayList.get(randomList).getTitle());

        if (movieArrayList.get(randomList).getBackdropPath() != null) {
            CommonFunction.setImage(getContext(), RestAPIURL.getUrlImage(movieArrayList.get(randomList).getBackdropPath()), movieHeaderPic);
        } else {
            CommonFunction.setImage(getContext(), RestAPIURL.getUrlImage(movieArrayList.get(randomList).getPosterPath()), movieHeaderPic);
        }

        movieHeaderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.MOVIE_ID, movieArrayList.get(randomList).getId());
                bundle.putString(AppConstant.MOVIE_TITLE, movieArrayList.get(randomList).getTitle());
                CommonFunction.moveActivity(getContext(), DetailActivity.class, bundle, false);
            }
        });
    }

    private void setErrorLayout(AppConstant.ErrorType errorType, String error) {
        if (page > 1) {
            Snackbar.make(movieLayout, AppConstant.ERROR_CONNECTION_TEXT, Snackbar.LENGTH_SHORT)
                    .setAction("Okay", null)
                    .show();
            page--;
        } else {
            movieLayout.setVisibility(View.GONE);
            movieLoading.setVisibility(View.GONE);
            movieError.setVisibility(View.VISIBLE);
            movieErrorContent.setText(error);
            if (errorType.equals(AppConstant.ErrorType.CONNECTION))
                movieErrorPic.setImageResource(R.drawable.ic_signal);
            else movieErrorPic.setImageResource(R.drawable.ic_logo_filemkondang);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_scroll_top:
                movieRecyclerView.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieList(MovieEvent event) {
        setDataResponse(event.getBody());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieListError(MovieErrorEvent event) {
        Log.e("errorResultData", event.getMessage());
        setErrorLayout(AppConstant.ErrorType.CONNECTION, AppConstant.ERROR_CONNECTION_TEXT);
    }
}