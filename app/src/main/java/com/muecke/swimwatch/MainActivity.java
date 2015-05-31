package com.muecke.swimwatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.muecke.swimwatch.adapter.IntervalList;
import com.muecke.swimwatch.model.Swimmer;
import com.muecke.swimwatch.model.SwimmingStatus;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity  {
    public static final String RESET = "reset";
    final int START_TIMER = 0;
    final int STOP_TIMER = 1;
    final int UPDATE_TIMER = 2;
    final int REFRESH_RATE = 100;

    final List<Swimmer> swimmers = new ArrayList<Swimmer>();
    Handler handler;
    ImageButton stopReplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ListView viewSwimmers = (ListView) findViewById(R.id.list_swimmer);

        final ArrayAdapter intervalList = new IntervalList(this, swimmers);
        viewSwimmers.setAdapter(intervalList);

        final View btnAddSwimmer = findViewById(R.id.imageBtnAddPerson);
        btnAddSwimmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swimmers.add(new Swimmer());
                intervalList.notifyDataSetChanged();
            }
        });

        stopReplay = (ImageButton) findViewById(R.id.imageButtonStopRepl);
        stopReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RESET.equals(stopReplay.getTag())) {
                    for (Swimmer swimmer : swimmers) {
                        swimmer.reset();
                    }
                    intervalList.notifyDataSetChanged();
                } else {
                    stopTimer(stopReplay);
                }
            }
        });

        viewSwimmers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Swimmer swimmer = (Swimmer) parent.getItemAtPosition(position);
                swimmer.changeSwimmingStatus();
                if (swimmer.getStatus().equals(SwimmingStatus.ACTIVE) &&
                        !handler.hasMessages(UPDATE_TIMER)) {
                    stopReplay.setImageResource(R.mipmap.ic_stop_black_48dp);
                    stopReplay.setTag("stop");

                    handler.sendEmptyMessage(START_TIMER);
                }
            }
        });

        viewSwimmers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Swimmer swimmer = (Swimmer) parent.getItemAtPosition(position);

                if (swimmer.getStatus().equals(SwimmingStatus.INACTIVE) &&
                        !handler.hasMessages(UPDATE_TIMER)) {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Threshold")
                            .setMessage("Test")
                            .setPositiveButton("Ok", null).show();
                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                    lp.alpha=0.5f;
                    dialog.getWindow().setAttributes(lp);
                }

                return true;
            }
        });

        handler = new StopWatchHandler(intervalList);

    }

    private void stopTimer(ImageButton stopReplay) {
        if (handler != null) {
            handler.sendEmptyMessage(STOP_TIMER);
        }

        if (stopReplay != null) {
            stopReplay.setImageResource(R.mipmap.ic_replay_black_48dp);
            stopReplay.setTag(RESET);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer(stopReplay);
    }

    private class StopWatchHandler extends Handler {

        private final ArrayAdapter intervalList;
        public StopWatchHandler(ArrayAdapter intervalList) {
            this.intervalList=intervalList;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_TIMER:


                case UPDATE_TIMER:
                    handler.sendEmptyMessageDelayed(UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                    intervalList.notifyDataSetChanged();
                    break;                                  //though the timer is still running
                case STOP_TIMER:
                    handler.removeMessages(UPDATE_TIMER); // no more updates.
                    break;

                default:
                    break;
            }
        }
    }



}
