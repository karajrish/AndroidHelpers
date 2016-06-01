package com.example.prime.showhideui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button toggleButton;
    public void toggleVisibility(View view){
        if(visible)
        {
            imageView.setVisibility(View.INVISIBLE);
            visible = false;
            toggleButton.setText("Show");
        }
        else if(visible==false) {

            imageView.setVisibility(View.VISIBLE);
            visible = true;
            toggleButton.setText("Hide");
        }
    }
    ImageView imageView;
    boolean visible = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        toggleButton = (Button) findViewById(R.id.controlButton);

    }
}
