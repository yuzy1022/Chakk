package com.example.chack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recordFragment extends Fragment {

    ViewPager2 viewPager2;
    TabLayout tabLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public recordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recordFragment newInstance(String param1, String param2) {
        recordFragment fragment = new recordFragment();
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
                             Bundle savedInstanceState)
    {
        //내서재 xml로 뷰객체 생성
        View v = inflater.inflate(R.layout.fragment_record, container, false);

        /*//fragment_record  xml안에 viewpager가져와 뷰페이저에 넣음
        viewPager = v.findViewById(R.id.viewpager);
        //뷰페이저에 어댑터 설정
        recordViewPagerAdapter adapter = new recordViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //탭 레이아웃 가져와 뷰페이저 설정해줌
        TabLayout tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);*/

        viewPager2 = v.findViewById(R.id.viewpager);
        tabLayout = v.findViewById(R.id.tab_layout);

        //뷰페이저2 어댑터 설정
        recordViewPagerAdapter viewPager2Adapter = new recordViewPagerAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
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
                    case 0: tab.setText("읽은 책");
                        break;
                    case 1: tab.setText("읽고있는 책");
                        break;
                    case 2: tab.setText("읽고싶은 책");
                        break;
                    case 3: tab.setText("메모");
                }
            }
        }).attach();

        return v;
    }
}


/*//내서재 탭바 프래그먼트에 쓸 어댑터 클래스
class recordViewPagerAdapter extends FragmentPagerAdapter
{
    private ArrayList<Fragment> arrayList = new ArrayList<>();  //프래그먼트를 담을 리스트
    private ArrayList<String> name = new ArrayList<>();  //탭 이름을 넣을 리스트

    //생성자
    public recordViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        //arrayList에 각 프래그먼트 클래스 추가
        arrayList.add(new pastRecordFragment());
        arrayList.add(new currentRecordFragment());
        arrayList.add(new futureRecordFragment());
        arrayList.add(new memoRecordFragment());

        //name 리스트에 탭바 이름으로 쓸 문자열 추가
        name.add("읽은 책");
        name.add("읽고 있는 책");
        name.add("읽고 싶은 책");
        name.add("메모");
    }


    //position을 입력받아 name에서 position번쨰의 탭이름을 리턴해주는 메소드
    @Override
    public CharSequence getPageTitle(int position)
    {
        return name.get(position);
    }

    //position을 입력받아 arrayList에서 position번째의 프래그먼트를 리턴해주는 메소드
    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return arrayList.get(position);
    }

    //arrayList에 있는 프래그먼트 개수를 리턴해주는 메소드
    @Override
    public int getCount()
    {
        return arrayList.size();
    }
}*/



//뷰페이저2 어댑터 클래스
class recordViewPagerAdapter extends FragmentStateAdapter
{

    public recordViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle)
    {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        switch (position) {
            case 0:
                return new pastRecordFragment();
            case 1:
                return new currentRecordFragment();
            case 2:
                return new futureRecordFragment();
            case 3:
                return new memoRecordFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;       // 페이지 수
    }
}