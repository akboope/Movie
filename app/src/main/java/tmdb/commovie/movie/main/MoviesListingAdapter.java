package tmdb.commovie.movie.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import tmdb.commovie.movie.R;
import tmdb.commovie.movie.model.Api;
import tmdb.commovie.movie.model.Movie;

/**
 * Created by Bagdat Eshmuratov on 12.12.2017.
 * email: eshmuratovbagdat@gmail.com.
 */

class MoviesListingAdapter extends RecyclerView.Adapter<MoviesListingAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movies;

    public MoviesListingAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.movie_grid_item, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.movie = movies.get(position);
        holder.name.setText(holder.movie.getTitle());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH);

        Glide.with(context)
                .asBitmap()
                .load(Api.getPosterPath(holder.movie.getPosterPath()))
                .apply(options)
                .into(new BitmapImageViewTarget(holder.poster) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(bitmap, transition);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override public void onGenerated(Palette palette) {
                                setBackgroundColor(palette, holder);
                            }
                        });
                    }
                });
    }

    private void setBackgroundColor(Palette palette, ViewHolder holder) {
        holder.titleBackground.setBackgroundColor(palette.getVibrantColor(context
                .getResources().getColor(R.color.black_translucent_60)));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        View titleBackground;
        TextView name;

        public Movie movie;

        public ViewHolder(View root) {
            super(root);
            poster = root.findViewById(R.id.movie_poster);
            titleBackground = root.findViewById(R.id.title_background);
            name = root.findViewById(R.id.movie_name);
        }
    }
}
