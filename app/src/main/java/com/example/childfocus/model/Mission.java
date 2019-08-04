package com.example.childfocus.model;

import java.util.Objects;

public class Mission {
    private Long id;

    private MissingPerson missingPerson;

    private Status status;

    public enum Status {
        ACCEPTED,
        PENDING,
        REFUSED
    }

    public Mission() {
    }

    public Mission(Long id, MissingPerson missingPerson, Status status) {
        this.id = id;
        this.missingPerson = missingPerson;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MissingPerson getMissingPerson() {
        return missingPerson;
    }

    public void setMissingPerson(MissingPerson missingPerson) {
        this.missingPerson = missingPerson;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return Objects.equals(id, mission.id) &&
          Objects.equals(missingPerson, mission.missingPerson) &&
          status == mission.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, missingPerson, status);
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", missingPerson=" + missingPerson +
                ", missonAccepted=" + status +
                '}';
    }
}
