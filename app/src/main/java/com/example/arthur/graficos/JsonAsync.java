package com.example.arthur.graficos;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by arthur on 04/05/15.
 */
public class JsonAsync extends AsyncTask<String, Integer, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        baseProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String response = makeRequest("http://teste.acessetecnologia.com.br:3000/teste/all");

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
//        baseProgressBar.setVisibility(View.INVISIBLE);
//        tvEndereco.setText(s);
    }

    private String makeRequest(String urlAddress){
        HttpURLConnection con = null;
        URL url = null;
        String response = null;
        try{
            url = new URL(urlAddress);
            con = (HttpURLConnection) url.openConnection();
            response = readStream(con.getInputStream());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
//        Log.i(null, response);
        return response;
    }

    private String readStream ( InputStream in ) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = reader.readLine()) != null){
                builder.append(line+ "\n");
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            if(reader != null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}
