package com.example.prime.storefavoriteplaces;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places;
    static ArrayAdapter<String> arrayAdapter;
    static ArrayList<LatLng> locations;


    public void openMap(View view){
        Intent i = new Intent(getApplicationContext(),MapsActivity2.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);

        places = new ArrayList<String>();
        places.add("Add a new location...");

        locations = new ArrayList<LatLng>();
        locations.add(new LatLng(0,0));

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Context context = getApplicationContext();
                //CharSequence text = places.get(position);
                //int duration = Toast.LENGTH_SHORT;

                Intent i = new Intent(getApplicationContext(),MapsActivity2.class);
                i.putExtra("locationInfo",position);
                startActivity(i);

                //Toast toast = Toast.makeText(context, text, duration);
                //toast.show();
            }
        });

    }
}
