package com.cotel.jseries.utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by DJCotelo on 03/03/2016.
 */
public class SearchRetriever extends Thread {

    public String res;
    private String url;

    public SearchRetriever(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        res = readUrl(url);
    }

    private String readUrl(String stringUrl) {
        BufferedReader reader = null;
        try {
            URL url = new URL(stringUrl);
            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                reader.close();
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
