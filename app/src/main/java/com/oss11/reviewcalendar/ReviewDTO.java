package com.oss11.reviewcalendar;


public class ReviewDTO{

    public String title;
    public String date;
    public String place;
    public String with;
    public String review;

    public ReviewDTO(){}

    public ReviewDTO(String title,String date,String place,String with,String review){
        this.title=title;
        this.date=date;
        this.place=place;
        this.with=with;
        this.review=review;
    }
}