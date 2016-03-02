package com.cotel.jseries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cotel.jseries.models.Serie;
import com.cotel.jseries.utils.DownloadImageTask;
import com.cotel.jseries.utils.UrlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Serie> series;

    public MainActivity() {
        series = new ArrayList<>();
        UrlReader serieRetriever1 = new UrlReader("tt2193021");
        UrlReader serieRetriever2 = new UrlReader("tt1856010");
        serieRetriever1.start();
        serieRetriever2.start();
        try {
            serieRetriever1.join();
            serieRetriever2.join();
            series.add(serieRetriever1.serie);
            series.add(serieRetriever2.serie);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView imagenes = (GridView) findViewById(R.id.gridView);
        imagenes.setAdapter(new ImageAdapter(this));
        imagenes.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "pic" + (i + 1) + " selected",
                        Toast.LENGTH_LONG).show();
            }
        });
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
            ImageView poster = new ImageView(context);
            poster.setLayoutParams(new GridView.LayoutParams(300, 370));
            poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
            poster.setPadding(5, 5, 5, 5);
            new DownloadImageTask(poster).execute(series.get(position).getUrlImagen());
            return poster;
        }
    }

}



