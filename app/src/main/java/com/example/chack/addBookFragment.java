package com.example.chack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class addBookFragment extends Fragment {
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
    ImageView bookimage,barcode_button;//도서 이미지
    TextView bookname1, bookauthor1,bookinformation1;
    String select;
    RatingBar bookrating;
    LinearLayout bookplus;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addBookFragment newInstance(String param1, String param2) {
        addBookFragment fragment = new addBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public addBookFragment() {
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
//    public void AddBookdlg(){
//        AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
//        dlgview = View.inflate(getActivity(),R.layout.popupaddbook,null);
//        dlg.setView(dlgview);
//        AlertDialog alertDialog = dlg.create();
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(alertDialog.getWindow().getAttributes());
//        lp.width = 400;
//        lp.height = 500;
//        alertDialog.getWindow().setAttributes(lp);
//        dlg.show();
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_addbook, container, false);
        //자동완성 텍스트 뷰 아이템
        String[] items={"구의 증명", "스즈메의 문단속", "세이노의 가르침", "데일 카네기 인간관계론", "더 웍", "The Wok", "메리골드 마음 세탁소"};
        //버튼 및 이미지뷰
        auto = view.findViewById(R.id.search_bar);
        search_button=view.findViewById(R.id.search_button);
        back_button=view.findViewById(R.id.back_button);
        bookimage=view.findViewById(R.id.bookimage);
        bookname1 =view.findViewById(R.id.bookname1);
        bookauthor1=view.findViewById(R.id.bookauthor1);
        bookinformation1=view.findViewById(R.id.bookinformation1);
        bookrating = view.findViewById(R.id.bookrating);
        bookplus = view.findViewById(R.id.bookplus);
        barcode_button = view.findViewById(R.id.barcode_button);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_dropdown_item_1line,items);
        auto.setAdapter(adapter);

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
                if(v.equals(search_button)){
                    bookimage.setVisibility(View.VISIBLE);
                    bookname1.setVisibility(View.VISIBLE);
                    bookauthor1.setVisibility(View.VISIBLE);
                    bookinformation1.setVisibility(View.VISIBLE);
                    select = auto.getText().toString();
                    switch (select){
                        case "구의 증명" :
                            bookimage.setImageResource(R.drawable.img_7);
                            bookname1.setText("구의 증명");
                            bookauthor1.setText("최진영");
                            bookinformation1.setText("2015.03.30\n은행나무");
                            bookrating.setVisibility(View.VISIBLE);
                            bookplus.setEnabled(false);
                            break;
                        case "스즈메의 문단속" :
                            bookimage.setImageResource(R.drawable.img_1);
                            bookname1.setText("스즈메의 문단속");
                            bookauthor1.setText("신카이 마코토");
                            bookinformation1.setText("2023.01.13\n대원씨아이");
                            bookrating.setVisibility(View.VISIBLE);
                            bookplus.setEnabled(false);
                            break;
                        case "더 웍" :
                            bookimage.setImageResource(R.drawable.img_4);
                            bookname1.setText("더 웍");
                            bookauthor1.setText("J. Kenji Lopez-Alt");
                            bookinformation1.setText("2023.01.25\n영진닷컴");
                            bookrating.setVisibility(View.VISIBLE);
                            bookplus.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event){
                                    Intent intent = new Intent(getActivity(), popUpAddBook.class);
                                    startActivity(intent);
                                    return false;
                                }
                            });
//                            bookplus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    AddBookdlg();
//                                }
//                            });
                            bookplus.setEnabled(true);
                            break;
                        case "The Wok" :
                            bookimage.setImageResource(R.drawable.img_4);
                            bookname1.setText("더 웍");
                            bookauthor1.setText("J. Kenji Lopez-Alt");
                            bookinformation1.setText("2023.01.25\n영진닷컴");
                            bookrating.setVisibility(View.VISIBLE);
                            bookplus.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event){
                                    Intent intent = new Intent(getActivity(), popUpAddBook.class);
                                    startActivity(intent);
                                    return false;
                                }
                            });
//                            bookplus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    AddBookdlg();
//                                }
//                            });
                            bookplus.setEnabled(true);
                            break;
                        case "세이노의 가르침" :
                            bookimage.setImageResource(R.drawable.img_2);
                            bookname1.setText("세이노의 가르침");
                            bookauthor1.setText("세이노");
                            bookinformation1.setText("2023.03.02\n(주)연필");
                            bookrating.setVisibility(View.VISIBLE);
                            bookplus.setEnabled(false);
                            break;
                        case "데일 카네기 인간관계론" :
                            bookimage.setImageResource(R.drawable.img_3);
                            bookname1.setText("데일 카네기 인간관계론");
                            bookauthor1.setText("Dale Carnegie");
                            bookinformation1.setText("2019.10.07\n현대지성");
                            bookrating.setVisibility(View.VISIBLE);
                            bookplus.setEnabled(false);
                            break;
                        case "메리골드 마음 세탁소" :
                            bookimage.setImageResource(R.drawable.img_6);
                            bookname1.setText("메리골드 마음 세탁소");
                            bookauthor1.setText("윤정은");
                            bookinformation1.setText("2023.03.06\n북로망스");
                            bookrating.setVisibility(View.VISIBLE);
                            bookplus.setEnabled(false);
                            break;
                        case"":
                            bookimage.setVisibility(View.INVISIBLE);
                            bookname1.setVisibility(View.INVISIBLE);
                            bookauthor1.setVisibility(View.INVISIBLE);
                            bookinformation1.setVisibility(View.INVISIBLE);
                            bookrating.setVisibility(View.INVISIBLE);
                            bookplus.setEnabled(false);
                            break;
                    }
                }
            }
        });
        return view;
    }
}