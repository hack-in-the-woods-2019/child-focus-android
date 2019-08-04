package com.example.childfocus;

import com.google.android.gms.maps.model.LatLng;

public class Child {

    private int id;
    private double latitude;
    private double longitude;
    private String firstName;
    private String lastName;

    public Child(int id, double latitude, double longitude, String firstName, String lastName) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LatLng getLatLong(){
        return new LatLng(latitude,longitude);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
