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

import com.example.chack.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.sql.Array;
import java.util.ArrayList;

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