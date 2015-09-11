package com.lemonlab.wordbook;

import java.util.Comparator;

/**
 * Created by lk on 2015. 9. 11..
 */
public class CountSort implements Comparator<Word>{
    @Override
    public int compare(Word word, Word t1) {
        if(word.getCount() > t1.getCount())
            return -1;
        else if(word.getCount() == t1.getCount())
            return 0;
        else
            return 1;
    }
}
