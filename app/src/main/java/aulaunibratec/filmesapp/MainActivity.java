package aulaunibratec.filmesapp;

import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu    ;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import aulaunibratec.filmesapp.http.MovieHttp;
import aulaunibratec.filmesapp.model.Movie;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    RecyclerView mRecyclerView;
    MovieAdapter mAdapter;
    List<Movie> mMoviesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = new ArrayList<>();
        mAdapter = new MovieAdapter(this, mMoviesList);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_movies);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        new MoviesTask().execute(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }



    class MoviesTask extends AsyncTask<String, Void, List<Movie>>  {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            return MovieHttp.searchMovies(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);

            if (movies != null && movies.size() > 0)
            {
                mMoviesList.addAll(movies);
                mAdapter.notifyDataSetChanged();
            }
        }
   }
}
