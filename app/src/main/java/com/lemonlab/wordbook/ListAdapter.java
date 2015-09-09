package com.lemonlab.wordbook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lk on 2015. 9. 10..
 */
public class ListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Word> item;

    public ListAdapter(Activity activity, ArrayList item){
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.wordlist_row, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_wordlist_title);
        TextView tvWord = (TextView) view.findViewById(R.id.tv_wordlist_word);

        Word word = item.get(i);
        tvTitle.setText(word.getEng());
        tvWord.setText(word.getKor());

        return view;
    }
}
