package com.oss11.reviewcalendar;


/*public class ReviewDTO{//파이어베이스 저장할 때 테이블 형식으로 만들기 위한 디투어

    public String title;
    public String date;
    public String place;
    public String with;
    public String review;
    public float rating;

    public ReviewDTO(){}

    public ReviewDTO(String title,String date,String place,String with,String review,float rating){
        this.title=title;
        this.date=date;
        this.place=place;
        this.with=with;
        this.review=review;
        this.rating=rating;
    }
}*/
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