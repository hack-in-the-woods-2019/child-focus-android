package com.example.childfocus.model;

import java.util.Objects;

public class Address {
    private Long id;

    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;

    public Address() {
    }

    public Address(Long id, String street, String houseNumber, String postalCode, String city, Coordinate coordinate) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id.equals(address.id) &&
                street.equals(address.street) &&
                houseNumber.equals(address.houseNumber) &&
                postalCode.equals(address.postalCode) &&
                city.equals(address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, houseNumber, postalCode, city);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}