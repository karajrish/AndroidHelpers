package com.example.prime.eggtimerdemo;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar;
    TextView timerTextView;
    Button controlButton;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;

    public void updateTimer(int secondsleft)
    {
        int minutes = (int) secondsleft / 60;
        int seconds = secondsleft - minutes * 60;
        String secondString = Integer.toString(seconds);
        String minuteString = Integer.toString(minutes);
        if(minutes<10)
        {
            minuteString = "0" + Integer.toString(minutes);
        }
        if(seconds<10)
        {
            secondString = "0" + Integer.toString(seconds);
        }
        timerTextView.setText(minuteString + ":" + secondString);
    }

    public void resetTimer()
    {
        timerTextView.setText("00:30");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        controlButton.setText("Go!");
        timerSeekbar.setEnabled(true);
        counterIsActive = false;
    }

    public void controlTimer(View view)
    {
        if(counterIsActive == false) {
            counterIsActive = true;
            controlButton.setText("Stop!");
            timerSeekbar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {


                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {

                    resetTimer();
                    Log.i("Timer finished", "done");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mplayer.start();
                }
            }.start();
        }
        else if (counterIsActive == true)
        {
            resetTimer();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = (SeekBar) findViewById(R.id.TimerSeekBar);
        timerTextView = (TextView) findViewById(R.id.TimerTextView);

        controlButton = (Button) findViewById(R.id.ControllerButton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
