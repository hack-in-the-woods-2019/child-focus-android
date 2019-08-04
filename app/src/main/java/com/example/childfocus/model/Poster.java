package com.example.childfocus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Poster {

    private Long id;

    private MissingPerson missingPerson;

    private List<DisplayLocation> displayLocations;

    public Poster() {
        displayLocations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DisplayLocation> getDisplayLocations() {
        return Collections.unmodifiableList(displayLocations);
    }

    public void addDisplayLocation(DisplayLocation displayLocation) {
        displayLocations.add(displayLocation);
    }

    public void removeDisplayLocation(DisplayLocation displayLocation) {
        displayLocations.removeIf(location -> location.equals(displayLocation));
    }

    public MissingPerson getMissingPerson() {
        return missingPerson;
    }

    public void setMissingPerson(MissingPerson missingPerson) {
        this.missingPerson = missingPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poster poster = (Poster) o;
        return Objects.equals(id, poster.id) &&
                Objects.equals(missingPerson, poster.missingPerson) &&
                displayLocations.equals(poster.displayLocations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, missingPerson, displayLocations);
    }

    @Override
    public String toString() {
        return "Poster{" +
          "id=" + id +
          ", displayLocations=" + displayLocations +
          '}';
    }
}
