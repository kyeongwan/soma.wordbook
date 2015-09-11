package com.lemonlab.wordbook;

import java.util.Comparator;

/**
 * Created by lk on 2015. 9. 11..
 */
public class IdSort implements Comparator<Word> {

    @Override
    public int compare(Word word, Word t1) {
        if(word.getId() > t1.getId())
            return 1;
        else
            return -1;
    }
}
