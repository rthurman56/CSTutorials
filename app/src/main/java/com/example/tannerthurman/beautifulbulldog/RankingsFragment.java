package com.example.tannerthurman.beautifulbulldog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingsFragment extends Fragment {

    private RecyclerView voteList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter voteAdapter;

    public RankingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rankings, container, false);
        voteList = (RecyclerView)view.findViewById(R.id.vote_list);
        layoutManager = new LinearLayoutManager(getContext());
        refreshList();

        return view;
    }

    public void onResume(){
        super.onResume();
        refreshList();
    }

    private void refreshList(){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Vote> votes = realm.where(Vote.class).findAll();
        VoteAdapter adapter = new VoteAdapter(getActivity(), votes);
        voteList.setAdapter(adapter);
    }

}
