package com.example.chack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addBookFragment extends Fragment {

    ViewPager2 viewPager2;
    ImageView bookimage;
    TextView bookname, bookauthor, bookdate, bookpub;
    RatingBar bookrating;
    ImageButton back_button;
    MainActivity activity;
    TabLayout tabLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("태그", getActivity().toString());
        //activity = (MainActivity)getActivity();
        activity = MainActivity.main;
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
     * @return A new instance of fragment addBookFragment.
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //fragment_addbook xml로 뷰객체 생성
        View v = inflater.inflate(R.layout.fragment_addbook, container, false);
        Context ct = container.getContext(); //프래그먼트의 context

        viewPager2 = v.findViewById(R.id.viewpager);
        tabLayout = v.findViewById(R.id.tab_layout);

        //뷰페이저2 어댑터 설정
        addBookViewPager2Adapter viewPager2Adapter = new addBookViewPager2Adapter(getActivity().getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(viewPager2Adapter);

        //TabLayout 사용을 위해 MainAcitivity에서 TabLayoutMediator( ) 객체를 생성한 후 TabLayout을 ViewPager2에 연결
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy()
        {
            //탭 이름 추가
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
            {
                switch(position)
                {
                    case 0: tab.setText("과거");
                            break;
                    case 1: tab.setText("현재");
                            break;
                    case 2: tab.setText("미래");
                }
            }
        }).attach();

        bookimage = v.findViewById(R.id.bookimage);
        bookname = v.findViewById(R.id.bookname);
        bookauthor = v.findViewById(R.id.bookauthor);
        bookpub = v.findViewById(R.id.bookpub);
        bookdate = v.findViewById(R.id.bookdate);
        bookrating = v.findViewById(R.id.bookrating);
        back_button = v.findViewById(R.id.back_button);

        //뒤로가기 버튼 클릭 시 이벤트
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(1);
            }
        });

        RetrofitAladin retrofitAladin = RetrofitAladin.getInstance(false);
        AladinHttpRequest httpRequest_aladin = retrofitAladin.getRetrofitInterface();
        httpRequest_aladin.getISBNSearchBook("ttbjhp22121729001", DataClass.searchText, "ISBN13",  "js", "20131101").enqueue(new Callback<DataClass>()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DataClass> call, retrofit2.Response<DataClass> response)
            {
                DataClass data = response.body(); //받아온 검색결과 데이터를 저장

                if(data.getItem().length != 0) //검색결과가 1개 이상 있으면
                {
                    Log.e("태그", "onResponse: " + response.body());
                    Log.e("태그", "result : " + data.getItem()[0]);

                    Item item = data.getItem()[0];  //책정보 가져와 item에 넣음
                    bookdate.setText(item.getPubDate());  //출판일 설정
                    bookpub.setText(item.getPublisher()); //출판사 설정
                    bookrating.setRating(Float.parseFloat(item.getCustomerReviewRank())/2);  //가져온 평점으로 평점 설정
                    bookname.setText(item.getTitle());  //제목설정
                    bookauthor.setText(item.getAuthor()); //작가설정

                    //Glide 라이브러리로 이미지뷰에 네트워크 이미지 표시 (책 표지)
                    String url = item.getCover();
                    Glide.with(ct).load(url)
                            .error(R.drawable.x) //이미지 불러오지 못했을 때 x표시 이미지 띄움
                            .into(bookimage);
                }
            }

            @Override
            public void onFailure(Call<DataClass> call, Throwable t)
            {
                Log.e("태그", "onFailure: " + t.getMessage());
            }
        });

        return v;
    }
}


//뷰페이저2 어댑터 클래스
class addBookViewPager2Adapter extends FragmentStateAdapter
{

    public addBookViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle)
    {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        switch (position) {
            case 0:
                return new addBookPastFragment();
            case 1:
                return new addBookCurrentFragment();
            case 2:
                return new addBookFutureFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;       // 페이지 수
    }
}

