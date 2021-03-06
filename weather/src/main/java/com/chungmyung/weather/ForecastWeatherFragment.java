package com.chungmyung.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chungmyung.weather.models.models.models.forecast.ForecastWeather;
import com.chungmyung.weather.retrofit.WeatherUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-09-26.
 */

public class ForecastWeatherFragment extends Fragment {

    @BindView(R.id.city_edit_text)
    EditText mCityEditText;
    @BindView(R.id.temp_text_view)
    TextView mTempTextView;
    @BindView(R.id.pressure_text_view)
    TextView mPressureTextView;
    @BindView(R.id.humidity_text_view)
    TextView mHumidityTextView;
    @BindView(R.id.min_temp_text_view)
    TextView mMinTempTextView;
    @BindView(R.id.max_temp_text_view)
    TextView mMaxTempTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.city_text_view)
    TextView mCityTextView;
    Unbinder unbinder;

    private WeatherUtil mWeatherUtil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        unbinder = ButterKnife.bind(this, view);

        mCityEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                Toast.makeText(getContext(), "" + keyCode, Toast.LENGTH_SHORT).show();
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    //검색버튼을 뗄때
                    search(mCityEditText.getText().toString());


                    dismissKeyboard();
                    return true;  //  이벤트 소비...
                }
                return false;
            }
        });
        return view;
    }

    private void dismissKeyboard() {

        InputMethodManager inputMethodManger;
        inputMethodManger = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManger.hideSoftInputFromInputMethod(mCityEditText.getWindowToken(), 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWeatherUtil = new WeatherUtil();
    }

    private void search(String cityName) {
        mProgressBar.setVisibility(View.VISIBLE);

        //Openweater
        mWeatherUtil.getApiService().getForecastWeather(cityName).enqueue(new Callback<ForecastWeather>() {
            @Override
            public void onResponse(Call<ForecastWeather> call, Response<ForecastWeather> response) {
                Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ForecastWeather> call, Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
