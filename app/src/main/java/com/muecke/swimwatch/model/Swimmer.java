package com.muecke.swimwatch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Swimmer implements Serializable {


    private  SwimmingStatus status;
    private  StopWatch watch;
    private  StopWatch pause;
    private List<Long> lap = new ArrayList<>();

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
            watch.stopAndLog("Swimmer");
            lap.add(watch.getElapsedTime());
        }
    }

    public List<Long> getLap() {
        return lap;
    }

    public String formatTime(Long time) {
        int swimTime = (int) (time/100);
        int totmin = swimTime / 600;
        int totsec = swimTime % 600;
        return String.format("%02d:%02d,%d", totmin, totsec/10, totsec%10);
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
        lap.clear();
        watch = StopWatch.getNullStopWatch();
        pause = StopWatch.getNullStopWatch();
    }

    public String[] getTimes() {
        List<String> swimTime = new ArrayList<>();
        for (Long lapTime : lap) {
            swimTime.add(formatTime(lapTime));
        }

        return swimTime.toArray(new String[swimTime.size()]);
    }
}
