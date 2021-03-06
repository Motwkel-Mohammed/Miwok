package com.example.motwkel.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Colors extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener monAudioFocusChangeListener=
            new AudioManager.OnAudioFocusChangeListener() {
                @Override

                public void onAudioFocusChange(int focusChange) {
                    if(focusChange==audioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange==audioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        //AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK and AUDIOFOCUS_LOSS mean that we don't have the audio focus!
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if (focusChange==audioManager.AUDIOFOCUS_GAIN){
                        //start after loosing the focus
                        mediaPlayer.start();
                    }
                    else if(focusChange==audioManager.AUDIOFOCUS_LOSS){
                        //release mediaplayer after it is end
                        releaseMediaPlayer();
                    }
                }
            };
    //global variable that release the medea player after finished
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);


        //audioManager object to request audioManager
        audioManager=(AudioManager) getSystemService (AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add (new Word("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("black","kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));


        WordAdapter Adapter = new WordAdapter(this, words,R.color.color);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(Adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Word word=words.get(position);

                //release it before medea player start other song
                releaseMediaPlayer();
                //request audio focus and pass in the change listener and stream type and  the duration
                int result=audioManager.requestAudioFocus(
                        monAudioFocusChangeListener
                        , AudioManager.STREAM_MUSIC
                        ,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    //we have audio focus now !

                    //get the  current position of the audio
                    mediaPlayer=MediaPlayer.create(Colors.this,word.getmAudioResouceId());
                    mediaPlayer.start();

                    //release it after finish medea player
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }}
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    //release the medea player
    private void releaseMediaPlayer(){
        if(mediaPlayer !=null)
            mediaPlayer.release();
        mediaPlayer=null;
        //abandon audio focus when playback is complete
        audioManager.abandonAudioFocus(monAudioFocusChangeListener);
    }
}