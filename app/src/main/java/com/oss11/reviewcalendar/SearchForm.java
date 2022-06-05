package com.oss11.reviewcalendar;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class SearchForm extends AppCompatActivity {
    String[] strcut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naversearch);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = getIntent();
                    String bundle = intent.getStringExtra("selectedDate");
                    String keyword = intent.getStringExtra("keyword");
                    String str = getNaverSearch(keyword);
                    strcut = str.split("\\n");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GridView gridImage = (GridView) findViewById(R.id.gridI);
                            gridImage.setAdapter(new ImageAdapter(SearchForm.this,strcut));

                            gridImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent2 = new Intent(SearchForm.this, write_review.class);
                                    intent2.putExtra("button1",true);
                                    intent2.putExtra("imageURL",strcut[position]);
                                    intent2.putExtra("selectedDate",bundle);
                                    startActivity(intent2);
                                }
                            });
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });thread.start();
    }

    public String getNaverSearch(String keyword) {

        String clientID = "OtOmof1B3DJgOQzWCYlO";
        String clientSecret = "4JVlxSF7a1";
        StringBuffer stringbuffer = new StringBuffer();
        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/image.xml?query=" + text + "&display=100" + "&start=1";
            URL url = new URL(apiURL);
            HttpURLConnection URLc = (HttpURLConnection) url.openConnection();
            URLc.setRequestProperty("X-Naver-Client-Id", clientID);
            URLc.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            URLc.setRequestMethod("GET");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            String tag;
            xpp.setInput(new InputStreamReader(URLc.getInputStream(), "UTF-8"));
            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")) ;
                        else if (tag.equals("thumbnail")) {
                            xpp.next();
                            stringbuffer.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            stringbuffer.append("\n");
                        }
                        break;
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e) {
            return e.toString();
        }
        return stringbuffer.toString();
    }
}