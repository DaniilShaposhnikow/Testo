package com.hfad.testo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
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


public class PastaFragment extends Fragment
{
    static final String NANE_PASTA ="name";
    static final String URI_PASTA ="uri";
    private DBHelper db;
    private RecyclerView rv;
    private AppCompatButton add;
    private CaptionedImagesAdapter adapter;

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

        add = pizzaRecycler.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), PastaAddActivity.class);
                StartFoResult.launch(intent);
            }
        });
        return pizzaRecycler;
    }
    ActivityResultLauncher<Intent> StartFoResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== Activity.RESULT_OK){
                Intent intent = result.getData();
                String name = intent.getStringExtra(NANE_PASTA);
                Uri uri=Uri.parse(intent.getStringExtra(URI_PASTA));
                db.insertDrink(name,uri.toString(),"PASTA");
                UpdateAdapter();
            }
        }
    });

    private void UpdateAdapter()
    {
        adapter=null;
        adapter = new CaptionedImagesAdapter(db.selectAll("PASTA"));
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

}