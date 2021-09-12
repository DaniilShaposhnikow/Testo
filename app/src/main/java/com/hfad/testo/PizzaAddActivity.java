package com.hfad.testo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Session2Command;
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

public class PizzaAddActivity extends AppCompatActivity
{

    private Button ok;
    private Button can;
    private Button ph;
    private ImageView pht;
    private EditText addd;
    private String currentPhotoPath;
    private File photoFile;
    private  Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_add);

        ok = findViewById(R.id.Ok);
        can = findViewById(R.id.Cancel);
        ph = findViewById(R.id.Photo);
        pht = findViewById(R.id.Photos);
        addd = findViewById(R.id.Addd);

        ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(PizzaAddActivity.this,
                                "com.hfad.testo.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        StartFoResult.launch(intent);
                    }
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra(PizzaFragment.NANE_PIZZA, addd.getText().toString());
                data.putExtra(PizzaFragment.URI_PIZZA, uri.toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
    //Intent in call Camera

    ActivityResultLauncher<Intent> StartFoResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== Activity.RESULT_OK){
                uri = FileProvider.getUriForFile(PizzaAddActivity.this,
                        "com.hfad.testo.fileprovider",
                        photoFile);
                revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                updatePhotoView();
            }
        }
    });
    //Photo + exciz

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
////            Bundle extras = data.getExtras();
////            Bitmap imageBitmap = (Bitmap) extras.get("data");
////            pht.setImageBitmap(imageBitmap);
//            uri = FileProvider.getUriForFile(this,
//                    "com.hfad.testo.fileprovider",
//                    photoFile);
//            revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            updatePhotoView();
//        }
//    }

    private void updatePhotoView() {
        if (photoFile == null || !photoFile.exists()) {
            pht.setImageDrawable(null);
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath(),null);
            pht.setImageBitmap(bitmap);
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
    //Create a photo
}