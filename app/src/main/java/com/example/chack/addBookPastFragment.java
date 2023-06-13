package com.example.chack;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class addBookPastFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View v;
    EditText startDate, finishDate;
    Calendar startCalendar = Calendar.getInstance(), finishCalendar = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();
    DatePickerDialog startDidalog, finishDialog;
    Date date = new Date(); //오늘 날짜 가져오기
    RatingBar rating;
    TextView ratingNum;

    private String mParam1;
    private String mParam2;

    public addBookPastFragment() {
        // Required empty public constructor
    }
    public static addBookPastFragment newInstance(String param1, String param2) {
        addBookPastFragment fragment = new addBookPastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_past_addbook, container, false);

        startDate = v.findViewById(R.id.startDate);
        finishDate = v.findViewById(R.id.finishDate);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDidalog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                startCalendar.set(Calendar.YEAR, year);
                                startCalendar.set(Calendar.MONTH, month);
                                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel();
                            }
                        },
                        startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)
                );
                startDidalog.show();
            }
        });

        finishCalendar.setTime(date);

        finishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                finishCalendar.set(Calendar.YEAR, year);
                                finishCalendar.set(Calendar.MONTH, month);
                                finishCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel();
                            }
                        },
                        finishCalendar.get(Calendar.YEAR),
                        finishCalendar.get(Calendar.MONTH),
                        finishCalendar.get(Calendar.DAY_OF_MONTH)
                );
                int year = maxDate.get(Calendar.YEAR);
                int month = maxDate.get(Calendar.MONTH);
                int day = maxDate.get(Calendar.DAY_OF_MONTH);

                maxDate.set(year, month, day);
                finishDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis()); //다 읽은 날짜 선택시 오늘을 넘길 수 없도록 설정

                finishDialog.show();
            }
        });

        rating = v.findViewById(R.id.addrating);
        ratingNum = v.findViewById(R.id.currendtday);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingNum.setText(String.valueOf(rating));
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    private void updateLabel() {
        String myFormat = "yyyy년 MM월 dd일";    // 출력형식   2023/06/01
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText startDate = (EditText) getView().findViewById(R.id.startDate);
        startDate.setText(sdf.format(startCalendar.getTime()));

        EditText finishDate = (EditText) getView().findViewById(R.id.finishDate);
        finishDate.setText(sdf.format(finishCalendar.getTime()));
    }
}