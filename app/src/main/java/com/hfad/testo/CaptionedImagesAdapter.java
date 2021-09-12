package com.hfad.testo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>
{
    private ArrayList<Pizza> list;
    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    public CaptionedImagesAdapter(ArrayList<Pizza> l)
    {
       list=l;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
       // Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), list.get(position).getImageResourceId());
        //imageView.setImageDrawable(drawable);
        Bitmap bitmap = BitmapFactory.decodeFile(list.get(position).getImageResourceId().getPath(),null);
        imageView.setImageBitmap(bitmap);
        imageView.setContentDescription(list.get(position).getName());
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(list.get(position).getName());
        cardView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            if (listener != null)
                                            {
                                                listener.onClick(position);
                                            }
                                        }
                                    });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public CardView cardView;
        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView=itemView;
        }
    }

    public void setListener(Listener listener)
    {        this.listener = listener;    }


}
