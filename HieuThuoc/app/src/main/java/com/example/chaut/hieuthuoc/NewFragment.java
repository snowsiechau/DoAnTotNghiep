package com.example.chaut.hieuthuoc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chaut.hieuthuoc.RSS.Downloader;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment {

    final static String urlAddress="https://vnexpress.net/rss/suc-khoe.rss";

    public NewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new, container, false);

        final RecyclerView rv= view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        new Downloader(getActivity(),urlAddress,rv).execute();


        return view;
    }
}
