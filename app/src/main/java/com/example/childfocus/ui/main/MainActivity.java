package com.example.childfocus.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.childfocus.Mission;
import com.example.childfocus.R;
import com.example.childfocus.ui.login.UserToken;
import com.example.childfocus.ui.maps.MapsActivity;
import com.example.childfocus.utils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListIterator<Mission> missions;

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

        missions = new ArrayList<Mission>().listIterator();
        missionSuivante();

        setContentView(R.layout.activity_main);

        missingIdentity = findViewById(R.id.missingIdentity);
        missingLocalIdentity = findViewById(R.id.missingLocalIdentity);
        receptionMissingAffiche = findViewById(R.id.receptionMissingAffiche);

        buttonAcceptation = findViewById(R.id.buttonAcceptation);
        buttonAcceptation.setOnClickListener(view -> accepterMission());

        buttonRefused = findViewById(R.id.buttonRefused);
        buttonRefused.setOnClickListener(view -> refuserMission());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void updateMissions() {
        HttpUtils.get("api/missions/poll", UserToken.getInstance().getToken(), null, newMissionsResponseHandler());
    }

    private AsyncHttpResponseHandler newMissionsResponseHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    List<Mission> retrievedMissions = mapper.readValue(responseBody, new TypeReference<List<Mission>>(){});
                    retrievedMissions.forEach(missions::add);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        };
    }

    private void accepterMission() {
        repondreMission(Mission.Status.ACCEPTED);
    }

    private void refuserMission() {
        repondreMission(Mission.Status.REFUSED);
    }

    private void repondreMission(Mission.Status status) {
        Mission mission = new Mission(identifiantMission, status);
        HttpUtils.post("api/missions/answer", UserToken.getInstance().getToken(), mission, HttpUtils.dumbResponseHandler());

        missionSuivante();
    }

    private void missionSuivante() {
        updateMissions();

        if (missions.hasNext()) {
            Mission mission = missions.next();
            missingIdentity.setText(String.valueOf(mission.getId()));
        }
    }

    public void pageMapsActivity(){
        this.startActivity(new Intent(this,MapsActivity.class));
    }

}
