package tmdb.commovie.movie.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tmdb.commovie.movie.model.MoviesWraper;
import tmdb.commovie.movie.model.ReviewsWrapper;
import tmdb.commovie.movie.model.VideoWrapper;

/**
 * Created by Bagdat Eshmuratov on 12.12.2017.
 * email: eshmuratovbagdat@gmail.com.
 */

public interface ApiInterface {

    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Call<MoviesWraper> popularMovies();

    @GET("3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    Call<MoviesWraper> highestRatedMovies();

    @GET("3/movie/{movieId}/videos")
    Call<VideoWrapper> trailers(@Path("movieId") String movieId);

    @GET("3/movie/{movieId}/reviews")
    Call<ReviewsWrapper> reviews(@Path("movieId") String movieId);

}
