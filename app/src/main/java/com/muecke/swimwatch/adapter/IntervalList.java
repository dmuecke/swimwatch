package com.muecke.swimwatch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.muecke.swimwatch.R;
import com.muecke.swimwatch.model.Swimmer;

import java.util.List;

public class IntervalList extends ArrayAdapter<Swimmer> {

    public IntervalList(Context context, List<Swimmer> objects) {
        super(context, R.layout.list_row, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = view == null ? inflater.inflate(R.layout.list_row, parent,false) : view;

        TextView name = (TextView) rowView.findViewById(R.id.textViewLast);
        TextView split_time = (TextView) rowView.findViewById(R.id.timeCurrent);
        //TextView round = (TextView)rowView.findViewById(R.id.textViewRound);

        Swimmer swimmer = getItem(position);
      //  round.setText("" + swimmer.getRound());

        return rowView;
    }
}
