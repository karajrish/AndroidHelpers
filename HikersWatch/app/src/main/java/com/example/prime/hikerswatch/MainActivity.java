package com.example.prime.hikerswatch;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;

    TextView latitudeTextView;
    TextView longitudeTextView;
    TextView altTextView;
    TextView bearingTextView;
    TextView speedTextView;
    TextView accuracyTextView;
    TextView addressTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitudeTextView = (TextView) findViewById(R.id.LatitudeTextView);
        longitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
        altTextView = (TextView) findViewById(R.id.altitudeTextView);
        bearingTextView = (TextView) findViewById(R.id.bearingTextView);
        speedTextView  = (TextView) findViewById(R.id.speedTextView);
        accuracyTextView = (TextView) findViewById(R.id.accuracyTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(),false);

        Location location = locationManager.getLastKnownLocation(provider);
        onLocationChanged(location);

        locationManager.requestLocationUpdates(provider,400,1,this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider,400,1,this);
    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        Double alt = location.getAltitude();
        Float bearing = location.getBearing();
        Float speed = location.getSpeed();
        Float accuracy = location.getAccuracy();


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String addressHolder="";
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lat,lng,1);

            if(listAddresses != null && listAddresses.size()>0){
                Log.i("Place info",listAddresses.get(0).toString());
                for(int i = 0; i <= listAddresses.get(0).getMaxAddressLineIndex();i++){
                    addressHolder += listAddresses.get(0).getAddressLine(i)+"\n";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        latitudeTextView.setText("Latitude : " + lat.toString());
        longitudeTextView.setText("Longitude : " + lng.toString());
        altTextView.setText("Altitude : " + alt.toString());
        bearingTextView.setText("Bearing : " + bearing.toString());
        speedTextView.setText("Speed : " + speed.toString());
        accuracyTextView.setText("Accuracy : " + accuracy.toString());
        addressTextView.setText("Address :\n" + addressHolder );
        Log.i("Latitude:",lat.toString());
        Log.i("Longitude",lng.toString());
        Log.i("Altitude:",alt.toString());
        Log.i("Bearing",bearing.toString());
        Log.i("Speed:",speed.toString());
        Log.i("Accuracy",accuracy.toString());


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
