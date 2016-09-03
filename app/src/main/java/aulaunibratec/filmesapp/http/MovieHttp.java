package aulaunibratec.filmesapp.http;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aulaunibratec.filmesapp.model.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by felipe on 03/09/16.
 */
public class MovieHttp {

    public static final String API_URL = "http://www.omdbapi.com/?s=%s&y=&plot=full&r=json";

    public static List<Movie> searchMovies(String query)
    {
        List<Movie> movies = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = String.format(API_URL, query);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            Gson gson = new Gson();
            String jsonList = jsonArray.toString();
            Movie[] moviesArray = gson.fromJson(jsonList,Movie[].class);
            movies.addAll(Arrays.asList(moviesArray));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        return movies;
    }
}
