package com.example.prime.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView weatherText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityName);
        weatherText = (TextView) findViewById(R.id.WeatherText);
    }

    public void findWeather(View view){

        String city = cityName.getText().toString();

        //to hide the keyboard after button press
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(),0);

        try {
            String encodedCityName = URLEncoder.encode(city,"UTF-8");

            getWeatherData task = new getWeatherData();
            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Invalid City name",Toast.LENGTH_LONG);

        }
        //Log.i("City Name: ",city);


    }

    public class getWeatherData extends AsyncTask<String, Void, String> {

        String result = "";
        @Override
        protected String doInBackground(String... urls) {


            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data!=-1){
                    char current = (char) data;

                    result+=current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Could not find weather",Toast.LENGTH_LONG);
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                String message="";

                JSONObject jsonObject = new JSONObject(result);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                String main="",description="";
                for (int i = 0; i < arr.length(); i++) {

                    JSONObject jsonPart = arr.getJSONObject(i);

                    main = jsonPart.getString("main");
                    description = jsonPart.getString("description");

                    if(main!="" && description!=""){
                        message+=main + ":" + description + "\r\n";
                    }

                }
                if(message!=""){
                    weatherText.setText(message);
                }else{

                    Toast.makeText(getApplicationContext(),"Could not find weather",Toast.LENGTH_LONG);

                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"JSON Error",Toast.LENGTH_LONG);
                //Log.i("Exception:","JSON EXCEPTION");
            }

        }
    }
}
