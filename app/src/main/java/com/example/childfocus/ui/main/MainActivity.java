package com.example.childfocus.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.childfocus.Mission;
import com.example.childfocus.R;
import com.example.childfocus.ui.login.UserToken;
import com.example.childfocus.ui.maps.MapsActivity;
import com.example.childfocus.utils.HttpUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private MapsActivity mapsActivity;
    private TextView missingIdentity;
    private TextView missingLocalIdentity;
    private TextView receptionMissingAffiche;
    private Button buttonAcceptation;
    private Button buttonRefused;
    private long identifiantMission;

    private AsyncHttpResponseHandler asyncHttpResponseHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        };
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    pageMapsActivity();
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        missingIdentity = findViewById(R.id.missingIdentity);
        missingLocalIdentity = findViewById(R.id.missingLocalIdentity);
        receptionMissingAffiche = findViewById(R.id.receptionMissingAffiche);
        buttonAcceptation = findViewById(R.id.buttonAcceptation);
        buttonRefused = findViewById(R.id.buttonRefused);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        buttonAcceptation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choixLaNotification(Mission.Status.ACCEPTED);
            }
        });

        buttonRefused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choixLaNotification(Mission.Status.REFUSED);
            }
        });
    }

    private void choixLaNotification(Mission.Status status) {
        Mission mission = new Mission(identifiantMission, status);
        AsyncHttpResponseHandler responseHandler = asyncHttpResponseHandler();
        HttpUtils.post("api/missions/answer", UserToken.getInstance().getToken(), mission, responseHandler);

        chargerLaNouvelNotification();
    }

    private void chargerLaNouvelNotification() {
    }

    public void pageMapsActivity(){
        this.startActivity(new Intent(this,MapsActivity.class));
    }

}
