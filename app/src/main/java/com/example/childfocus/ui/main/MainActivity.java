package com.example.childfocus.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.childfocus.ui.maps.MapsActivity;
import com.example.childfocus.model.Mission;
import com.example.childfocus.R;
import com.example.childfocus.ui.login.UserToken;
import com.example.childfocus.utils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private List<Mission> missions;
    private List<Mission> misssionsAccepted;

    private TextView missingPersonName;
    private TextView missingLocation;
    private TextView pickupLocation;
    private Button buttonAcceptation;
    private Button buttonRefused;
    private Button buttonMettreAffiche;
    private Spinner listMissionsAcceptedSpinner;

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
    private Mission currentMission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();

        missions = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        missionSuivante();
    }

    @Override
    protected void onResume() {
        super.onResume();
        missionSuivante();
    }

    private void initView() {
        missingPersonName = findViewById(R.id.missingIdentity);
        missingLocation = findViewById(R.id.missingLocalIdentity);
        pickupLocation = findViewById(R.id.receptionMissingAffiche);

        buttonAcceptation = findViewById(R.id.buttonAcceptation);
        buttonAcceptation.setOnClickListener(view -> accepterMission());

        buttonRefused = findViewById(R.id.buttonRefused);
        buttonRefused.setOnClickListener(view -> refuserMission());

        buttonMettreAffiche = findViewById(R.id.buttonPutAffiche);
        buttonMettreAffiche.setOnClickListener(view -> mettreAffiche());

        listMissionsAcceptedSpinner = findViewById(R.id.ensembleMissionsAccepted);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void mettreAffiche() {

    }

    private void fetchMissions() {
        AsyncHttpResponseHandler responseHandler = newMissionsResponseHandler();
        HttpUtils.get("api/missions/poll", UserToken.getInstance().getToken(), null, responseHandler);
    }

    private AsyncHttpResponseHandler newMissionsResponseHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    NavigableSet<Mission> retrievedMissions = new TreeSet<>(Comparator.comparing(Mission::getId));
                    retrievedMissions.addAll(mapper.readValue(responseBody, new TypeReference<List<Mission>>(){}));
                    missions.addAll(retrievedMissions);
                    updateMission();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        };
    }

    private void accepterMission() {
        repondreMission(Mission.Status.ACCEPTED);
    }

    private void refuserMission() {
        repondreMission(Mission.Status.REFUSED);
    }

    private void repondreMission(Mission.Status status) {
        currentMission.setStatus(status);

        HttpUtils.post("api/missions/answer", UserToken.getInstance().getToken(), currentMission, HttpUtils.dumbResponseHandler());

        missionSuivante();
    }

    private void missionSuivante() {
        updateMission();
        if (missions.isEmpty()) {
            fetchMissions();
        }
    }

    private void updateMission() {
        if (missions.isEmpty()) {
            hideView();
        } else {
            showView();

            currentMission = missions.remove(0);
            missingPersonName.setText(currentMission.getMissingPerson().getFirstname() + " " + currentMission.getMissingPerson().getLastname());
        }
        mettreAJourListMissionsAccepted();
        refreshDrawableState();
    }

    private void hideView() {
        toggleVisibility(View.GONE);
    }

    private void showView() {
        toggleVisibility(View.VISIBLE);
    }

    private void toggleVisibility(int visibility) {
        missingPersonName.setVisibility(visibility);
        missingLocation.setVisibility(visibility);
        pickupLocation.setVisibility(visibility);
        buttonAcceptation.setVisibility(visibility);
        buttonRefused.setVisibility(visibility);
    }

    private void refreshDrawableState() {
        missingPersonName.refreshDrawableState();
        missingLocation.refreshDrawableState();
        pickupLocation.refreshDrawableState();
        buttonAcceptation.refreshDrawableState();
        buttonRefused.refreshDrawableState();
        listMissionsAcceptedSpinner.refreshDrawableState();
    }

    public void pageMapsActivity(){
        this.startActivity(new Intent(this,MapsActivity.class));
    }



    public void mettreAJourListMissionsAccepted(){
        reprendreLaListAfficheAccepted();
        if(misssionsAccepted.isEmpty()){
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,new ArrayList<String>());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listMissionsAcceptedSpinner.setAdapter(adapter);
        }else{
            ArrayList<String> libelle = new ArrayList<>();
            libelle.add("Sarah Croch");
            libelle.add("Sarah Pell");
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,libelle);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listMissionsAcceptedSpinner.setAdapter(adapter);
        }
    }

    private void reprendreLaListAfficheAccepted() {

    }
}
