package tmdb.commovie.movie.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmdb.commovie.movie.R;
import tmdb.commovie.movie.model.Movie;
import tmdb.commovie.movie.model.MoviesWraper;
import tmdb.commovie.movie.rest.ApiClient;
import tmdb.commovie.movie.rest.ApiInterface;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.movies_listing);

        ApiInterface apiInterface = ApiClient.getApiInterface();

        Call<MoviesWraper> call = apiInterface.highestRatedMovies();
        call.enqueue(new Callback<MoviesWraper>() {
            @Override
            public void onResponse(Call<MoviesWraper> call, Response<MoviesWraper> response) {
                if (response.isSuccessful()){
                    List<Movie> movies = response.body().getMovieList();
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new MoviesListingAdapter(movies));
                }
            }

            @Override public void onFailure(Call<MoviesWraper> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
