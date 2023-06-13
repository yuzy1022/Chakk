package com.example.chack;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class addBookCurrentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View v;

    EditText currentDate;
    Calendar currentCalendar = Calendar.getInstance();
    DatePickerDialog currentDialog;
    Calendar maxDate = Calendar.getInstance(); //최대 날짜 지정
    TextView currendtday;

    long today, dday, resultdday;
    int resultvalue=0;

    public addBookCurrentFragment() {
        // Required empty public constructor
    }

    public static addBookCurrentFragment newInstance(String param1, String param2) {
        addBookCurrentFragment fragment = new addBookCurrentFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_current_addbook, container, false);
        currentDate = v.findViewById(R.id.currentDate);
        currendtday = v.findViewById(R.id.currendtday);
        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                currentCalendar.set(Calendar.YEAR, year);
                                currentCalendar.set(Calendar.MONTH, month);
                                currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel();
                                today=maxDate.getTimeInMillis();
                                dday=currentCalendar.getTimeInMillis();
                                resultdday = (today - dday)/(24*60*60*1000);
                                resultvalue = (int) resultdday;
                                updateDday(resultvalue);
                            }
                        },
                        currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH),
                        currentCalendar.get(Calendar.DAY_OF_MONTH)
                );
                int year = maxDate.get(Calendar.YEAR);
                int month = maxDate.get(Calendar.MONTH);
                int day = maxDate.get(Calendar.DAY_OF_MONTH);

                maxDate.set(year,month,day);
                currentDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis()); //다 읽은 날짜 선택시 오늘을 넘길 수 없도록 설정
                currentDialog.show();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    @SuppressLint("DefaultLocale")
    private void updateDday(int resultvalue){
        if(resultvalue>=0){
            currendtday.setText(String.format("%d일 째 읽고 있어요",resultvalue));
            Log.d("태그", "day : " + resultvalue);
        }
        else
            currentDate.setText("다시 입력하세요");
    }

    private void updateLabel() {
        String myFormat = "yyyy년 MM월 dd일";    // 출력형식   2023/06/01
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText currentDate = (EditText) getView().findViewById(R.id.currentDate);
        currentDate.setText(sdf.format(currentCalendar.getTime()));
    }
}