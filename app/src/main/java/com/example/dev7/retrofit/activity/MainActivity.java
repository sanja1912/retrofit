package com.example.dev7.retrofit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dev7.retrofit.R;
import com.example.dev7.retrofit.adapter.MovieAdapter;
import com.example.dev7.retrofit.model.Movie;
import com.example.dev7.retrofit.model.MovieResponse;
import com.example.dev7.retrofit.rest.MovieApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    private static final String TAG=MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    public static final String API_KEY="a0cce257672d7bda0b2483231292f85e";
    private RecyclerView recyclerView=null;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        connectAndGetApiData();


    }

    private void connectAndGetApiData() {
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .build();
        }

        MovieApiService movieApiService=retrofit.create(MovieApiService.class);
        Call<MovieResponse>call=movieApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies=response.body().getResults();
                adapter = new MovieAdapter(getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.setMovies(movies);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());

            }
        });
    }


}
