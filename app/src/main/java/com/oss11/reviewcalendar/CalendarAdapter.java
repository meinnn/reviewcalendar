package com.oss11.reviewcalendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends BaseAdapter {
    private ArrayList<com.oss11.reviewcalendar.DayInfo> arrayListDayInfo;
    public Date selectedDate;
    public Date dateToday;

    public CalendarAdapter(ArrayList<com.oss11.reviewcalendar.DayInfo> arrayLIstDayInfo, Date date) {
        this.arrayListDayInfo = arrayLIstDayInfo;
        this.selectedDate = date;
        this.dateToday = Calendar.getInstance().getTime();
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

        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day, parent, false);
        }

        if(day != null){
            TextView tvDay = convertView.findViewById(R.id.day_cell_tv_day);
            tvDay.setText(day.getDay());

            ImageView ivSelected = convertView.findViewById(R.id.iv_selected);
            if(day.isSameDay(dateToday)){
                ivSelected.setVisibility(View.VISIBLE);
            }else{
                ivSelected.setVisibility(View.INVISIBLE);
            }

            if(day.isInMonth()){
                if((position%7 + 1) == Calendar.SUNDAY){
                    tvDay.setTextColor(Color.RED);
                }/*else if((position%7 + 1) == Calendar.SATURDAY){
                    tvDay.setTextColor(Color.BLUE);
                }*/else{
                    tvDay.setTextColor(Color.BLACK);
                }
            }else{
                tvDay.setTextColor(Color.GRAY);
            }
        }
        convertView.setTag(day);

        return convertView;
    }

}
