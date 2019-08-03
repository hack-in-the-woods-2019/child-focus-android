package com.example.childfocus;

public class Mission {
    public Status getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public enum Status {
        ACCEPTED,
        PENDING,
        REFUSED
    }
    private long id;
    private Status status;

    public Mission(long id,Status status){
        this.id = id;
        this.status = status;
    }


}
