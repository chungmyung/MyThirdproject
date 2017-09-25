package com.chungmyung.jsonparcingexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chungmyung.jsonparcingexam.Models.Location;
import com.chungmyung.jsonparcingexam.Models.Weather;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.result_text)
    TextView mResultText;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private JsonSampleInterface mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JsonSampleInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // 만들어서
                .build();
        mApiService = retrofit.create(JsonSampleInterface.class);
    }

    public void requestLocation(View view) {


        mProgressBar.setVisibility(View.VISIBLE);

        // .enqueue  안드로이드에서  무조건 비동기
        mApiService.getLocation().enqueue(new Callback<Location>() {
            //성공했을때 오는곳
            @Override  //  <Location>  결과담아서 .. 원래는 STring으로 온다..
            public void onResponse(Call<Location> call, Response<Location> response) {
                mResultText.setText(response.body().toString());  //  결과를 뿌리면 된다...  butterknife설치
                mProgressBar.setVisibility(View.GONE);

            }

            //실패했을 때 오는 곳
            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                mResultText.setText(t.getLocalizedMessage()); // 실패하면 이곳이 뿌린다.
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    public void requestWeather(View view) {
        mProgressBar.setVisibility(View.VISIBLE);

        mApiService.getWeather().enqueue(new Callback<List<Weather>>() {
             @Override
              public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                 mResultText.setText(response.body().toString());  //  결과를 뿌리면 된다...  butterknife설치
                 mProgressBar.setVisibility(View.GONE);
             }

             @Override
             public void onFailure(Call<List<Weather>> call, Throwable t) {
                mResultText.setText(t.getLocalizedMessage()); // 실패하면 이곳이 뿌린다.
                mProgressBar.setVisibility(View.GONE);

             }
            }
        );
    }
}
