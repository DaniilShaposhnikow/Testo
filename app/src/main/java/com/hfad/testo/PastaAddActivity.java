package com.hfad.testo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PastaAddActivity extends AppCompatActivity
{
    private Button photo;
    private Button ok;
    private Button cancel;
    private EditText text;
    private ImageView image;
    private File photoFile;
    private String currentPhotoPath;
    private  Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasta_add);

        photo = findViewById(R.id.photo);
        ok = findViewById(R.id.ok);
        cancel = findViewById(R.id.cancel);
        text = findViewById(R.id.text);
        image = findViewById(R.id.image);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null)
                {
                    photoFile = null;
                    try
                    {
                        photoFile = createImageFile();
                    }
                    catch (IOException ex)
                    {

                    }
                    if (photoFile != null)
                    {
                        Uri photoURI = FileProvider.getUriForFile(PastaAddActivity.this,
                                "com.hfad.testo.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        StartFoResult.launch(intent);
                    }
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent data = new Intent();
                data.putExtra(PastaFragment.NANE_PASTA, text.getText().toString());
                data.putExtra(PastaFragment.URI_PASTA, uri.toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    ActivityResultLauncher<Intent> StartFoResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== Activity.RESULT_OK){
                uri = FileProvider.getUriForFile(PastaAddActivity.this,
                        "com.hfad.testo.fileprovider",
                        photoFile);
                revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                updatePhotoView();
            }
        }
    });

    private void updatePhotoView() {
        if (photoFile == null || !photoFile.exists())
        {
            image.setImageDrawable(null);
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath(),null);
            image.setImageBitmap(bitmap);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}