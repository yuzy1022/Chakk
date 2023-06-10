package com.example.chack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchResultFragment extends Fragment {

    private RecyclerView recyclerView; //검색결과 표시할 리사이클러 뷰
    private AddBookFragAdapter adapter = new AddBookFragAdapter(); //리사이클러뷰의 어댑터

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchResultFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static searchResultFragment newInstance(String param1, String param2) {
        searchResultFragment fragment = new searchResultFragment();
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
        View v =  inflater.inflate(R.layout.fragment_recyclerview, container, false); //프래그먼트 레이아웃(xml)을 가져와 뷰객체 생성

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ct, RecyclerView.VERTICAL, false)); //리사이클러뷰 세로방향 설정

        //레트로핏2을 이용해 검색어로 알라딘 api에 검색결과 받아옴
        RetrofitAladin retrofitAladin = RetrofitAladin.getInstance();
        AladinHttpRequest httpRequest_aladin = retrofitAladin.getRetrofitInterface();
        httpRequest_aladin.getSearchBook("ttbjhp22121729001", DataClass.searchText, "Book", "100", "js", "20131101").enqueue(new Callback<DataClass>()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DataClass> call, retrofit2.Response<DataClass> response)
            {
                DataClass data = response.body(); //받아온 검색결과 데이터를 저장

                if(data.getItem().length != 0) //검색결과가 1개 이상 있으면
                {
                    Log.e("태그", "onResponse: " + response.body());
                    Log.e("태그", "결과 수 : " + data.getItem().length);

                    //어댑터에 검색결과를 모두 넣어줌
                    for(int i = 0; i < data.getItem().length; i++)
                    {
                        adapter.setArrayData(data.getItem()[i]); //Item 클래스의 객체로 넣어줌
                    }

                    recyclerView.setAdapter(adapter);  //리사이클러 뷰에 어댑터 설정
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


//검색결과 프래그먼트 리사이클러뷰 뷰홀더 클래스
class AddBookFragViewHolder extends RecyclerView.ViewHolder
{

    public ImageView image;
    public TextView name, writer, date, pub;
    public RatingBar rating;

    //생성자
    AddBookFragViewHolder(Context context, View itemView)
    {
        super(itemView);

        //책이미지, 이름, 작가, 정보, 레이팅바
        image = itemView.findViewById(R.id.bookimage);
        name = itemView.findViewById(R.id.bookname);
        writer = itemView.findViewById(R.id.bookauthor);
        date = itemView.findViewById(R.id.bookdate);
        pub = itemView.findViewById(R.id.bookpub);
        rating = itemView.findViewById(R.id.bookrating);
    }
}


//검색결과 프래그먼트 리사이클러뷰의 어댑터 클래스
class AddBookFragAdapter extends RecyclerView.Adapter<AddBookFragViewHolder>
{
    //책 정보를 담아놓을 리스트
    private ArrayList<Item> arrayList;
    private Context con;

    //생성자
    public AddBookFragAdapter()
    {
        arrayList = new ArrayList<>();
    }

    //뷰 홀더를 생성하는 매소드 ex)리스트 목록이 10개 라면, 위아래 버퍼를 고려해 13~15번 호출된다)
    public AddBookFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        con = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //검색결과 레이아웃을 인플레이트하여 뷰홀더 생성
        View view = inflater.inflate(R.layout.add_book_item, parent, false);
        AddBookFragViewHolder viewHolder = new AddBookFragViewHolder(context, view);

        //생성한 뷰홀더 리턴
        return viewHolder;
    }

    //앞에 있던 객체가 스크롤되어 안보이게 되면 그 객체를 뒤로 불러와 데이터를 재설정해주는 메소드
    public void onBindViewHolder(AddBookFragViewHolder holder, int position)
    {
        Item item = arrayList.get(position);  //리스트에서 책정보와 가져와 item에 넣음
        holder.date.setText(item.getPubDate());  //출판일 설정
        holder.pub.setText(item.getPublisher()); //출판사 설정
        holder.rating.setRating(Float.parseFloat(item.getCustomerReviewRank())/2);  //가져온 평점으로 평점 설정

        if(item.getTitle().length() > 40) //책이름 40자 넘어가면 자르고 ...붙이기
            holder.name.setText(item.getTitle().substring(0, 41) + "...");
        else
            holder.name.setText(item.getTitle());
        if(item.getAuthor().length() > 45) //작가 45자 넘어가면 자르고 ...붙이기
            holder.writer.setText(item.getTitle().substring(0, 46) + "...");
        else
            holder.writer.setText(item.getAuthor());

        //Glide 라이브러리로 이미지뷰에 네트워크 이미지 표시 (책 표지)
        String url = item.getCover();
        Glide.with(con).load(url).into(holder.image);
    }

    //이미지소스 리스트의 개수를 리턴해주는 메소드
    public int getItemCount()
    {
        return  arrayList.size();
    }

    //이미지소스 리스트에 새로운 소스를 추가해주는 메소드
    public void setArrayData(Item data)
    {
        arrayList.add(data);
    }

}