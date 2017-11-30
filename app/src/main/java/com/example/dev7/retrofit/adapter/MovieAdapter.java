package com.example.dev7.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dev7.retrofit.R;
import com.example.dev7.retrofit.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev7 on 30.11.17..
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Movie> movies=new ArrayList<>();
    private Context context;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list_of_movies,parent,false);
        return new MovieViewHolder(v);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder movieHolder = (MovieViewHolder) holder;
        final Movie listOfMovies = movies.get(position);

        String image_url = IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(movieHolder.movieImage);

        movieHolder.movieTitle.setText(listOfMovies.getTitle());
        movieHolder.movieDescription.setText(listOfMovies.getOverview());
        movieHolder.date.setText(listOfMovies.getReleaseDate());
        movieHolder.raiting.setText(listOfMovies.getVoteAverage().toString());




    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
      LinearLayout movieLayout;
      TextView movieTitle;
      TextView date;
      TextView movieDescription;
      ImageView movieImage;
      TextView raiting;

        public MovieViewHolder(View itemView){
            super(itemView);
            movieLayout=itemView.findViewById(R.id.layout_movie);
            movieTitle=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            movieDescription=itemView.findViewById(R.id.description);
            movieImage=itemView.findViewById(R.id.movie_image);
            raiting=itemView.findViewById(R.id.raiting);



        }
    }
}
