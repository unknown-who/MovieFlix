package com.example.movieflix.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieflix.Adapter.FilmListAdapter;
import com.example.movieflix.Domain.ListFilm;
import com.example.movieflix.R;
import com.google.gson.Gson;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovie, adapterUpComing;
    private RecyclerView recyclerViewNewMovies, recyclerViewUpComing;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    private ProgressBar loading1, loading2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        sendRequest1();
        sendRequest2();
    }

    private void sendRequest1() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
            Gson gson = new Gson();
            loading1.setVisibility(View.GONE);
            ListFilm items = gson.fromJson(response, ListFilm.class);
            adapterNewMovie = new FilmListAdapter(items);
            recyclerViewNewMovies.setAdapter(adapterNewMovie);
        }, error -> {
            loading1.setVisibility(View.GONE);
            Log.e("movieflix", "sendRequest1: " + error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void sendRequest2() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3", response -> {
            Gson gson = new Gson();
            loading2.setVisibility(View.GONE);
            ListFilm items = gson.fromJson(response, ListFilm.class);
            adapterUpComing = new FilmListAdapter(items);
            recyclerViewUpComing.setAdapter(adapterUpComing);
        }, error -> {
            loading2.setVisibility(View.GONE);
            Log.e("movieflix", "sendRequest2: " + error.toString());
        });
        mRequestQueue.add(mStringRequest2);
    }

    private void initView() {
        recyclerViewNewMovies = findViewById(R.id.view1);
        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComing = findViewById(R.id.view2);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading2); // Corrected the assignment
    }
}
