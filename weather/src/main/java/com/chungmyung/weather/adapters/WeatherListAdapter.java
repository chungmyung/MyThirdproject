package com.chungmyung.weather.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chungmyung.weather.R;
import com.chungmyung.weather.models.models.models.forecast.List;

/**
 * Created by user on 2017-09-27.
 */

public class WeatherListAdapter extends BaseAdapter {

    private final java.util.List<List> mWeatherList;

    public WeatherListAdapter(java.util.List<List> weatherList) {
        mWeatherList = weatherList ;
    }

    @Override
    public int getCount() {
        return mWeatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWeatherList.get(position);
    }

    @Override
    public long getItemId(int postion) {
        return postion ;
    }

    @Override
    public View getView(int position, View converterView, ViewGroup viewGroup) {
        ViewHolder holder ;
        if (converterView == null) {
            holder = new ViewHolder();
            converterView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather, viewGroup,false);
            holder.icon = converterView.findViewById(R.id.icon_image_view);
            holder.temp = converterView.findViewById(R.id.temp_text_view);
            holder.min = converterView.findViewById(R.id.min_temp_text_view);
            holder.max = converterView.findViewById(R.id.max_temp_text_view);
            holder.weather = converterView.findViewById(R.id.weather_text_view);
            holder.time = converterView.findViewById(R.id.time_text_view);

            converterView.setTag(holder);

        } else {
            holder = (ViewHolder) converterView.getTag();

        }
        List weatherList = (List) getItem(position);

        String iconUrl = "http://openweathermap.org/img/w/" +
                weatherList.getWeather().get(0).getIcon() + ".png";

        Glide.with(viewGroup.getContext()).load(iconUrl).into(holder.icon);

       holder.temp.setText( "기온  :  " + weatherList.getMain().getTemp());
       holder.min.setText( "최저 기온  :  " + weatherList.getMain().getTempMax());
       holder.max.setText( "최고 기온  :  " + weatherList.getMain().getTempMin());
       holder.weather.setText("날씨  :  " + weatherList.getWeather().get(0).getMain());
       holder.time.setText("시간   :  " + weatherList.getDtTxt()) ;


        return converterView ;
    }

    private static class ViewHolder {
        TextView temp ;
        TextView min ;
        TextView max ;
        TextView weather ;
        TextView time ;

        ImageView icon  ;
    }
}
