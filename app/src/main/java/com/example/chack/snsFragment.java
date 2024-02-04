package com.example.chack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chack.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.sql.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/*
public class snsFragment extends Fragment {
    View v;
    MainActivity activity;
    Button writeBtn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<post> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public snsFragment() {
        // Required empty public constructor
    }

 public static snsFragment newInstance(String param1, String param2) {
        snsFragment fragment = new snsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void onAttach(Context context){
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
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
        v = inflater.inflate(R.layout.fragment_sns, container, false);
        writeBtn = v.findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(3);
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        arrayList = new ArrayList<>(); //post 객체 담을 arraylist

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("post");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    post posts = snapshot.getValue(post.class);
                    arrayList.add(posts);
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
                Log.e("snserror", arrayList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("snsFragment", String.valueOf(error.toException()));
            }
        });

        adapter = new PostAdapter(arrayList, activity);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return v;
    }
}
 */


public class snsFragment extends Fragment {
    MainActivity activity;
    Button writeBtn; //글쓰기 버튼
    private RecyclerView recyclerView;  //메모 프래그먼트의 리사이클러뷰 담을거
    private snsFragAdapter adapter = new snsFragAdapter();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public void onAttach(Context context){
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public snsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment snsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static snsFragment newInstance(String param1, String param2) {
        snsFragment fragment = new snsFragment();
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
        //글쓰기 버튼
        View v = inflater.inflate(R.layout.fragment_sns, container, false);
        writeBtn = v.findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(3);
            }
        });

        //리사이클러뷰
        Context ct = this.getContext(); //프래그먼트의 context
        //View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        //생성한 뷰객체를 이용해 리사이클러뷰를 초기화
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        //스크롤 방향 세로로 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(ct, RecyclerView.VERTICAL, false));

        
        postInfo item = new postInfo();
        item.tag = "독서모임";
        item.userName = "김철수";
        item.address = "서울 중구 세종대로 110 (태평로1가)";
        item.mainPost = "독서모임 회원 모집합니다. 주 1회 만나서 각자 읽은 책에 대해 가볍게 대화나누는 모임입니다. 관심 있으신 분은 편하게 연락주세요.";
        adapter.setArrayData(item);

        postInfo item2 = new postInfo();
        item2.tag = "도서 추천";
        item2.userName = "홍길동";
        item2.address = "";
        item2.mainPost = "너의 이름은, 날씨의 아이 작가인 신카이 마코토 감독의 신작 스즈메의 문단속 입니다. 규슈의 조용한 마을에서 이모와 함께 살아가는 17살 소녀 스즈메. 어느 날 등굣길에 아름다운 청년과 스쳐간 스즈메는 “문을 찾고 있다”는 그의 뒤를 쫓아 산속 폐허에 들어선다. 그곳에서 스즈메가 발견한 것은 붕괴에서 빗겨난 듯 덩그러니 남겨진 낡고 하얀 문. 무언가에 이끌리듯 스즈메는 문을 향해 손을 뻗는데…. 과거와 현재와 미래를 잇는 스즈메의 ‘문단속’ 이야기 입니다.";
        adapter.setArrayData(item2);


        recyclerView.setAdapter(adapter); //리사이클러 뷰에 어댑터 설정

        return v;
    }
}

//메모 프래그먼트의 뷰홀더 클래스
class snsFragViewHolder extends RecyclerView.ViewHolder
{
    public TextView userName; //유저 이름
    public TextView tags; //태그
    public TextView mainPost; //글 내용
    public TextView address;  //주소


    //생성자
    snsFragViewHolder(Context context, View itemView)
    {
        super(itemView);

        //유저이름, 태그, 글내용, 주소 인플레이트
        userName = itemView.findViewById(R.id.userName);
        tags = itemView.findViewById(R.id.tags);
        mainPost = itemView.findViewById(R.id.mainPost);
        address = itemView.findViewById(R.id.address);
    }
}


//메모 프래그먼트 리사이클러뷰의 어댑터 클래스
class snsFragAdapter extends RecyclerView.Adapter<snsFragViewHolder>
{
    //메모 정보를 담아놓을 리스트
    private ArrayList<postInfo> arrayList;

    //생성자
    public snsFragAdapter()
    {
        arrayList = new ArrayList<>();
    }

    //뷰 홀더를 생성하는 매소드 ex)리스트 목록이 10개 라면, 위아래 버퍼를 고려해 13~15번 호출된다)
    public snsFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //글 레이아웃을 인플레이트하여 뷰홀더 생성
        View view = inflater.inflate(R.layout.list_post, parent, false);
        snsFragViewHolder viewHolder = new snsFragViewHolder(context, view);

        //생성한 뷰홀더 리턴
        return viewHolder;
    }

    //앞에 있던 객체가 스크롤되어 안보이게 되면 그 객체를 뒤로 불러와 데이터를 재설정해주는 메소드
    public void onBindViewHolder(snsFragViewHolder holder, int position)
    {
        postInfo item = arrayList.get(position);  //리스트에서 글 정보 가져와서 item에 넣기
        holder.tags.setText(item.tag);  //태그 설정
        holder.userName.setText(item.userName); //유저이름 설정
        holder.mainPost.setText(item.mainPost); //글 내용 설정
        holder.address.setText(item.address); //주소 설정
    }

    //이미지소스 리스트의 개수를 리턴해주는 메소드
    public int getItemCount()
    {
        return  arrayList.size();
    }

    //이미지소스 리스트에 새로운 소스를 추가해주는 메소드
    public void setArrayData(postInfo data)
    {
        arrayList.add(data);
    }
}