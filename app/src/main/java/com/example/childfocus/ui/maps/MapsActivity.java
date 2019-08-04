package com.example.childfocus.ui.maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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

import com.example.childfocus.Child;
import com.example.childfocus.ui.main.MainActivity;
import com.example.childfocus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    List<Child> childList = new ArrayList<>();

    private static final LatLng lAT_LNG_DEFAULT_BRUXELLES = new LatLng(50.85045, 4.34878);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pageMainActivity();
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    pageMainActivity();
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
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

        childList = this.getChildList();
        childList.forEach(c -> mMap.addMarker(
                new MarkerOptions().position(c.getLatLong())
                        .title(c.getFirstName() + " " + c.getLastName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).anchor(0.5f, 1f)
        ));
        mMap.addMarker(new MarkerOptions().position(this.getPosition()).title("Moi")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).anchor(0.5f, 1f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(this.getPosition(), 8.0f));
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

    public List<Child> getChildList() {
        Child child1 = new Child(1, 50.41136, 4.44448, "Amir", "AMAR");
        Child child2 = new Child(2, 50.63373, 5.56749, "Ilyas", "HAMOUDI");
        Child child3 = new Child(3, 51.21989, 4.40346, "Sabri", "AJRODE");
        return Arrays.asList(child1, child2, child3);
    }
}
