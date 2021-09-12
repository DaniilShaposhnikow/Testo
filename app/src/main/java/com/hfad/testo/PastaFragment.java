package com.hfad.testo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class PastaFragment extends Fragment {
    private DBHelper db;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View pizzaRecycler = inflater.inflate(R.layout.pasta_fragment, container, false);
        db=new DBHelper(getActivity());
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(db.selectAll("PASTA"));
        rv=pizzaRecycler.findViewById(R.id.pasta_recycler);
        rv.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        
        adapter.setListener(new CaptionedImagesAdapter.Listener(){
            @Override
            public void onClick(int position)
            {
                Intent intent = new Intent(getActivity(), PastaDetailActivity.class);
                intent.putExtra(PastaDetailActivity.EXTRA_PASTA_ID, position);
                getActivity().startActivity(intent);
            }
        });
        return pizzaRecycler;

    }
}