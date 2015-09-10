package com.lemonlab.wordbook;

/**
 * Created by lk on 2015. 9. 10..
 */
public class Word {

    private String wordEng;
    private String wordKor;
    private int count;

    public Word(String Eng, String Kor){
        this.wordEng = Eng;
        this.wordKor = Kor;
        this.count = 0;
    }

    public String getEng(){
        return wordEng;
    }

    public String getKor(){
        return wordKor;
    }

    public void increaseCount(){
        count++;
    }

    public int getCount(){
        return count;
    }

}
