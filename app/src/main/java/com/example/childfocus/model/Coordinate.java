package com.example.childfocus.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Coordinate {
    private Long id;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private Address address;

    private Coordinate() {}

    public Coordinate(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(id, that.id) &&
          latitude.equals(that.latitude) &&
          longitude.equals(that.longitude) &&
          Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, address);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
          "id=" + id +
          ", latitude=" + latitude +
          ", longitude=" + longitude +
          ", address=" + address +
          '}';
    }
}
