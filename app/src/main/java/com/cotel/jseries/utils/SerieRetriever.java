package com.cotel.jseries.utils;

import android.os.AsyncTask;

import com.cotel.jseries.models.Serie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by Cotel on 02/03/2016.
 */
public class SerieRetriever extends Thread{

    private String id;
    public Serie serie;

    public SerieRetriever(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        this.serie = new Serie(this.id);
    }


}
