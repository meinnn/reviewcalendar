package com.oss11.reviewcalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends BaseAdapter {
    private ArrayList<com.oss11.reviewcalendar.DayInfo> arrayListDayInfo;
    public Date selectedDate;
    public Date dateToday;
    private Context context;
    private FirebaseStorage storage;


    public CalendarAdapter(ArrayList<com.oss11.reviewcalendar.DayInfo> arrayLIstDayInfo, Date date, Context context) {
        this.arrayListDayInfo = arrayLIstDayInfo;
        this.selectedDate = date;
        this.dateToday = Calendar.getInstance().getTime();
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayListDayInfo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayListDayInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        com.oss11.reviewcalendar.DayInfo day = arrayListDayInfo.get(position);
        String date = arrayListDayInfo.get(position).getDate().toString().substring(0, 10);

        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day, parent, false);
        }

        if(day != null) {
            TextView tvDay = convertView.findViewById(R.id.day_cell_tv_day);
            tvDay.setText(day.getDay());

            ImageView ivSelected = convertView.findViewById(R.id.iv_selected);
            ImageView savedimage = convertView.findViewById(R.id.saved_image);
            storage = FirebaseStorage.getInstance();
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            mRootRef.child(date).child("URL").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue(String.class) != null) {
                        String text = dataSnapshot.getValue(String.class);
                        Glide.with(context).load(text).into(savedimage);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            storage.getReference().child(date).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(uri!=null) {
                        Glide.with(context).load(uri).into(savedimage);
                    }
                }
            });
            if (day.isSameDay(dateToday)) {
                ivSelected.setVisibility(View.VISIBLE);
            } else {
                ivSelected.setVisibility(View.INVISIBLE);
            }

            if(savedimage.getDrawable() == null) {
                if (day.isInMonth()) {
                    if ((position % 7 + 1) == Calendar.SUNDAY) {
                        tvDay.setTextColor(Color.RED);
                    }/*else if((position%7 + 1) == Calendar.SATURDAY){
                    tvDay.setTextColor(Color.BLUE);
                }*/ else {
                        tvDay.setTextColor(Color.BLACK);
                    }
                } else {
                    tvDay.setTextColor(Color.GRAY);
                }
            }
        }
        convertView.setTag(day);

        return convertView;
    }

}
