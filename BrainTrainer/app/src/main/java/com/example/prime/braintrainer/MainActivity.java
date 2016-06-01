package com.example.prime.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button goButton, PlayAgainButton;
    CountDownTimer timer;
    Boolean counterIsActive = false;
    TextView timerTextView, answerTextView,expTextView, scoreTextView;
    Boolean ifRunning = false;

    void resetGame(View view)
    {
        resetTimer();
        goButton.setVisibility(View.INVISIBLE);
    }
    public void resetTimer()
    {
        //timerTextView.setText("30s");
        timer.start();
//        timerSeekbar.setProgress(30);
//        countDownTimer.cancel();
//        controlButton.setText("Go!");
//        timerSeekbar.setEnabled(true);
        counterIsActive = false;
    }
    public void nextView(View view)
    {
        goButton.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        answerTextView.setVisibility(View.VISIBLE);
        expTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);

        timer = new CountDownTimer(30000+100,1000) {
            @Override
            public void onTick(long millisecondsUntilDone) {

                ifRunning = true;
                String seconds = String.valueOf(millisecondsUntilDone / 1000);
                timerTextView.setText(seconds + "s");

            }

            @Override
            public void onFinish() {

                ifRunning = false;
                PlayAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                Log.i("Done!","Countdown timer finished");
            }
        }.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button) findViewById(R.id.goButton);
        PlayAgainButton = (Button) findViewById(R.id.PlayAgainButton);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        expTextView = (TextView) findViewById(R.id.expTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);


        goButton.setVisibility(View.VISIBLE);
        PlayAgainButton.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.INVISIBLE);
        answerTextView.setVisibility(View.INVISIBLE);
        expTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);

    }
}
