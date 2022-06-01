package com.oss11.reviewcalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import androidx.appcompat.app.AppCompatActivity;


public class SearchForm extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.naversearch);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = getIntent();
                    String keyword = intent.getStringExtra("keyword");
                    String str = getNaverSearch(keyword);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView searchR = (TextView) findViewById(R.id.searchR);
                            searchR.setText(str);
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
            String apiURL = "https://openapi.naver.com/v1/search/image.xml?query=" + text + "&display=10" + "&start=1";
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
                        else if (tag.equals("title")) {
                            xpp.next();
                            stringbuffer.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            stringbuffer.append("\n");
                        }
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