package com.example.chack;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link currentRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class currentRecordFragment extends Fragment {

    private RecyclerView recyclerView;
    private CurrentRecFragAdapter adapter = new CurrentRecFragAdapter();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public currentRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment currentRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static currentRecordFragment newInstance(String param1, String param2) {
        currentRecordFragment fragment = new currentRecordFragment();
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
        Context ct = this.getContext(); //프래그먼트의 context
        View v = inflater.inflate(R.layout.fragment_current_record, container, false);

        //생성한 뷰객체를 이용해 리사이클러뷰를 초기화
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        //스크롤 방향 세로로 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(ct, RecyclerView.VERTICAL, false));

        //리사이클러 뷰에 객체 10개 생성해 줌 (추후 서재에 있는 책 목록으로 객체 생성 하게끔 수정 필요)
        for(int i = 0; i < 10; i++)
        {
            BookItemRecord item = new BookItemRecord();
            item.image = R.drawable.img_1;
            item.rating = (float)4.5;
            item.name = "스즈메의 문단속";
            item.writer = "신카이 마코토";
            item.setStartingDate(2023, 4, 1);
            item.bookPage = 330;
            item.readPage = 120;
            item.isbn13 = "9791169791977";
            item.pub = "대원씨아이(단행본)";
            adapter.setArrayData(item);
        }

        //커스텀 이벤트 리스너 객체를 생성하여 어댑터에 전달
        adapter.setOnItemClickListener(new AddBookFragAdapter.OnItemClickListener()
        {
            //아이템 클릭시
            @Override
            public void onItemClick(View v, int position)
            {
                DataClass.searchText = adapter.getItemPosition(position).isbn13;
                //addBookFragment생성
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new addBookFragment()).commitAllowingStateLoss();
            }
        });

        recyclerView.setAdapter(adapter);  //리사이클러 뷰에 어댑터 설정

        return v;
    }
}





//읽고있는책 프래그먼트 리사이클러뷰의 어댑터 클래스
class CurrentRecFragAdapter extends RecyclerView.Adapter<CurrentRecFragAdapter.CurrentRecFragViewHolder>
{

    private ArrayList<BookItemRecord> arrayList; //책과 사용자 정보를 담아놓을 리스트
    private AddBookFragAdapter.OnItemClickListener mListener = null; //setOnItemClickListener메소드로 전달된 객체를 저장할 변수(mListener)

    //생성자
    public CurrentRecFragAdapter()
    {
        arrayList = new ArrayList<>();
    }

    //뷰 홀더를 생성하는 매소드 ex)리스트 목록이 10개 라면, 위아래 버퍼를 고려해 13~15번 호출된다)
    public CurrentRecFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //읽고있는 책 레이아웃을 인플레이트하여 뷰홀더 생성
        View view = inflater.inflate(R.layout.current_book_item_record, parent, false);
        CurrentRecFragViewHolder viewHolder = new CurrentRecFragViewHolder(context, view);

        //생성한 뷰홀더 리턴
        return viewHolder;
    }

    //앞에 있던 객체가 스크롤되어 안보이게 되면 그 객체를 뒤로 불러와 데이터를 재설정해주는 메소드
    public void onBindViewHolder(CurrentRecFragViewHolder holder, int position)
    {
        BookItemRecord item = arrayList.get(position);  //리스트에서 책정보와 사용자정보 가져와 item에 넣음
        holder.image.setImageResource(item.image); //가져온 소스로 이미지 뷰의 소스 설정
        holder.name.setText(item.name);  //가져온 텍스트로 책이름 설정
        holder.writer.setText(item.writer);  //가져온 텍스트로 작가 설정
        holder.date.setText(item.getStartingDate());  //날짜 설정
        holder.rating.setRating(item.rating);  //가져온 평점으로 평점 설정
        holder.readPage.setText(item.readPage + " / " + item.bookPage + " 페이지"); //읽은 페이지 수 설정
        holder.progressBar.setMax(item.bookPage);  //프로그래스바 최대값을 책 페이지로 설정
        holder.progressBar.setProgress(item.readPage);  //프로그래스바 진행률을 읽은 페이지 수로 설정
        holder.readPercent.setText((int)((double)item.readPage / item.bookPage * 100) + "%"); //읽은 % 설정
        holder.pub.setText(item.pub); //출판사 설정
    }

    //이미지소스 리스트의 개수를 리턴해주는 메소드
    public int getItemCount()
    {
        return  arrayList.size();
    }

    //이미지소스 리스트에 새로운 소스를 추가해주는 메소드
    public void setArrayData(BookItemRecord data)
    {
        arrayList.add(data);
    }

    //커스텀 리스너 인터페이스 정의
    public interface OnItemClickListener
    {
        void onItemClick(View v, int position);
    }

    //리스너 객체를 전달하는 메소드(setOnItemClickListener)
    public void setOnItemClickListener(AddBookFragAdapter.OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    //position을 입력받아 해당 위치의 아이템 정보를 리턴해줌
    public BookItemRecord getItemPosition(int pos)
    {
        return arrayList.get(pos);
    }




    //읽고있는 책 프래그먼트 리사이클러뷰 뷰홀더 클래스
    class CurrentRecFragViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView image;
        public TextView name, writer, date, readPage, readPercent, pub;
        public RatingBar rating;
        public ProgressBar progressBar;

        //생성자
        CurrentRecFragViewHolder(Context context, View itemView)
        {
            super(itemView);

            //책이미지, 이름, 작가, 날짜, 레이팅바, 읽은 페이지, 읽은 %, 프로그레스바 인플레이트
            image = itemView.findViewById(R.id.BookImgView);
            name = itemView.findViewById(R.id.bookName);
            writer = itemView.findViewById(R.id.bookWriter);
            date = itemView.findViewById(R.id.readDate);
            rating = itemView.findViewById(R.id.rating);
            readPage = itemView.findViewById(R.id.readPage);
            readPercent = itemView.findViewById(R.id.readPercent);
            progressBar = itemView.findViewById(R.id.progressBar);
            pub = itemView.findViewById(R.id.bookPub);

            //리사이클러뷰의 아이템 클릭 이벤트 (클릭하면 addbook 프래그먼트 생성하게끔)
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition(); //어댑터 내 아이템의 위치를 리턴해주는 메소드
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        if(mListener != null)
                            mListener.onItemClick(v, pos);
                    }
                }
            });
        }
    }
}