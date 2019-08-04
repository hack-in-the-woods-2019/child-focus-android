package com.example.childfocus;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PositionGps extends Activity {

    private LocationManager locationManager;
    private LocationProvider locationProvider;
    private Location location;


    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d("GPS", "Latitude " + location.getLatitude() + " et longitude " + location.getLongitude());
        }
    };

    private static final LatLng lAT_LNG_DEFAULT_BRUXELLES = new LatLng(50.85045, 4.34878);

    public PositionGps() {
    }

    public LatLng getPosition(ContextWrapper context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //obtenir les providers
        // LocationManager.GPS_PROVIDER pour le GPS
        // LocationManager.NETWORK_PROVIDER pour la triangulation.
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //obtenir la dernière position
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return lAT_LNG_DEFAULT_BRUXELLES;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 150, locationListener);

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
