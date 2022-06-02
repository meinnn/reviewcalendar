package com.oss11.reviewcalendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;


public class SelectPhoto extends DialogFragment {

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), write_review.class);
                intent.putExtra("selectedDate",bundle.getString("selectedDate"));//MyGridCalendar의 selectedDate 값을 넘겨받은 후에 다시 write_review로 값 전달
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
