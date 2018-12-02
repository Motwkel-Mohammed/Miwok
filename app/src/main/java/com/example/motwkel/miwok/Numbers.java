package com.example.motwkel.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.media.RemoteController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Numbers extends AppCompatActivity {

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
        words.add (new Word("one","lutti",        R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","otiiko",        R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tolookosu",   R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","oyyisa",       R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","massokka",     R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","temmokka",      R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","kenekaku",    R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","kawinta",     R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","wo’e",         R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","na’aacha",      R.drawable.number_ten,R.raw.number_ten));


        WordAdapter Adapter = new WordAdapter(this, words,R.color.number);

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
                mediaPlayer=MediaPlayer.create(Numbers.this,word.getmAudioResouceId());
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