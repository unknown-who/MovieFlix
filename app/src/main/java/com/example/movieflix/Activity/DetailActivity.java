package com.example.movieflix.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.movieflix.Adapter.ImagesListAdapter;
import com.example.movieflix.Domain.FilmItem;
import com.example.movieflix.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    RequestQueue mReaquestQueue;
    StringRequest mStringRequest;
    ProgressBar progressBar;
    TextView titleText,movieRateText,movieTimeText,movieDateText,movieSummaryInfo,movieActorsInfo;
    NestedScrollView scrollView;
    int idFilm;
    ShapeableImageView pic1;
    ImageView pic2,backImg;
    RecyclerView.Adapter adapterImgList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


        idFilm=getIntent().getIntExtra("id",0);
        initView();
        senRequest();
    }

    private void senRequest() {
        mReaquestQueue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/"+idFilm, response -> {
            Gson gson=new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            FilmItem item=gson.fromJson(response,FilmItem.class);
            Glide.with(DetailActivity.this)
                    .load(item.getPoster())
                    .into(pic1);
            Glide.with(DetailActivity.this)
                    .load(item.getPoster())
                    .into(pic2);
            titleText.setText(item.getTitle());
            movieRateText.setText(item.getRated());
            movieTimeText.setText(item.getRuntime());
            movieDateText.setText(item.getReleased());
            movieSummaryInfo.setText(item.getPlot());
            movieActorsInfo.setText(item.getActors());
            if(item.getImages()!=null){
                adapterImgList=new ImagesListAdapter(item.getImages());
                recyclerView.setAdapter(adapterImgList);
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            Log.i("MovieFLix","onErrorResponse:"+error.toString());
        });
        mReaquestQueue.add(mStringRequest);
    }

    private void initView() {
        titleText=findViewById(R.id.movieName);
        progressBar=findViewById(R.id.detailLoading);
        scrollView=findViewById(R.id.scrollView4);
        pic1=findViewById(R.id.posterNormalImage);
        pic2=findViewById(R.id.posterBigImage);
        movieRateText=findViewById(R.id.movieRate);
        movieTimeText=findViewById(R.id.movieTime);
        movieDateText=findViewById(R.id.moiveDate);
        movieSummaryInfo=findViewById(R.id.summeryInfo);
        movieActorsInfo=findViewById(R.id.movieActorInfo);
        backImg=findViewById(R.id.backImg);
        recyclerView=findViewById(R.id.imagesRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        backImg.setOnClickListener(v -> finish());
    }
}