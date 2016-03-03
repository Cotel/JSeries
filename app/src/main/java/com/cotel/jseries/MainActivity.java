package com.cotel.jseries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

import com.cotel.jseries.activities.NewSerieView;
import com.cotel.jseries.models.Serie;
import com.cotel.jseries.utils.DownloadImageTask;
import com.cotel.jseries.utils.SerieRetriever;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Serie> series;

    public MainActivity() {
        series = new ArrayList<>();
        SerieRetriever serieRetriever1 = new SerieRetriever("tt2193021");
        SerieRetriever serieRetriever2 = new SerieRetriever("tt1856010");
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

        FloatingActionButton newSerie = (FloatingActionButton) findViewById(R.id.newSerie);
        newSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newSerieIntent = new Intent(MainActivity.this, NewSerieView.class);
                MainActivity.this.startActivity(newSerieIntent);
            }
        });
    }

    public List<Serie> getSeries() {
        return series;
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



