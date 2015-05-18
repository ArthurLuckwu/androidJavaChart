package com.project.prototype.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.project.prototype.MainActivity;
import com.project.prototype.R;
import com.project.prototype.network.Json;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;


public class FragmentBarChart extends Fragment {

    private LinearLayout baseProgressBar;
    private BarChart chart;

    public FragmentBarChart() {
    }

    public static FragmentBarChart newInstance() {
        FragmentBarChart fragment = new FragmentBarChart();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bar_chart, null);

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
            String response = Json.makeRequest("http://teste.acessetecnologia.com.br:3000/teste/all");

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            baseProgressBar.setVisibility(View.INVISIBLE);

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

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
