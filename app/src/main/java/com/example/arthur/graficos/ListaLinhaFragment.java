package com.example.arthur.graficos;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.List;


public class ListaLinhaFragment extends Fragment {

    private LinearLayout baseProgressBar;
    private BarChart chart;

    private List<Linha> linhas;

    public static ListaLinhaFragment newInstance() {
        ListaLinhaFragment fragment = new ListaLinhaFragment();
        return fragment;
    }

    public ListaLinhaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listalinhas, null);

        int idFab = ((MainActivity) getActivity()).getIdFab();
        int idPlanta = ((MainActivity) getActivity()).getIdPlanta();

        Log.i(null, "idFAB NO FRAGMENT ->" + String.valueOf(((MainActivity) getActivity()).getIdFab()));

        linhas = new ArrayList<Linha>();
        linhas.add(new Linha(1, "Linha 1", "Status1", idPlanta, idFab));
        linhas.add(new Linha(2, "Linha 2", "Status2", idPlanta, idFab));

//        linhas.add(new Linha(3, "Linha 3", "Status3"));

        LinhaAdapter adapter = new LinhaAdapter(getActivity(), linhas);

        ListView lista = (ListView) rootView.findViewById(R.id.lista_linhas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(), "Voce clicou em -> " + linhas.get(position).getDescricao(), Toast.LENGTH_SHORT).show();

                final FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MainActivity.PlaceholderFragment.newInstance(position + 1))
                        .commit();

                if (linhas.get(position).getId() == 1) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, FirstFragment.newInstance())
                            .commit();
                }
                if (linhas.get(position).getId() == 2) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, SecondFragment.newInstance())
                            .commit();
                }
            }

        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }


}
