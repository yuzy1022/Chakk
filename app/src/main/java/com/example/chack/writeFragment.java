package com.example.chack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link writeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class writeFragment extends Fragment {
    MainActivity activity;
    View v;
    Spinner spn;
    ImageButton backBtn;
    TextView tagText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public writeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WriteFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static writeFragment newInstance(String param1, String param2) {
        writeFragment fragment = new writeFragment();
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
        v = inflater.inflate(R.layout.fragment_write, container, false);
        spn = v.findViewById(R.id.spinnerSub);
        backBtn = v.findViewById(R.id.back_button);
        tagText = v.findViewById(R.id.tagText);

        String[] list = {"주제선택","독서모임", "도서추천","문장공유"};

        ArrayAdapter spn_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.writesubject, android.R.layout.simple_spinner_dropdown_item);

        spn_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(spn_adapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagText.setText(" #"+spn.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ;
            }
        });

        backBtn.setOnClickListener(v->{activity.onFragmentChange(2);});


        // Inflate the layout for this fragment
        return v;
    }
}