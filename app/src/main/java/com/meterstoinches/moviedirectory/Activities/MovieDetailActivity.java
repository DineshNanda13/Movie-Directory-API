package com.meterstoinches.moviedirectory.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.meterstoinches.moviedirectory.Model.Movie;
import com.meterstoinches.moviedirectory.R;
import com.meterstoinches.moviedirectory.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private TextView movieTitle;
    private ImageView movieImage;
    private TextView movieYear;
    private TextView directors;
    private TextView genre;
    private TextView actors;
    private TextView category;
    private TextView rating;
    private TextView writers;
    private TextView plot;
    private TextView boxOffice;
    private TextView awards;
    private TextView language;
    private TextView country;
    private TextView runtime;

    private RequestQueue queue;
    private String movieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        queue = Volley.newRequestQueue(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        movieID = movie.getImdbID();

        setUpUI();
        getMovieDetails(movieID);
    }

    private void getMovieDetails(String id) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Constants.URL + id +Constants.URL_RIGHT, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if(response.has("Ratings")){
                        JSONArray ratingsArray = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null;

                        if(ratingsArray.length() > 0){
                            JSONObject mRatings = ratingsArray.getJSONObject
                                                    (ratingsArray.length() - 1);

                            source = mRatings.getString("Source");
                            value = mRatings.getString("Value");

                            rating.setText(source + ": " +value);
                        }else {
                            rating.setText("Ratings: N/A");
                        }

                        movieTitle.setText(response.getString("Title"));
                        movieYear.setText("Released: "+response.getString("Released"));
                        //category.setText("Category: "+response.getString("Category"));
                        directors.setText("Director: "+response.getString("Director"));
                        writers.setText("Writers: "+response.getString("Writer") );
                        plot.setText("Plot: "+response.getString("Plot"));
                        runtime.setText("Runtime: "+response.getString("Runtime"));
                        genre.setText("Genre: "+response.getString("Genre"));
                        actors.setText("Actors: "+response.getString("Actors"));

                        Picasso.get()
                                .load(response.getString("Poster"))
                                .into(movieImage);

                        boxOffice.setText("Box Office: "+response.getString("BoxOffice"));
                        awards.setText("Awards: "+response.getString("Awards"));
                        language.setText("Language: "+response.getString("Language"));
                        country.setText("Country: "+response.getString("Country"));

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Error: "+error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void setUpUI() {

        movieTitle = (TextView) findViewById(R.id.movieTitleIdDets);
        movieImage = (ImageView) findViewById(R.id.movieImageIdDets);
        movieYear = (TextView) findViewById(R.id.movieReleaseIdDets);
        directors = (TextView) findViewById(R.id.directedByIdDets);
        //category = (TextView) findViewById(R.id.movieCatIdDets);
        rating = (TextView) findViewById(R.id.movieRatingIdDets);
        writers = (TextView) findViewById(R.id.writersIdDets);
        plot = (TextView) findViewById(R.id.plotIdDets);
        boxOffice = (TextView) findViewById(R.id.boxOfficeIdDets);
        awards = (TextView) findViewById(R.id.awardsIdDets);
        runtime = (TextView) findViewById(R.id.runtimeIdDet);
        genre = (TextView) findViewById(R.id.movieGenreIdDets);
        actors = (TextView) findViewById(R.id.actorsIdDets);
        language = (TextView) findViewById(R.id.languageIdDets);
        country = (TextView) findViewById(R.id.countryIdDets);

    }
}
