package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Boolean isActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void onClick(View view){

        if (isActive){

            resetTimer();

        }else{

            isActive = true;
            seekBar.setEnabled(false);
            goButton.setText("STOP!");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 +100,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.horn);
                    mediaPlayer.start();
                    resetTimer();

                }
            }.start();
        }

    }

    public void updateTimer(int progress){
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);
        String secondsString = Integer.toString(seconds);

        if (seconds < 10){
            secondsString = "0" + secondsString;
        }

        textView.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    public void resetTimer (){
        textView.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        isActive = false;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        goButton = findViewById(R.id.goButton);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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