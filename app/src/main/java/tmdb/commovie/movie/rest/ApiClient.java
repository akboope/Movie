package tmdb.commovie.movie.rest;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bagdat Eshmuratov on 12.12.2017.
 * email: eshmuratovbagdat@gmail.com.
 */

public class ApiClient {
    public static final String BASE_URL = "http://api.themoviedb.org/";
    public static final String API_KEY = "d6d00e9c2a87f892a8ddf414370a4660";
    private static Retrofit retrofit = null;
    private static ApiInterface service;


    @NonNull
    public static ApiInterface getApiInterface() {
        if (service == null) {
            service = getClient().create(ApiInterface.class);
        }
        return service;
    }

    public static Retrofit getClient(){

        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build();

                Request request = original.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });

        client = builder.build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
