package com.lemonlab.wordbook;

import java.util.Comparator;

/**
 * Created by lk on 2015. 9. 11..
 */
public class PreorderSort implements Comparator<Word> {

        @Override
        public int compare(Word word, Word t1) {
            return t1.getEng().compareTo(word.getEng());
        }
}
