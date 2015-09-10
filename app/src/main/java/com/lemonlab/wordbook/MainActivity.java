package com.lemonlab.wordbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    ArrayList<Word> arrayList;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (menuItem.isCheckable()) {
                            menuItem.setChecked(true);
                        }
                        Toast.makeText(getApplicationContext(),
                                menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        drawer.closeDrawers();
                        return true;
                    }
                });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (null != ab) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        arrayList = new ArrayList<>();
        Word w1 = new Word("Lemon", "① 레몬② 불량품");
        Word w2 = new Word("Apple", "① 사과② 사과나무");
        arrayList.add(w1);
        arrayList.add(w2);

        ListView listView = (ListView) findViewById(R.id.lv_main_wordlist);
        adapter = new ListAdapter(MainActivity.this, arrayList);
        listView.setAdapter(adapter);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fabBt_main_addword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog().show();
            }
        });


    }

    public AlertDialog Dialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final View innerView = getLayoutInflater().inflate(R.layout.dialog_addword, null);
        alert.setTitle("단어추가");
        alert.setView(innerView);
        final AppCompatEditText et = (AppCompatEditText) innerView.findViewById(R.id.et_dialog_text);
        alert.setPositiveButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                httpThread(et.getText().toString());
            }
        });
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return alert.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            drawer.openDrawer(GravityCompat.START);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    void httpThread(final String word){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                testHttp(word);
            }
        });
        thread.start();
    }


    void testHttp(String word){
        String url = "http://dic.daum.net/search.do?q="+ word +"&dic=eng";
        String response = requestHttp(url);

        if("".equals(response))
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "단어를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        else {
            StringBuilder sb = new StringBuilder();
            for (char c : response.toCharArray()) {
                if ((c >= '"' && c <= 'z')) {
                    continue;
                } else {
                    sb.append(c);
                }
            }
            System.out.println(sb.toString());
            arrayList.add(new Word(word, sb.toString()));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }


    public String requestHttp(String urlStr){
        try {
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String response = "";
            String tmp = "";
            while( (tmp = reader.readLine())!= null){
                if(tmp.contains("wrap_meaning")) {
                    response += tmp;
                    break;
                }
            }
            is.close();

            return new String(response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
