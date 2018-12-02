package com.example.motwkel.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MOTWKEL on 6/23/2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceIde;

    public WordAdapter(Activity context, ArrayList<Word> Words,int ColorResourceIde){
        super(context, 0, Words);
        mColorResourceIde=ColorResourceIde;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView miwok = (TextView) listItemView.findViewById(R.id.miwok_transilation);
        miwok.setText(currentWord.getmMiwok_Transilation());

        TextView english = (TextView) listItemView.findViewById(R.id.english_transilation);
        english.setText(currentWord.getmEnglish_transilation());

        ImageView icon = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()){
        icon.setImageResource(currentWord.getmImageResourceIde());
            icon.setVisibility(View.VISIBLE);
        }
        else{
            icon.setVisibility(View.GONE);
        }

        View text =listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),mColorResourceIde);
        text.setBackgroundColor(color);

        return listItemView;
    }
}
