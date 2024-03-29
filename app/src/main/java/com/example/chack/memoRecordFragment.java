package com.example.chack;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link memoRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class memoRecordFragment extends Fragment {

    private RecyclerView recyclerView;  //메모 프래그먼트의 리사이클러뷰 담을거
    private MemoRecFragAdapter adapter = new MemoRecFragAdapter();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public memoRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment memoRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static memoRecordFragment newInstance(String param1, String param2) {
        memoRecordFragment fragment = new memoRecordFragment();
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
        View v = inflater.inflate(R.layout.fragment_memo_record, container, false);

        //생성한 뷰객체를 이용해 리사이클러뷰를 초기화
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        //스크롤 방향 세로로 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(ct, RecyclerView.VERTICAL, false));

        //리사이클러 뷰에 객체 10개 생성해 줌 (추후 서재에 있는 책 목록으로 객체 생성 하게끔 수정 필요)
        /*
        for(int i = 0; i < 10; i++)
        {
            MemoItemRecord item = new MemoItemRecord();
            item.memo = "모르는 건 죄가 아닌데 기다리지 못하는 건 죄가 되기도 한다고";
            item.page = 23;
            item.bookName = "구의 증명";

            adapter.setArrayData(item);
        }
         */

        MemoItemRecord item = new MemoItemRecord();
        item.memo = "모르는 건 죄가 아닌데 기다리지 못하는 건 죄가 되기도 한다고";
        item.page = 23;
        item.bookName = "구의 증명";
        adapter.setArrayData(item);

        MemoItemRecord item4 = new MemoItemRecord();
        item4.memo = "어떤 아픈 기억은 지워져야만 살 수 있기도 하고, 어떤 기억은 아프지만 그 불행을 이겨내는 힘으로 살기도 하지. 슬픔이 때론 살아가는 힘이 되기도 해.";
        item4.page = 55;
        item4.bookName = "메리골드 마음 세탁소";
        adapter.setArrayData(item4);

        MemoItemRecord item2 = new MemoItemRecord();
        item2.memo = "가시적 결과를 외부에서 찾지 말고 내부에서 찾아라. 당신 자신의 노력을 인정해 주고 칭찬하여야 할 주체는 타인이나 직장이나 사회가 아니다.";
        item2.page = 66;
        item2.bookName = "세이노의 가르침";
        adapter.setArrayData(item2);

        MemoItemRecord item3 = new MemoItemRecord();
        item3.memo = "노력한 만큼의 대가는 반드시 주어진다는 것을 믿어라. 문제는 그 시기가 당신이 생각하는 시간보다 더 미래에 있다는 점이다.";
        item3.page = 67;
        item3.bookName = "세이노의 가르침";
        adapter.setArrayData(item3);

        MemoItemRecord item5 = new MemoItemRecord();
        item5.memo = "마음을 치유하고 싶다며 스스로를 열어 보이는 이들은 꽤나 용감한 사람들이다. 대부분의 사람들은 속이 곪아 있다. 곪아 있는지도, 아픈지도 인지하지 못하고 살아가는 이들이 대부분이다. 가장 아픈 상처 한두 개쯤은 치유해주어야 살 만해진다는 것도 모르면서 살아간다.";
        item5.page = 110;
        item5.bookName = "메리골드 마음 세탁소";
        adapter.setArrayData(item5);



        recyclerView.setAdapter(adapter); //리사이클러 뷰에 어댑터 설정

        return v;
    }
}

//메모 프래그먼트의 뷰홀더 클래스
class MemoRecFragViewHolder extends RecyclerView.ViewHolder
{

    public TextView memo, bookName;
    public LinearLayout layout;

    //생성자
    MemoRecFragViewHolder(Context context, View itemView)
    {
        super(itemView);

        //메모, 제목, 레이아웃 인플레이트
        memo = itemView.findViewById(R.id.memo);
        bookName = itemView.findViewById(R.id.bookName);
        layout = itemView.findViewById(R.id.layout);
    }
}


//메모 프래그먼트 리사이클러뷰의 어댑터 클래스
class MemoRecFragAdapter extends RecyclerView.Adapter<MemoRecFragViewHolder>
{
    //메모 정보를 담아놓을 리스트
    private ArrayList<MemoItemRecord> arrayList;

    //생성자
    public MemoRecFragAdapter()
    {
        arrayList = new ArrayList<>();
    }

    //뷰 홀더를 생성하는 매소드 ex)리스트 목록이 10개 라면, 위아래 버퍼를 고려해 13~15번 호출된다)
    public MemoRecFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //메모 레이아웃을 인플레이트하여 뷰홀더 생성
        View view = inflater.inflate(R.layout.memo_item_record, parent, false);
        MemoRecFragViewHolder viewHolder = new MemoRecFragViewHolder(context, view);

        //생성한 뷰홀더 리턴
        return viewHolder;
    }

    //앞에 있던 객체가 스크롤되어 안보이게 되면 그 객체를 뒤로 불러와 데이터를 재설정해주는 메소드
    public void onBindViewHolder(MemoRecFragViewHolder holder, int position)
    {
        MemoItemRecord item = arrayList.get(position);  //리스트에서 메모 정보 가져와서 item에 넣기
        holder.memo.setText(item.memo);  //메모 아이템의 메모설정
        holder.bookName.setText("-" + item.bookName + ", " + item.page + "페이지-");  //메모 아이템의 책제목과 페이지 설정
        if(position % 2 == 0)  //짝수번째 항목일 때만 보라색 백그라운드 설정
        {
            holder.layout.setBackgroundResource(R.drawable.memo_purple_background_layout);
        }

    }

    //이미지소스 리스트의 개수를 리턴해주는 메소드
    public int getItemCount()
    {
        return  arrayList.size();
    }

    //이미지소스 리스트에 새로운 소스를 추가해주는 메소드
    public void setArrayData(MemoItemRecord data)
    {
        arrayList.add(data);
    }
}