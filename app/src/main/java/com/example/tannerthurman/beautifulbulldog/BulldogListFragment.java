package com.example.tannerthurman.beautifulbulldog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import java.util.zip.Inflater;
import java.util.ArrayList;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;
import android.content.Intent;
import java.io.Serializable;
import io.realm.RealmResults;

import io.realm.Realm;


public class BulldogListFragment extends Fragment {

    private RecyclerView bulldogList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter bulldogAdapter;

    public BulldogListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_bulldog_list, container, false);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Bulldog> bulldogs = realm.where(Bulldog.class).findAll();
        bulldogList = (RecyclerView)view.findViewById(R.id.bulldog_list);

        layoutManager = new LinearLayoutManager(getContext());
        bulldogList.setLayoutManager(layoutManager);

        final MainActivity mainActivity = (MainActivity) this.getActivity();

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bulldog bulldog = (Bulldog) bulldogs.get(position);
                Intent intent = new Intent(view.getContext(), BulldogActivity.class);
                intent.putExtra("username", mainActivity.user.getUsername());
                intent.putExtra("bulldog", bulldog.getId());
                startActivity(intent);

            }
        };

        bulldogAdapter = new BulldogAdapter(getContext(), bulldogs, listener);
        bulldogList.setAdapter(bulldogAdapter);

        return view;

    }

}
