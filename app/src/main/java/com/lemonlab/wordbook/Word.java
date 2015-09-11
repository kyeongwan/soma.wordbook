package com.lemonlab.wordbook;

/**
 * Created by lk on 2015. 9. 10..
 */
public class Word {

    private int id;
    private int count;
    private String wordEng;
    private String wordKor;


    public Word(int id, int count, String Eng, String Kor){
        this.count = count;
        this.id = id;
        this.wordEng = Eng.toLowerCase();
        this.wordKor = Kor;
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

    public int getId(){
        return id;
    }

}
