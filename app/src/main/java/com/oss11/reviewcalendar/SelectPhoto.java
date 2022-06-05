package com.oss11.reviewcalendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;

public class SelectPhoto extends DialogFragment {
    private static final int REQUEST_CODE = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.select_photo, null, false);
        builder.setView(dialog);

        Button button = dialog.findViewById(R.id.button);
        Button button2 = dialog.findViewById(R.id.button2);
        Button button3 = dialog.findViewById(R.id.button3);
        Button button4 = dialog.findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView searchText = dialog.findViewById(R.id.searchText);
                String keyword = searchText.getText().toString();

                Intent intentS = new Intent(getActivity(), SearchForm.class);
                intentS.putExtra("selectedDate",bundle.getString("selectedDate"));
                intentS.putExtra("keyword", keyword);
                startActivity(intentS);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), write_review.class);
                intent2.putExtra("button2",true);
                intent2.putExtra("selectedDate",bundle.getString("selectedDate"));
                startActivity(intent2);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentT = new Intent(getActivity(), write_review.class);
                intentT.putExtra("selectedDate",bundle.getString("selectedDate"));
                startActivity(intentT);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPhoto.this.getDialog().dismiss();
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}