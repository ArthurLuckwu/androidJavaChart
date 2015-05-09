package com.example.arthur.graficos;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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

public class SecondFragment extends Fragment {


    private LinearLayout baseProgressBar;
    private PieChart chart;

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

    public SecondFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grafico2, null);

        int colors[] = {Color.parseColor("#DCDEE0"),Color.parseColor("#466A80"),Color.parseColor("#0078CA"),Color.parseColor("#5BC2E7"),Color.parseColor("#99E4FF")};

        chart = (PieChart) rootView.findViewById(R.id.piechart);
        baseProgressBar = (LinearLayout) rootView.findViewById(R.id.baseProgressBar);

        new JsonAsync().execute();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(3);
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
            int colors[] = {Color.parseColor("#DCDEE0"),Color.parseColor("#466A80"),Color.parseColor("#0078CA"),Color.parseColor("#5BC2E7"),Color.parseColor("#99E4FF")};

            try{
                JSONArray json = new JSONArray(response);
                ArrayList<String> bebida = new ArrayList<String>();
                ArrayList<Integer> consumo = new ArrayList<Integer>();

                for(int i=0;i<json.length();i++){
//                Log.i(null, json.getJSONObject(i).getString("bebida"));
                    bebida.add(json.getJSONObject(i).getString("bebida"));
                    consumo.add(json.getJSONObject(i).getInt("consumo"));
                }


                ArrayList<Entry> entries = new ArrayList<>();
                for(int i=0;i< consumo.size() ;i++){
                    Entry e = new Entry(consumo.get(i), i);
                    entries.add(e);
                }

                chart.setHoleColorTransparent(true);
                chart.setHoleRadius(60f);
                chart.setDrawCenterText(true);
                chart.setDrawHoleEnabled(true);
                chart.setRotationAngle(0);
                chart.setRotationEnabled(true);
                chart.setCenterText("Consumo de Bebidas");
                chart.invalidate();

                PieDataSet dataSet = new PieDataSet(entries, "Consumo");
                dataSet.setSliceSpace(3f);
                dataSet.setColors(colors);

                PieData data = new PieData(bebida, dataSet);
                chart.setData(data);

            }catch (JSONException e) {
                e.printStackTrace ();
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

    }

}
