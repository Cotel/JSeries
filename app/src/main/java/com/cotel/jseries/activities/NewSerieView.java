package com.cotel.jseries.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cotel.jseries.MainActivity;
import com.cotel.jseries.R;
import com.cotel.jseries.models.Serie;
import com.cotel.jseries.utils.DownloadImageTask;
import com.cotel.jseries.utils.SearchRetriever;
import com.cotel.jseries.utils.SerieRetriever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DJCotelo on 03/03/2016.
 */
public class NewSerieView extends AppCompatActivity {

    private static final String BASE = "https://www.omdbapi.com/?s=";

    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.new_serie_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText busqueda = (EditText) findViewById(R.id.editText);
        Button ok = (Button) findViewById(R.id.button);
        final GridView resultados = (GridView) findViewById(R.id.gridView2);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aBuscar = busqueda.getText().toString();
                aBuscar = aBuscar.toLowerCase().replace(' ', '+');
                SearchRetriever retriever = new SearchRetriever(BASE + aBuscar + "&type=series" + "&r=json");
                retriever.start();
                try {
                    retriever.join();
                    JSONObject resultado = new JSONObject(retriever.res);
                    if (resultado.getString("Response").equals("True")) {
                        JSONArray aux = resultado.getJSONArray("Search");
                        resultados.setAdapter(new ResultadosAdapter(NewSerieView.this, aux));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    public class ResultadosAdapter extends BaseAdapter {

        Context context;
        JSONArray resultados;

        public ResultadosAdapter(Context c, JSONArray res) {
            this.context = c;
            this.resultados = res;
        }

        @Override
        public int getCount() {
            return resultados.length();
        }

        @Override
        public Object getItem(int position) {
            try {
                return resultados.get(position);
            } catch (JSONException e) {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View listaResultados;
            if(convertView == null) {
                listaResultados = new View(context);
                listaResultados = inflater.inflate(R.layout.search_results, null);

                final int pos = position;
                ImageView poster = (ImageView) listaResultados.findViewById(R.id.resPoster);
                TextView title = (TextView) listaResultados.findViewById(R.id.resTitle);
                TextView year = (TextView) listaResultados.findViewById(R.id.resYear);

                try {
                    title.setText(resultados.getJSONObject(position).getString("Title"));
                    year.setText(resultados.getJSONObject(position).getString("Year"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                poster.setPadding(5, 5, 5, 5);
                try {
                    new DownloadImageTask(poster).execute(resultados.getJSONObject(position).getString("Poster"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                poster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            SerieRetriever retriever = new SerieRetriever(resultados.getJSONObject(pos).getString("imdbID"));
                            retriever.start();
                            retriever.join();
                            Serie toAdd = retriever.serie;
                            Intent goBack = new Intent();
                            goBack.putExtra("addedSerie", toAdd.getId());
                            setResult(Activity.RESULT_OK, goBack);          // Corregir fallo al pulsar imagen sin teclado
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                listaResultados = (View) convertView;
            }
            return listaResultados;
        }
    }
}
