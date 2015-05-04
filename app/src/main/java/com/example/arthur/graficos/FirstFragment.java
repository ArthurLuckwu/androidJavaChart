package com.example.arthur.graficos;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private LinearLayout baseProgressBar;
    private BarChart chart;

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    public FirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grafico1, null);

        baseProgressBar = (LinearLayout) rootView.findViewById(R.id.baseProgressBar);

        chart = (BarChart) rootView.findViewById(R.id.chart);

        new JsonAsync().execute();

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }

    public class JsonAsync extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String response = makeRequest("http://teste.acessetecnologia.com.br:3000/teste/all");

            Log.i(null, "doInBackgroud");

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            baseProgressBar.setVisibility(View.INVISIBLE);

            Log.i(null, "onPostExecute 1");


            try {
                JSONArray json = new JSONArray(response);
                ArrayList<String> bebida = new ArrayList<String>();
                ArrayList<Integer> consumo = new ArrayList<Integer>();

                for (int i = 0; i < json.length(); i++) {
//                Log.i(null, json.getJSONObject(i).getString("bebida"));
                    bebida.add(json.getJSONObject(i).getString("bebida"));
                    consumo.add(json.getJSONObject(i).getInt("consumo"));
                }

                BarData data = new BarData(bebida, getDataSet(consumo));
                chart.setData(data);
                chart.setDescription("My Chart");
                chart.animateXY(2000, 2000);
                chart.invalidate();

                Log.i(null, "onPostExecute 2");

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(null, "DEU ERRO");
            }

        }

        private String makeRequest(String urlAddress) {
            HttpURLConnection con = null;
            URL url = null;
            String response = null;
            try {
                url = new URL(urlAddress);
                con = (HttpURLConnection) url.openConnection();
                response = readStream(con.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
//        Log.i(null, response);
            return response;
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder builder = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return builder.toString();
        }

        private ArrayList<BarDataSet> getDataSet(ArrayList<Integer> consumo) {

            ArrayList<BarDataSet> dataSet = null;

            ArrayList<BarEntry> valueSet = new ArrayList<>();
            for (int i = 0; i < consumo.size(); i++) {
                BarEntry b = new BarEntry(consumo.get(i), i);
                valueSet.add(b);
            }

            BarDataSet dados = new BarDataSet(valueSet, "Consumo");
            dados.setColors(ColorTemplate.COLORFUL_COLORS);

            dataSet = new ArrayList<>();
            dataSet.add(dados);

            return dataSet;
        }
    }
}
