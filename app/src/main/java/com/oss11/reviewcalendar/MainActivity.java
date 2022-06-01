package com.oss11.reviewcalendar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    MyGridViewCalendar myGridViewCalendar;
    Date selectedDate;
    TextView tvSelectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSelectedDate = findViewById(R.id.tv_selected_date);

        myGridViewCalendar = (MyGridViewCalendar) getSupportFragmentManager().findFragmentById(R.id.gridViewCalendar);
        myGridViewCalendar.setSelectedDate(new Date());

    }

}