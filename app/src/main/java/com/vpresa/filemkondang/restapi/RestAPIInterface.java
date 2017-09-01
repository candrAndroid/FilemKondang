package com.vpresa.filemkondang.restapi;

import com.vpresa.filemkondang.model.Movie;
import com.vpresa.filemkondang.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dwicandra on 9/1/2017.
 */

public interface RestAPIInterface {
    @GET("movie/{type}")
    Call<MovieResponse> getMovies(@Path("type") String type, @Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page, @Query("region") String region);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("language") String language, @Query("page") int page);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language, @Query("append_to_response") String appendToResponse);
}
