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


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String response = makeRequest("http://teste.acessetecnologia.com.br:3000/teste/all");

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


            PieChart chart = (PieChart) rootView.findViewById(R.id.piechart);
            chart.setHoleColorTransparent(true);
            chart.setHoleRadius(60f);
            chart.setDrawCenterText(true);
            chart.setDrawHoleEnabled(true);
            chart.setRotationAngle(0);
            chart.setRotationEnabled(true);
            chart.setCenterText("Consumo de Bebidas");
            chart.invalidate();

//                ArrayList<Entry> entries = new ArrayList<Entry>();
//                entries.add(new Entry((float) 20.0, entries.size()-1));
//                entries.add(new Entry((float) 30.0, entries.size() - 1));
//                ArrayList<String> labels = new ArrayList<String>();
//                labels.add("Teste1");
//                labels.add("Teste2");

            PieDataSet dataSet = new PieDataSet(entries, "Consumo");
            dataSet.setSliceSpace(3f);
            dataSet.setColors(colors);

            PieData data = new PieData(bebida, dataSet);
            chart.setData(data);

        }catch (JSONException e) {
            e.printStackTrace ();
            Log.i(null, "DEU ERRO");
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(3);
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

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

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);
        return dataSets;
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
