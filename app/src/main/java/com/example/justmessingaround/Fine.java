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

public class Fine extends AppCompatActivity {
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
        setContentView(R.layout.activity_fine);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        mSeekBarTime = findViewById(R.id.seekBarTime);
        mSeekBarVol = findViewById(R.id.seekBarVol);
        imageView = findViewById(R.id.imageView);
        songTitle = findViewById(R.id.sontTitle);

        ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0, R.raw.red_heart);
        songs.add( 1, R.raw.ahoy);
        songs.add(2, R.raw.hollowness);
        songs.add(3,R.raw.lilac);

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
            songTitle.setText("Red Hear");
            imageView.setImageResource(R.drawable.red_heart);
        }
        if(currentIndex == 1){
            songTitle.setText("Ahoy! (cover)");
            imageView.setImageResource(R.drawable.ahoy);
        }
        if(currentIndex == 2){
            songTitle.setText("Hollowness");
            imageView.setImageResource(R.drawable.hollowness);
        }
        if(currentIndex == 3){
            songTitle.setText("Lilac");
            imageView.setImageResource(R.drawable.lilac);
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