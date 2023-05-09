package com.example.chack;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link futureRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class futureRecordFragment extends Fragment {

    private RecyclerView recyclerView;
    private FutureRecFragAdapter adapter = new FutureRecFragAdapter();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public futureRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment futureRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static futureRecordFragment newInstance(String param1, String param2) {
        futureRecordFragment fragment = new futureRecordFragment();
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
        Context ct = container.getContext(); //프래그먼트의 context
        View v = inflater.inflate(R.layout.fragment_future_record, container, false);

        //생성한 뷰객체를 이용해 리사이클러뷰를 초기화
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        //스크롤 방향 세로로 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(ct, RecyclerView.VERTICAL, false));

        //리사이클러 뷰에 객체 10개 생성해 줌 (추후 서재에 있는 책 목록으로 객체 생성 하게끔 수정 필요)
        for(int i = 0; i < 10; i++)
        {
            BookItemRecord item = new BookItemRecord();
            item.image = R.drawable.img_6;
            item.rating = (float)4;
            item.name = "메리골드 마음 세탁소";
            item.writer = "윤정은";
            item.setStartingDate(2023, 5, 15);
            adapter.setArrayData(item);
        }

        recyclerView.setAdapter(adapter);

        return v;
    }
}

//내서재 읽고 싶은 책 탭의 리사이클러뷰 뷰홀더 클래스
class FutureRecFragViewHolder extends RecyclerView.ViewHolder
{

    public ImageView image;
    public TextView name, writer, date, remainDay;
    public RatingBar rating;

    //생성자
    FutureRecFragViewHolder(Context context, View itemView)
    {
        super(itemView);

        //책이미지, 이름, 작가, 날짜, 레이팅바 인플레이트
        image = itemView.findViewById(R.id.BookImgView);
        name = itemView.findViewById(R.id.bookName);
        writer = itemView.findViewById(R.id.bookWriter);
        date = itemView.findViewById(R.id.readDate);
        rating = itemView.findViewById(R.id.rating);
        remainDay = itemView.findViewById(R.id.remainDay);
    }
}


//내서재 읽고 싶은 책 탭의 리사이클러뷰의 어댑터 클래스
class FutureRecFragAdapter extends RecyclerView.Adapter<FutureRecFragViewHolder>
{
    //읽은 책 정보를 담아놓을 리스트
    private ArrayList<BookItemRecord> arrayList;

    //생성자
    public FutureRecFragAdapter()
    {
        arrayList = new ArrayList<>();
    }

    //뷰 홀더를 생성하는 매소드 ex)리스트 목록이 10개 라면, 위아래 버퍼를 고려해 13~15번 호출된다)
    public FutureRecFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //내서재 읽고싶은 책 탭의 책 아이템 레이아웃을 인플레이트하여 뷰홀더 생성
        View view = inflater.inflate(R.layout.future_book_item_record, parent, false);
        FutureRecFragViewHolder viewHolder = new FutureRecFragViewHolder(context, view);

        //생성한 뷰홀더 리턴
        return viewHolder;
    }

    //앞에 있던 객체가 스크롤되어 안보이게 되면 그 객체를 뒤로 불러와 데이터를 재설정해주는 메소드
    public void onBindViewHolder(FutureRecFragViewHolder holder, int position)
    {
        BookItemRecord item = arrayList.get(position);  //item에
        holder.image.setImageResource(item.image); //가져온 소스로 이미지 뷰의 소스 설정
        holder.name.setText(item.name);  //가져온 텍스트로 책이름 설정
        holder.writer.setText(item.writer);  //가져온 텍스트로 작가 설정
        holder.date.setText(item.getStartingDate());  //날짜 설정
        holder.rating.setRating(item.rating);  //가져온 평점으로 평점 설정
        holder.remainDay.setText("10");  //남은 날짜 설정
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
}