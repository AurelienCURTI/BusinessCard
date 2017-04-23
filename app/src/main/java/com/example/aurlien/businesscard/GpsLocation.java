/*

package com.example.aurlien.businesscard;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
*/
/**
 * Created by Elia on 23/04/2017.
 */
/*
import com.example.aurlien.businesscard.MainActivity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.aurlien.businesscard.MainActivity;

public class GpsLocation extends MainActivity implements LocationListener {

    GoogleMap mMap;
    LocationManager locationManager;

// Get the LocationManager object from the System Service LOCATION_SERVICE
    locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

    // Create a criteria object needed to retrieve the provider
    Criteria criteria = new Criteria();

    // Get the name of the best available provider
    String provider = locationManager.getBestProvider(criteria, true);

    // We can use the provider immediately to get the last known location
    Location location = locationManager.getLastKnownLocation(provider);

// request that the provider send this activity GPS updates every 20 seconds
    locationManager.requestLocationUpdates(provider, 20000, 0, this);



    @Override
    public void onLocationChanged(Location location) {
        if (mMap != null) {
            {
                drawMarker(location);
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        }

    @Override
    public void onProviderEnabled(String s) {

        }

    @Override
    public void onProviderDisabled(String s) {

        }

    private void drawMarker(Location location){
        mMap.clear();

//  convert the location object to a LatLng object that can be used by the map API
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

// zoom to the current location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,16));

// add a marker to the map indicating our current position
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude()));

    }
*/