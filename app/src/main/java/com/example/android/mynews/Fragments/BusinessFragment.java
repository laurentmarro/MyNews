package com.example.android.mynews.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.mynews.Adapter.MyAdapter;
import com.example.android.mynews.R;

public class BusinessFragment extends Fragment {

    public static BusinessFragment newInstance() {
        return (new BusinessFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_business, container, false);
        final FragmentActivity fragmentActivity = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity));
        recyclerView.setAdapter(new MyAdapter());
        return view;
    }
}