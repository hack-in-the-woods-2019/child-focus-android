package com.example.childfocus.model;

import java.util.Arrays;
import java.util.Objects;

public class MissingPerson {
    private Long id;

    private String firstname;
    private String lastname;
    private String pictureFileName;
    private byte[] picture;

    public MissingPerson() {}

    public MissingPerson(Long id, String firstname, String lastname, byte[] picture) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissingPerson that = (MissingPerson) o;
        return id.equals(that.id) &&
                firstname.equals(that.firstname) &&
                lastname.equals(that.lastname) &&
                pictureFileName.equals(that.pictureFileName) &&
                Arrays.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, firstname, lastname, pictureFileName);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @Override
    public String toString() {
        return "MissingPerson{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", pictureFileName='" + pictureFileName + '\'' +
                '}';
    }
}
