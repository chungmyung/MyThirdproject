package com.chungmyung.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @butterknife.BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

        mViewPager.setAdapter(new MypagerAdapter(getSupportFragmentManager()));
    }

    private static class MypagerAdapter extends FragmentPagerAdapter {

        public MypagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CurrentWeatherFragment();
                case 1 :
                    return new ForecastWeatherFragment();
                case 2 :
                    return new ForecasFragment();
            }
            return  null;
        }

        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position){
            switch (position) {
                case 0 :
                    return "현재 날씨";
                case 1 :
                    return "예보 날씨";
                case 2 :
                    return  "리스트 뷰" ;
            }
            return  null ;
        }
    }

}
