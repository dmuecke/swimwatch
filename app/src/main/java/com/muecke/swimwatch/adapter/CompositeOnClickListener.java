package com.muecke.swimwatch.adapter;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class CompositeOnClickListener implements AdapterView.OnItemClickListener {
    List<AdapterView.OnItemClickListener> listeners;

    public CompositeOnClickListener(){
        listeners = new ArrayList<AdapterView.OnItemClickListener>();
    }

    public void addOnClickListener(AdapterView.OnItemClickListener listener){
        listeners.add(listener);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for(AdapterView.OnItemClickListener listener : listeners){
            listener.onItemClick(parent,view,position,id);
        }
    }
}