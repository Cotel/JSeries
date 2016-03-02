package com.cotel.jseries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.cotel.jseries.models.Serie;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Serie> series;

    public MainActivity() {
        series = new ArrayList<>();
        series.add(new Serie("tt2193021"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView imagenes = (GridView) findViewById(R.id.gridView);
        imagenes.setAdapter(new ImageAdapter(this));
    }

    public class ImageAdapter extends BaseAdapter {

        Context context;

        public ImageAdapter(Context c) {
            this.context = c;
        }

        @Override
        public int getCount() {
            return series.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView poster = null;
            if(poster == null) {
                poster = new ImageView(context);
                poster.setLayoutParams(new GridView.LayoutParams(185, 185));
                poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
                poster.setPadding(5,5,5,5);
            } else {
                poster = (ImageView) convertView;
            }
            try {
                URL posterUrl = new URL(series.get(position).getUrlImagen());
                Bitmap mIcon_val = BitmapFactory.decodeStream(posterUrl.openConnection().getInputStream());
                poster.setImageBitmap(mIcon_val);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return poster;
        }
    }

}



