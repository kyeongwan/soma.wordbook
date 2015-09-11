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
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Created by lk on 2015. 9. 11..
 */
public class FileStorge {
    private static FileStorge ourInstance;
    private ArrayList<Word> list;
    private static Context context;
    private static File file;
    private static File file2;

    public static FileStorge getInstance(Context context) {
        if(ourInstance == null)
            ourInstance = new FileStorge(context);
        return ourInstance;
    }

    private FileStorge(Context context) {
        this.list = list;
        this.context = context;
        file = new File(context.getFilesDir(), "save.txt");
        file2 = new File(context.getFilesDir(), "saveCount.txt");
    }

    public static void saveEntry(Word word){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(context.getFilesDir()+"/save.txt", true)));
            BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(context.getFilesDir()+"/saveCount.txt", true)));
            bw.write(word.getEng() + "/" + word.getKor() + "/" + "\n");
            bw2.write(String.format("%07d", word.getCount()));
            bw.close();
            bw2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAll(ArrayList<Word> list){
        deleteAll();
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(context.getFilesDir()+"/save.txt", true)));
            BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(context.getFilesDir()+"/saveCount.txt", true)));
            for(int i=0; i< list.size(); i++) {
                bw.write(list.get(i).getEng() + "/" + list.get(i).getKor() + "/" + "\n");
                bw2.write("0000001");
            }
            bw.close();
            bw2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void saveCount(Word word){
        int id = word.getId();
        try {
            RandomAccessFile rfile = new RandomAccessFile(context.getFilesDir()+"/saveCount.txt", "rw");
            rfile.seek(id * 7 - 7);
            rfile.writeBytes(String.format("%07d", word.getCount()));
            rfile.close();
            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
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

                FileInputStream fileInputStream2 = new FileInputStream(file2);
                BufferedInputStream br2 = new BufferedInputStream(fileInputStream2);
                //InputStreamReader br2 = new InputStreamReader(fileInputStream2));
                String data = "";
                String temp = "";
                char[] bufferCount = new char[7];
                int id = 1;
                while((temp = br.readLine()) != null){
                    String[] wordStr = temp.split("/");

                    for(int i=0; i<7; i++)
                        bufferCount[i] = (char)br2.read();

                    Word word = new Word(id, Integer.parseInt(String.valueOf(bufferCount)), wordStr[0], wordStr[1]);
                    list.add(word);
                    System.out.println(Integer.parseInt(String.valueOf(bufferCount)));
                    id++;
                }
                br.close();
                br2.close();
                return list;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    public static void deleteAll(){
        if(file.exists()){
            file.delete();
            file =  new File(context.getFilesDir(), "save.txt");
        }
        if(file2.exists()) {
            file2.delete();
            file2 = new File(context.getFilesDir(), "saveCount.txt");
        }
    }

    public static ArrayList<Word> loadWord(ArrayList<Word> list){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("wordEn")));
            String data = "";
            while(true) {
                data = reader.readLine();
                if(data == null)
                    break;
                Word word = new Word(list.size() + 1, 1, data, "null");
                //saveEntry(word);
                list.add(word);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
