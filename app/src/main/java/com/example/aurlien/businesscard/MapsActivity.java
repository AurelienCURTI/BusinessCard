package com.example.aurlien.businesscard;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BroadcastReceiver GPSBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final Intent intent = getIntent();
        double longitude = Double.parseDouble(intent.getStringExtra("K_LONGITUDE"));
        double latitude = Double.parseDouble(intent.getStringExtra("K_LATITUDE"));
        LatLng myLatLng = new LatLng(latitude, longitude);
        CameraPosition myPosition = new CameraPosition.Builder().target(myLatLng).zoom(17).bearing(90).tilt(30).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
    }
}