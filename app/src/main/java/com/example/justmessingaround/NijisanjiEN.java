package com.example.justmessingaround;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class NijisanjiEN extends AppCompatActivity {
    ImageView play, prev, next, imageView;
    TextView songTitle;
    SeekBar mSeekBarTime, mSeekBarVol;
    static MediaPlayer mMediaPlayer;
    private Runnable runnable;
    private AudioManager mAudioManager;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nijisanji_en);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        mSeekBarTime = findViewById(R.id.seekBarTime);
        mSeekBarVol = findViewById(R.id.seekBarVol);
        imageView = findViewById(R.id.imageView);
        songTitle = findViewById(R.id.sontTitle);

        ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0, R.raw.rick1);
        songs.add( 1, R.raw.rick2);
        songs.add(2, R.raw.rick3);
        songs.add(3,R.raw.rick4);

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));




        int maxV = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int curV = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSeekBarVol.setMax(maxV);
        mSeekBarVol.setProgress(curV);

        mSeekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());

                if(mMediaPlayer!=null && mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                    play.setImageResource(R.drawable._11871_pause_icon);
                }
                else {
                    mMediaPlayer.start();
                    play.setImageResource(R.drawable._11876_play_icon);
                }
                SongName();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMediaPlayer!=null){
                    play.setImageResource(R.drawable._11876_play_icon);
                }
                if(currentIndex < songs.size() -1){
                    currentIndex++;
                }
                else{
                    currentIndex = 0;
                }

                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }

                mMediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
                mMediaPlayer.start();
                SongName();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMediaPlayer!=null){
                    play.setImageResource(R.drawable._11876_play_icon);
                }

                if(currentIndex >0){
                    currentIndex--;
                }
                else{
                    currentIndex = songs.size() -1;
                }
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }

                mMediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
                mMediaPlayer.start();
                SongName();
            }
        });

    }
    private void SongName(){
        if(currentIndex == 0){
            songTitle.setText("Never Gonna Give You Up");
            imageView.setImageResource(R.drawable.rick);
        }
        if(currentIndex == 1){
            songTitle.setText("Together Forever");
            imageView.setImageResource(R.drawable.rick2);
        }
        if(currentIndex == 2){
            songTitle.setText("Cry for Help");
            imageView.setImageResource(R.drawable.ric3);
        }
        if(currentIndex == 3){
            songTitle.setText("Whenever You Need Somebody");
            imageView.setImageResource(R.drawable.ric4);
        }

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
            }
        });

        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mMediaPlayer.seekTo(i);
                    mSeekBarTime.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mMediaPlayer!=null){
                    try {
                        if (mMediaPlayer.isPlaying()) {
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);

                        }
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @SuppressLint("Handler Leak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage (Message msg){
            mSeekBarTime.setProgress(msg.what);
        }
    };
}