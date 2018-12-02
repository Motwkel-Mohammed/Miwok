package com.example.motwkel.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Familly extends AppCompatActivity {

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
        words.add (new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("older sister","chalitti",R.drawable.family_older_sister,R.raw.family_younger_brother));
        words.add(new Word("younger brother","teṭe",R.drawable.family_younger_brother,R.raw.family_older_sister));
        words.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter Adapter = new WordAdapter(this, words,R.color.family);

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
                    mediaPlayer=MediaPlayer.create(Familly.this,word.getmAudioResouceId());
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