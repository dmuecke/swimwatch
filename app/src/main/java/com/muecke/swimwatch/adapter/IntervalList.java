package com.muecke.swimwatch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.muecke.swimwatch.R;
import com.muecke.swimwatch.model.Swimmer;
import com.muecke.swimwatch.model.SwimmingStatus;

import java.util.List;

public class IntervalList extends ArrayAdapter<Swimmer> {

    public IntervalList(Context context, List<Swimmer> objects) {
        super(context, R.layout.list_row, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = view == null ? inflater.inflate(R.layout.list_row, parent,false) : view;

        TextView timeCurrent = (TextView) rowView.findViewById(R.id.timeCurrent);
        TextView timePause = (TextView) rowView.findViewById(R.id.timePause);
        TextView timeRound = (TextView) rowView.findViewById(R.id.timeRound);

        ImageView imgBtn = (ImageView) rowView.findViewById(R.id.start_stop_swimmer);


        Swimmer swimmer = getItem(position);
        timeCurrent.setText(swimmer.getElapsedTime());
        timePause.setText(swimmer.getPauseTime());
        timeRound.setText(""+swimmer.getRound());
        if (swimmer.getStatus().equals(SwimmingStatus.ACTIVE)) {
            imgBtn.setImageResource(R.mipmap.ic_pause_circle_outline_black_48dp);
        } else {
            imgBtn.setImageResource(R.mipmap.ic_play_circle_outline_black_48dp);
        }


        return rowView;
    }
}
