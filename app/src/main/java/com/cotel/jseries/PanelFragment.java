package com.cotel.jseries;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PanelFragment extends Fragment {

    private List<Serie> series;

    public PanelFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File file = new File(getActivity().getFilesDir() + "data.db");
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            this.series = (ArrayList<Serie>) ois.readObject();
            ois.close();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Base de datos vacía",
                    Toast.LENGTH_LONG).show();
            this.series = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.panel_fragment, container, false);


        GridView imagenes = (GridView) view.findViewById(R.id.gridView);
        imagenes.setAdapter(new ImageAdapter(view.getContext()));
        imagenes.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(), "pic" + (i + 1) + " selected",
                        Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton newSerie = (FloatingActionButton) view.findViewById(R.id.newSerie);
        newSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newSerieIntent = new Intent(getActivity(), NewSerieView.class);
                PanelFragment.this.startActivityForResult(newSerieIntent, 1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                SerieRetriever aux = new SerieRetriever(data.getExtras().getString("addedSerie"));
                aux.start();
                try {
                    aux.join();
                    boolean exists = false;
                    for(Serie serie : series) {
                        if(serie.equals(aux.serie)) {
                            exists = true;
                        }
                    }
                    if(!exists) {
                        series.add(aux.serie);
                        rellenarArchivo();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if(resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    private void rellenarArchivo() {
        try {
            File fileSeries = new File(getActivity().getFilesDir() + "data.db");
            FileOutputStream fos = new FileOutputStream(fileSeries);
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(fos);
                oos.writeObject(series);
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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



