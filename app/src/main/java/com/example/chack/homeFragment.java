package com.example.chack;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class homeFragment extends Fragment {

    MainActivity activity;

    private RecyclerView futureRV, pastRV, currentRV;  //미래, 과거, 현재 리사이클러 뷰
    private HomeFragAdapter futureAdt = new HomeFragAdapter(), pastAdt = new HomeFragAdapter(), currentAdt  = new HomeFragAdapter();  //미래, 과거, 현재 어댑터

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
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

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


    @Override//레이아웃을 인플레이트 하는 메소드.  뷰 객체를 얻어 초기화
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Context ct = container.getContext(); //프래그먼트의 context
        View v =  inflater.inflate(R.layout.fragment_home, container, false); //프래그먼트 레이아웃(xml)을 가져와 뷰객체 생성

        //생성한 뷰객체를 이용해 미래, 과거, 현재 리사이클러뷰를 초기화
        futureRV = (RecyclerView) v.findViewById(R.id.futureRV);
        pastRV = (RecyclerView) v.findViewById(R.id.pastRV);
        currentRV = (RecyclerView) v.findViewById(R.id.currentRV);

        //미래, 과거, 현재 리사이클러 뷰의 스크롤 방향을 가로로 설정
        futureRV.setLayoutManager(new LinearLayoutManager(ct, RecyclerView.HORIZONTAL, false));
        pastRV.setLayoutManager(new LinearLayoutManager(ct, RecyclerView.HORIZONTAL, false));
        currentRV.setLayoutManager(new LinearLayoutManager(ct, RecyclerView.HORIZONTAL, false));

        //리사이클러 뷰에 수동으로 객체 생성(추후 서재에 있는 책 목록으로 객체 생성 하게끔 수정 필요)
        currentAdt.setArrayData(R.drawable.img_1);
        currentAdt.setArrayData(R.drawable.img_7);
        pastAdt.setArrayData(R.drawable.img_2);
        futureAdt.setArrayData(R.drawable.img_3);
        futureAdt.setArrayData(R.drawable.img_4);
        futureAdt.setArrayData(R.drawable.img_5);
        futureAdt.setArrayData(R.drawable.img_6);



        //리사이클러 뷰에 어댑터를 설정
        futureRV.setAdapter(futureAdt);
        pastRV.setAdapter(pastAdt);
        currentRV.setAdapter(currentAdt);

        //서가 채우기 버튼 클릭리스너 등록
        AppCompatButton bookAdd = v.findViewById(R.id.bookAdd);
        bookAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(1);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}

//메인화면 리사이클러 뷰의 뷰홀더 클래스
class HomeFragViewHolder extends RecyclerView.ViewHolder
{
    //책사진 넣을 이미지 뷰
    public ImageView imageView;

    //생성자
    HomeFragViewHolder(Context context, View itemView)
    {
        super(itemView);

        //메인화면 책사진 xml의 이미지뷰를 인플레이트
        imageView = itemView.findViewById(R.id.mainBookImgView);
    }
}


//메인화면 리사이클러 뷰의 어댑터 클래스
class HomeFragAdapter extends RecyclerView.Adapter<HomeFragViewHolder>
{
    //이미지 뷰의 교체될 이미지 소스를 담아놓을 리스트
    private ArrayList<Integer> arrayList;

    //생성자
    public HomeFragAdapter()
    {
        arrayList = new ArrayList<>();
    }

    //뷰 홀더를 생성하는 매소드 ex)리스트 목록이 10개 라면, 위아래 버퍼를 고려해 13~15번 호출된다)
    public HomeFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //메인화면에 나올 책사진 xml을 인플레이트하여 뷰홀더 생성
        View view = inflater.inflate(R.layout.book_item_main, parent, false);
        HomeFragViewHolder viewHolder = new HomeFragViewHolder(context, view);

        //생성한 뷰홀더 리턴
        return viewHolder;
    }

    //앞에 있던 객체가 스크롤되어 안보이게 되면 그 객체를 뒤로 불러와 데이터를 재설정해주는 메소드
    public void onBindViewHolder(HomeFragViewHolder holder, int position)
    {
        int src = arrayList.get(position); //리스트에서 이미지 뷰에 설정할 이미지의 소스를 가져옴
        holder.imageView.setImageResource(src); //가져온 소스로 이미지 뷰의 소스 설정
    }

    //이미지소스 리스트의 개수를 리턴해주는 메소드
    public int getItemCount()
    {
        return  arrayList.size();
    }

    //이미지소스 리스트에 새로운 소스를 추가해주는 메소드
    public void setArrayData(int srcData)
    {
        arrayList.add(srcData);
    }
}

