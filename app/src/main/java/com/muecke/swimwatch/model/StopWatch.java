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

import java.util.ArrayList;

/**
 * A {@link StopWatch} records start, laps and stop, and print them to logcat.
 */
public class StopWatch {
    private final String mLabel;
    private boolean stop;
    private final ArrayList<Long> mTimes = new ArrayList<>();
    private final ArrayList<String> mLapLabels = new ArrayList<>();

    private StopWatch(String label) {
        mLabel = label;
        stop=false;
        lap("");
    }

    /**
     * Create a new instance and start it.
     */
    public static StopWatch start(String label) {
        return new StopWatch(label);
    }

    /**
     * Record a lap.
     */
    public void lap(String lapLabel) {
        mTimes.add(System.currentTimeMillis());
        mLapLabels.add(lapLabel);
    }

    public Long getElapsedTime() {
        if (stop) {
            final long start = mTimes.get(0);
            final long stop = mTimes.get(mTimes.size() - 1);
            return stop - start;
        } else {
            return System.currentTimeMillis() - mTimes.get(0);
        }
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
        lap("Stop");
        this.stop = true;

        final long start = mTimes.get(0);
        final long stop = mTimes.get(mTimes.size() - 1);
        final long total = stop - start;

        final StringBuilder sb = new StringBuilder();
        sb.append(mLabel);
        sb.append(",");
        sb.append(total);
        sb.append(": ");
        long last = start;
        for (int i = 1; i < mTimes.size(); i++) {
            final long current = mTimes.get(i);
            sb.append(mLapLabels.get(i));
            sb.append(",");
            sb.append((current - last));
            sb.append(" ");
            last = current;
        }
        Log.v(TAG, sb.toString());
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
            super(null);
        }
        @Override
        public void lap(String lapLabel) {
            // noop
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
