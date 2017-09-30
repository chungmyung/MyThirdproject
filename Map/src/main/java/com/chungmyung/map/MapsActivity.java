package com.chungmyung.map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
}


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Add a marker in Sydney and move the camera
        LatLng youngTong = new LatLng(37.260407,127.07674599999996);
        mMap.addMarker(new MarkerOptions().position(youngTong).title("청명고등학교"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(youngTong));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(youngTong, 17.0f));
        CameraUpdateFactory.zoomTo(6.0f);

        // Add a marker in Sydney and move the camera
        LatLng chomackgol = new LatLng(37.3536153,126.91856459999997);
        mMap.addMarker(new MarkerOptions().position(chomackgol).title("초막골생태공원"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chomackgol));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chomackgol, 15.0f));
        CameraUpdateFactory.zoomTo(6.0f);
    }
}
