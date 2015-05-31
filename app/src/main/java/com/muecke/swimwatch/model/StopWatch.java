package com.muecke.swimwatch.model;

/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;

/**
 * A {@link StopWatch} records start, laps and stop, and print them to logcat.
 */
public class StopWatch {
    private long start;
    private long stop;

    private StopWatch(long startTime) {
        stop=-1;
        start=startTime;
    }

    /**
     * Create a new instance and start it.
     */
    public static StopWatch start() {
        return new StopWatch(System.currentTimeMillis());
    }


    public Long getElapsedTime() {

        long currentTimeMillis = stop > 0 ? stop : System.currentTimeMillis();
        return currentTimeMillis - start;
    }

    public String getElaspedTime() {
        int swimTime = (int) (getElapsedTime()/100);
        int totmin = swimTime / 600;
        int totsec = swimTime % 600;
        return String.format("%02d:%02d,%d", totmin, totsec/10, totsec%10);
    }

    /**
     * Stop it and log the result, if the total time >= {@code timeThresholdToLog}.
     */
    public void stopAndLog(String TAG) {
        this.stop = System.currentTimeMillis();

        Log.d(TAG, getElaspedTime());
    }

    /**
     * Return a dummy instance that does no operations.
     */
    public static StopWatch getNullStopWatch() {
        return NullStopWatch.INSTANCE;
    }

    private static class NullStopWatch extends StopWatch {
        public static final NullStopWatch INSTANCE = new NullStopWatch();
        public NullStopWatch() {
            super(System.currentTimeMillis());
        }

        @Override
        public void stopAndLog(String TAG) {
            // noop
        }

        @Override
        public Long getElapsedTime() {
            return Long.valueOf(0);
        }

        @Override
        public String getElaspedTime() {
            return "00:00,0";
        }
    }
}
