package com.example.dev7.retrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import com.example.dev7.retrofit.R;
import com.example.dev7.retrofit.adapter.MovieAdapter;
import com.example.dev7.retrofit.model.Movie;
import com.example.dev7.retrofit.model.MovieResponse;
import com.example.dev7.retrofit.rest.MovieApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {
    private static final String TAG=MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    public static final String API_KEY="a0cce257672d7bda0b2483231292f85e";
    private RecyclerView recyclerView=null;
    private MovieAdapter adapter;
    private Menu menu;
    Subscription subscription;
    private MovieApiService movieApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MovieAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        connectAndGetApiData();



    }





    private void connectAndGetApiData() {
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                     .build();
        }
        movieApiService=retrofit.create(MovieApiService.class);

        Observable<List<MovieResponse>>observable=movieApiService.getTopRatedMovies(API_KEY);
        observable.subscribe(new Observer<List<MovieResponse>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"In onComplete()");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"In onError()");
            }

            @Override
            public void onNext(List<MovieResponse> movieResponses) {
                Log.d(TAG,"In onNext()");
                for(int i=0;i<movieResponses.size();i++) {
                    adapter.setMovies(movieResponses.get(i).getResults());
                }
            }
        });


//        Observable<MovieResponse> observable =movieApiService.getTopRatedMovies(API_KEY);
//        observable.subscribe(new Observer<MovieResponse>() {
//            @Override
//            public void onCompleted() {
//                Log.d(TAG,"In onComplete()");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG,"In onError()");
//            }
//
//            @Override
//            public void onNext(MovieResponse movieResponse) {
//                Log.d(TAG,"In onNext()");
////                for(int i=0;i<movieResponses.size();i++) {
////                    MovieResponse movie=new MovieResponse();
////                    movie.setResults(movieResponses.get(i).getResults());
//
//                    adapter.setMovies(movieResponse.getResults());
////                }
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }
}

