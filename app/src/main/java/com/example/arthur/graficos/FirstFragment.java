package com.example.arthur.graficos;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    public FirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grafico1, null);
//            this.startActivity(new Intent(this.getActivity(), Grafico1.class));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String response = makeRequest("http://192.168.0.101:3000/teste/all");

        try{
            JSONArray json = new JSONArray(response);
            ArrayList<String> bebida = new ArrayList<String>();
            ArrayList<Integer> consumo = new ArrayList<Integer>();

            for(int i=0;i<json.length();i++){
                Log.i(null, json.getJSONObject(i).getString("bebida"));
                bebida.add(json.getJSONObject(i).getString("bebida"));
                consumo.add(json.getJSONObject(i).getInt("consumo"));
            }

            BarChart chart = (BarChart) rootView.findViewById(R.id.chart);

            BarData data = new BarData(bebida, getDataSet(consumo));
            chart.setData(data);
            chart.setDescription("My Chart");
            chart.animateXY(2000, 2000);
            chart.invalidate();

        }catch (JSONException e) {
            e.printStackTrace ();
            Log.i(null, "DEU ERRO");
        }

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }


    private ArrayList<BarDataSet> getDataSet(ArrayList<Integer> consumo) {

        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarDataSet> dataSet = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);


        ArrayList<BarEntry> valueSet = new ArrayList<>();
        for(int i=0;i< consumo.size() ;i++){
            BarEntry b = new BarEntry(consumo.get(i), i);
            valueSet.add(b);
        }

        BarDataSet dados = new BarDataSet(valueSet,"Consumo");
        dados.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        dataSet = new ArrayList<>();
        dataSet.add(dados);

        return dataSet;
    }


    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
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
