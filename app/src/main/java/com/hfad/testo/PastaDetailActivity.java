package com.hfad.testo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PastaDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PASTA_ID = "pastaId";
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasta_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(this);
        int pastaId = (Integer)getIntent().getExtras().get(EXTRA_PASTA_ID);
        String pastaName = dbHelper.select(pastaId,"PASTA").getName();
        TextView textView = (TextView)findViewById(R.id.pasta_text);
        textView.setText(pastaName);

        //int pastaImage = Pasta.pastas[pastaId].getImageResourceId();
        /*Uri pastaImage = dbHelper.select(pastaId,"PASTA").getImageResourceId();
        ImageView imageView = (ImageView) findViewById(R.id.pasta_image);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, pastaImage));
        imageView.setContentDescription(pastaName);*/
    }
}