package com.example.chack;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class addBookFutureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View v;

    EditText willDate;
    Calendar willCalendar = Calendar.getInstance();
    DatePickerDialog willDialog;
    Calendar minDate = Calendar.getInstance(); //최소 날짜 지정

    public addBookFutureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addBookFutureFragment newInstance(String param1, String param2) {
        addBookFutureFragment fragment = new addBookFutureFragment();
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
        v = inflater.inflate(R.layout.fragment_future_addbook, container, false);
        willDate = v.findViewById(R.id.willDate);
        willDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                willDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                willCalendar.set(Calendar.YEAR, year);
                                willCalendar.set(Calendar.MONTH, month);
                                willCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel();
                            }
                        },
                        willCalendar.get(Calendar.YEAR),
                        willCalendar.get(Calendar.MONTH),
                        willCalendar.get(Calendar.DAY_OF_MONTH)
                );
                int year = minDate.get(Calendar.YEAR);
                int month = minDate.get(Calendar.MONTH);
                int day = minDate.get(Calendar.DAY_OF_MONTH);

                minDate.set(year,month,day);
                willDialog.getDatePicker().setMinDate(minDate.getTimeInMillis()); //다 읽은 날짜 선택시 오늘을 넘길 수 없도록 설정
                willDialog.show();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
    private void updateLabel() {
        String myFormat = "yyyy년 MM월 dd일";    // 출력형식   2023/06/01
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText willDate = (EditText) getView().findViewById(R.id.willDate);
        willDate.setText(sdf.format(willCalendar.getTime()));
    }
}
