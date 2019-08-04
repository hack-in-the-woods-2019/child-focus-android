package com.example.childfocus;

import java.util.Objects;

public class Mission {

    private long id;
    private Status status;

    private Mission() {}

    public Mission(long id, Status status){
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        ACCEPTED,
        PENDING,
        REFUSED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return id == mission.id &&
                status == mission.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }
}
