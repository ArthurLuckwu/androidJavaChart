package com.sanmina.moblineprototype.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sanmina.moblineprototype.MainActivity;
import com.sanmina.moblineprototype.R;
import com.sanmina.moblineprototype.model.Linha;
import com.sanmina.moblineprototype.views.adapters.AdapterLinha;

import java.util.ArrayList;
import java.util.List;


public class FragmentListaLinha extends Fragment {
    private List<Linha> linhas;

    public static FragmentListaLinha newInstance() {
        FragmentListaLinha fragment = new FragmentListaLinha();
        return fragment;
    }

    public FragmentListaLinha() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_listalinhas, null);

        int idFab = ((MainActivity) getActivity()).getIdFab();
        int idPlanta = ((MainActivity) getActivity()).getIdPlanta();

//        Log.i(null, "idFAB NO FRAGMENT ->" + String.valueOf(((MainActivity) getActivity()).getIdFab()));

        linhas = new ArrayList<Linha>();
        linhas.add(new Linha(1, "Linha 1", "Status1", idPlanta, idFab));
        linhas.add(new Linha(2, "Linha 2", "Status2", idPlanta, idFab));


        AdapterLinha adapter = new AdapterLinha(getActivity(), linhas);

        ListView lista = (ListView) rootView.findViewById(R.id.lista_linhas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(), "Voce clicou em -> " + linhas.get(position).getDescricao(), Toast.LENGTH_SHORT).show();

                ((MainActivity) getActivity()).setNivel(2);

                final FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MainActivity.PlaceholderFragment.newInstance(position + 1))
                        .commit();

                if (linhas.get(position).getId() == 1) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, FragmentBarChart.newInstance())
                            .addToBackStack("FragmentListaLinha")
                            .commit();
                }
                if (linhas.get(position).getId() == 2) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, FragmentPieChart.newInstance())
                            .addToBackStack("FragmentListaLinha")
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
