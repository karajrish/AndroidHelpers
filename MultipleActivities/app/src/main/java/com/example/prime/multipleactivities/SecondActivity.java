package com.example.prime.multipleactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView msg;
    public void changeActivity(View view){

        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        msg = (TextView) findViewById(R.id.messageTextVIew);
        Intent i = getIntent();
        msg.setText(i.getStringExtra("name"));
        //Log.i("Data recieved : ",i.getStringExtra("name"));
    }
}
