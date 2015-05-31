package com.muecke.swimwatch.model;

import java.io.Serializable;

public class Swimmer implements Serializable {


    private  SwimmingStatus status;
    private  StopWatch watch;
    private  StopWatch pause;


    private  int round;

    public Swimmer() {
        reset();
    }

    public SwimmingStatus getStatus() {
        return status;
    }

    public void changeSwimmingStatus() {
        status = status == SwimmingStatus.ACTIVE ? SwimmingStatus.INACTIVE : SwimmingStatus.ACTIVE;
        if (status.equals(SwimmingStatus.ACTIVE)) {
            watch = StopWatch.start();
            pause = StopWatch.getNullStopWatch();

            round = round + 1;
        } else {
            pause = StopWatch.start();
            watch.stopAndLog("StopWatch");
        }
    }

    public String getElapsedTime() {
        return watch.getElaspedTime();
    }

    public int getRound() {
        return round;
    }

    public String getPauseTime() {
        return pause.getElaspedTime();
    }

    public void reset() {
        status = SwimmingStatus.INACTIVE;
        round = 0;
        watch = StopWatch.getNullStopWatch();
        pause = StopWatch.getNullStopWatch();
    }
}
