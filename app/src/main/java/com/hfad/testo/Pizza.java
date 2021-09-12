package com.hfad.testo;

import android.net.Uri;

public class Pizza
{
    private long id;
    private String name;
//    private int imageResourceId;
    private Uri imageResourceId;
//    public static final Pizza[] pizzas = {new Pizza("Diavolo", R.drawable.diavolo),
//            new Pizza("Funghi", R.drawable.funghi)};

    public Pizza(long id, String name, Uri imageResourceId) {
        this.id = id;
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    private Pizza(String name, Uri imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public Uri getImageResourceId() {
        return imageResourceId;
    }

    public long getId() {
        return id;
    }
}
