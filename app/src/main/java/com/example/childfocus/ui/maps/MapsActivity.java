package com.example.childfocus.ui.maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.childfocus.model.DisplayLocation;
import com.example.childfocus.model.Poster;
import com.example.childfocus.ui.login.UserToken;
import com.example.childfocus.ui.main.MainActivity;
import com.example.childfocus.R;
import com.example.childfocus.utils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private TextView mTextMessage;
    private GoogleMap mMap;

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

    List<Poster> posters = new ArrayList<>();

    private static final LatLng lAT_LNG_DEFAULT_BRUXELLES = new LatLng(50.85045, 4.34878);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        pageMainActivity();
                        return true;
                    case R.id.navigation_dashboard:
                        return true;
                    case R.id.navigation_notifications:
                        pageMainActivity();
                        return true;
                }
                return false;
            };

    public void pageMainActivity() {
        this.startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

        initMap();
    }

    private MarkerOptions createMarkerOption(DisplayLocation location) {
        return new MarkerOptions()
                .position(new LatLng(location.getCoordinate().getLatitude().doubleValue(), location.getCoordinate().getLongitude().doubleValue()))
                .title(location.getPoster().getMissingPerson().getFirstname() + location.getPoster().getMissingPerson().getLastname())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .anchor(0.5f, 1f);
    }

    public LatLng getPosition() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //obtenir les providers
        // LocationManager.GPS_PROVIDER pour le GPS
        // LocationManager.NETWORK_PROVIDER pour la triangulation.
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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

    public void initMap() {
        HttpUtils.get("api/posters", UserToken.getInstance().getToken(), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try {
                    posters = mapper.readValue(responseBody, new TypeReference<List<Poster>>(){});
                    posters.forEach(poster -> poster.getDisplayLocations().forEach(displayLocation -> displayLocation.setPoster(poster)));
                    posters.stream()
                            .map(Poster::getDisplayLocations).flatMap(List::stream)
                            .map(MapsActivity.this::createMarkerOption)
                            .forEach(mMap::addMarker);

                    mMap.addMarker(new MarkerOptions().position(MapsActivity.this.getPosition()).title("Moi")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).anchor(0.5f, 1f));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MapsActivity.this.getPosition(), 8.0f));
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
