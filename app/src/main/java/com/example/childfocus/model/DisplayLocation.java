package com.example.childfocus.model;

import java.util.Objects;

public class DisplayLocation {
    private Long id;

    private Coordinate coordinate;

    public DisplayLocation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayLocation that = (DisplayLocation) o;
        return Objects.equals(id, that.id) &&
          Objects.equals(coordinate, that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinate);
    }

    @Override
    public String toString() {
        return "DisplayLocation{" +
          "id=" + id +
          ", coordinate=" + coordinate +
          '}';
    }
}
