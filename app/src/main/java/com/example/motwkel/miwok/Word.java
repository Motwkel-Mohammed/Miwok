package com.example.motwkel.miwok;

import android.view.View;

/**
 * Created by MOTWKEL on 6/19/2018.
 */

public class Word {
    //custom word class
    //global variables
    private String mMiwok_Transilation;
    private String mEnglish_Transilation;
    private int mImageResourceIde=IMAGE;

    private static final int IMAGE= -1;

    private int mAudioResouceId;
    //constructor
    public Word(String english_transilation,String miwok_transilation,int imageResourceIde,int AudioResouceId){
        mEnglish_Transilation = english_transilation;
        mMiwok_Transilation = miwok_transilation;
        mImageResourceIde = imageResourceIde;
        mAudioResouceId = AudioResouceId;
    }
    public Word(String english_transilation,String miwok_transilation,int AudioResouceId){
        mEnglish_Transilation = english_transilation;
        mMiwok_Transilation = miwok_transilation;
        mAudioResouceId =  AudioResouceId;
    }

    //get method which get MiwokTransilation and EnglishTransilation

    public String getmEnglish_transilation(){
        return mEnglish_Transilation;
    }
    public String getmMiwok_Transilation(){
        return mMiwok_Transilation;
    }
    public int getmImageResourceIde(){return mImageResourceIde;}
    public int getmAudioResouceId(){return mAudioResouceId;}
    public boolean hasImage(){
        return mImageResourceIde != IMAGE;
    }

}
