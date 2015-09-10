package com.lemonlab.wordbook;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by lk on 2015. 9. 11..
 */
public class FileStorge {
    private static FileStorge ourInstance;
    private ArrayList<Word> list;
    private static Context context;
    private static File file;

    public static FileStorge getInstance(Context context) {
        if(ourInstance == null)
            ourInstance = new FileStorge(context);
        return ourInstance;
    }

    private FileStorge(Context context) {
        this.list = list;
        this.context = context;
        file = new File(context.getFilesDir(), "save.txt");
    }

    public static void saveEntry(Word word){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(context.getFilesDir()+"/save.txt", true)));
            bw.write(word.getEng() + "/" + word.getKor() + "/" + word.getCount() + "\n");
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Word> loadEntry(){
        if(file.exists()) {
            System.out.println("파일이 존재합니다 .");
            ArrayList list = new ArrayList();
            int fileSize = (int) file.length();
            byte[] b = new byte[fileSize];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
                BufferedInputStream bis = new BufferedInputStream(fileInputStream);
                String data = "";
                String temp = "";
                while((temp = br.readLine()) != null){
                    String[] wordStr = temp.split("/");
                    Word word = new Word(wordStr[0], wordStr[1]);
                    list.add(word);
                }
                br.close();
                return list;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
