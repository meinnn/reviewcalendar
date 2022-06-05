package com.oss11.reviewcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.Date;

public class write_review extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    private FirebaseStorage storage;

    EditText EditText_title;
    EditText EditText_date;
    EditText EditText_place;
    EditText EditText_with;
    EditText EditText_review;
    Button saveButton;
    String date, sub_date;
    RatingBar ratingbar;
    float rate;
    Button deleteButton;
    Button Backspace;
    String URL;
    boolean Button1Flag;
    boolean Button2Flag;


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        sub_date = intent.getExtras().getString("selectedDate");
        URL = intent.getStringExtra("imageURL");
        Button1Flag = intent.getBooleanExtra("button1", false);
        Button2Flag = intent.getBooleanExtra("button2", false);
        setContentView(R.layout.review);
        EditText_title = (EditText) findViewById(R.id.editText);
        EditText_date = (EditText) findViewById(R.id.editText2);
        EditText_place = (EditText) findViewById(R.id.editText3);
        EditText_with = (EditText) findViewById(R.id.editText4);
        EditText_review = (EditText) findViewById(R.id.editText5);
        saveButton = (Button) findViewById(R.id.button5);
        date = sub_date.substring(0, 10);
        imageView = findViewById(R.id.imageView3);
        storage = FirebaseStorage.getInstance();
        storage.getReference().child(date).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(write_review.this).load(uri).into(imageView);
            }
        });

        if (Button1Flag == true) {
            Glide.with(this).load(URL).into(imageView);
            Uri file = Uri.parse(URL);
            StorageReference storageRef = storage.getReference();
            UploadTask uploadTask = storageRef.child(date).putFile(file);
        }

        if (Button2Flag == true) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Button2Flag == true) {
            if (requestCode == REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Uri file = data.getData();
                    StorageReference storageRef = storage.getReference();
                    UploadTask uploadTask = storageRef.child(date).putFile(file);
                    try {
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();
                        imageView.setImageBitmap(img);
                    } catch (Exception e) {
                    }
                } else if (resultCode == RESULT_CANCELED) {
                }
            }
        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();// date 값이 사용자가 클릭한 날짜값이므로 각각의 날짜를 제목으로 하는 테이블 생성,
        // 그 후 테이블 안의 키값인 title, date, place, with, review에 입력한 값 저장


        EditText_date.setText(date);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {//별점 클릭시 해당 값 파이어베이스에 저장
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                mRootRef.child(date).child("rating").setValue(rating);
                rate=rating;
            }
        });

        mRootRef.child(date).child("rating").addValueEventListener(new ValueEventListener() {//파이어베이스에 저장된 별점 값
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float rating=dataSnapshot.getValue(float.class);
                ratingbar.setRating(rating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRootRef.child(date).child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_title.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(date).child("place").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_place.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(date).child("with").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_with.setText(text);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(date).child("review").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_review.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {// 수정 버튼을 누를 시 파이어베이스의 각 키 값에 데이터가 저장됨
            @Override
            public void onClick(View v) {
                ReviewDTO reviewDTO=new ReviewDTO(EditText_title.getText().toString(), date
                        ,EditText_place.getText().toString(), EditText_with.getText().toString(),EditText_review.getText().toString(),rate);


                mRootRef.child(date).setValue(reviewDTO);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {//삭제 버튼
            @Override
            public void onClick(View view) {
                EditText_review.setText("");
                EditText_with.setText("");
                EditText_place.setText("");
                EditText_title.setText("");
            }
        });
    }
}*/
    @Override
    protected void onStart() {
        super.onStart();


        mRootRef.child(date).child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_title.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(date).child("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_date.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(date).child("place").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_place.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(date).child("with").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_with.setText(text);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(date).child("review").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                EditText_review.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewDTO reviewDTO = new ReviewDTO(EditText_title.getText().toString(), date
                        , EditText_place.getText().toString(), EditText_with.getText().toString(), EditText_review.getText().toString());


                mRootRef.child(date).setValue(reviewDTO);
            }
        });
    }
}