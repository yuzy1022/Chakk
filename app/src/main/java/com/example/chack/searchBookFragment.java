package com.example.chack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class searchBookFragment extends Fragment {
    MainActivity activity;
    View dlgview;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
    AutoCompleteTextView auto;//자동완성 텍스트뷰
    ImageButton search_button,back_button;//검색버튼,뒤로가기 버튼
    ImageView barcode_button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static searchBookFragment newInstance(String param1, String param2) {
        searchBookFragment fragment = new searchBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public searchBookFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_searchbook, container, false);
        Context ct = container.getContext(); //프래그먼트의 context

        //검색텍스트, 버튼
        auto = view.findViewById(R.id.search_bar);
        search_button=view.findViewById(R.id.search_button);
        back_button=view.findViewById(R.id.back_button);
        barcode_button = view.findViewById(R.id.barcode_button);

        //바코드 버튼 클릭 시 이벤트
        barcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),BarcodeScan.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);         // 프래그먼트 > 액티비티 화면전환 성공
                startActivity(intent);
            }
        });

        //뒤로가기 버튼 클릭 시 이벤트
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(0);
            }
        });

        //검색 버튼 클릭시 이벤트
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = auto.getText().toString(); //검색어 가져와서 searchText에 저장

                if(searchText.equals("")) //검색어가 입력 안됐으면 토스트 메시지 출력
                    Toast.makeText(getActivity(),"검색어를 입력하세요",Toast.LENGTH_SHORT).show();
                else //입력 됐으면 Data클래스 static변수에 검색어 저장후 프래그먼트 띄움
                {
                    DataClass.searchText = searchText;
                    getChildFragmentManager().beginTransaction().replace(R.id.search_page, new searchResultFragment()).commitAllowingStateLoss();
                }
            }
        });
        return view;
    }


    public static void addBookfragment()
    {
        MainActivity.main.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new addBookFragment()).commitAllowingStateLoss();
    }
}