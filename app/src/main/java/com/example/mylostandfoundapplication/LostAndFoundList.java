package com.example.mylostandfoundapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class LostAndFoundList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ListView listview;

    public LostAndFoundList() {
        // Required empty public constructor
    }

    public static LostAndFoundList newInstance(String param1, String param2) {
        LostAndFoundList fragment = new LostAndFoundList();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lostandfoundlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = view.findViewById(R.id.lostAndFoundListView);
        listViewManage();
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                ((MainActivity)getActivity()).selectArticleFragment(position+1);
            }
        });
    }

    public void listViewManage(){

        List<Item> table = ((MainActivity)getActivity()).dbGetAll();
        List<String> articleNames = new ArrayList<>();
        for (int i =0 ; i < table.size(); i++){
            Item x = table.get(i);
            articleNames.add(x.Status + " : " + x.Description);
        }

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, articleNames);
        listview.setAdapter(listViewAdapter);
    }
}
