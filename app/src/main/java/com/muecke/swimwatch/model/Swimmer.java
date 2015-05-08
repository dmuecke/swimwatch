package com.muecke.swimwatch.model;

import java.io.Serializable;

public class Swimmer implements Serializable {


    private  SwimmingStatus status;

    public Swimmer() {
        status = SwimmingStatus.INACTIVE;
    }

    public SwimmingStatus getStatus() {
        return status;
    }

    public void changeSwimmingStatus() {
        status = status == SwimmingStatus.ACTIVE ? SwimmingStatus.INACTIVE : SwimmingStatus.ACTIVE;
    }

}
