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

public class Phrases extends AppCompatActivity {

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
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        WordAdapter Adapter = new WordAdapter(this, words, R.color.phrases);

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
                    mediaPlayer=MediaPlayer.create(Phrases.this,word.getmAudioResouceId());
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