package com.example.childfocus.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.childfocus.Mission;
import com.example.childfocus.R;
import com.example.childfocus.services.MyFirebaseMessagingService;
import com.example.childfocus.ui.login.UserToken;
import com.example.childfocus.ui.maps.MapsActivity;
import com.example.childfocus.utils.HttpUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    private MapsActivity mapsActivity;
    private TextView missingIdentity;
    private TextView missingLocalIdentity;
    private TextView receptionMissingAffiche;
    private Button buttonAcceptation;
    private Button buttonRefused;
    private long identifiantMission;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
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

        buttonAcceptation.setOnClickListener(view -> choixLaNotification(Mission.Status.ACCEPTED));

        buttonRefused.setOnClickListener(view -> choixLaNotification(Mission.Status.REFUSED));
    }

    private void choixLaNotification(Mission.Status status) {
        Mission mission = new Mission(identifiantMission, status);
        HttpUtils.post("api/missions/answer", UserToken.getInstance().getToken(), mission, HttpUtils.dumbResponseHandler());

        chargerLaNouvelNotification();
    }

    private void chargerLaNouvelNotification() {
    }

    public void pageMapsActivity(){
        this.startActivity(new Intent(this,MapsActivity.class));
    }

}
