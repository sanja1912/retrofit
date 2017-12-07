package com.example.dev7.retrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import com.example.dev7.retrofit.R;
import com.example.dev7.retrofit.adapter.MovieAdapter;
import com.example.dev7.retrofit.model.MovieResponse;
import com.example.dev7.retrofit.rest.MovieApiService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

        movieApiService.getTopRatedMovies(API_KEY)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "In onComplete()");
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        Log.d(TAG, "In onNext()");

                        adapter.setMovies(movieResponse.getResults());
                        adapter.notifyDataSetChanged();
                    }
                }
                );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }
}

