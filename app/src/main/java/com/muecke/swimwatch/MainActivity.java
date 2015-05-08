package com.muecke.swimwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.muecke.swimwatch.adapter.IntervalList;
import com.muecke.swimwatch.model.Swimmer;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity  {

    final List<Swimmer> swimmers = new ArrayList<Swimmer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        viewSwimmers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Swimmer swimmer = (Swimmer) parent.getItemAtPosition(position);
                swimmer.changeSwimmingStatus();
            }
        });

        TextView runningTime = (TextView) findViewById(R.id.textViewTime);
    }


}
