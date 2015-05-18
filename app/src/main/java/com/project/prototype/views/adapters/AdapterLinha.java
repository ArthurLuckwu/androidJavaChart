package com.project.prototype.views.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.prototype.model.Linha;
import com.project.prototype.R;

import java.util.List;

public class AdapterLinha extends BaseAdapter {

    private List<Linha> linhas;
    private Activity activity;

    public AdapterLinha(Activity activity, List<Linha> linhas){
        this.activity = activity;
        this.linhas = linhas;
    }

    @Override
    public int getCount() {
        return linhas.size();
    }

    @Override
    public Object getItem(int position) {
        return linhas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return linhas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.activity.getLayoutInflater().inflate(R.layout.view_linha, null);

        Linha linha = linhas.get(position);
        TextView descricao = (TextView) view.findViewById(R.id.descricao);
        TextView planta = (TextView) view.findViewById(R.id.planta);
        TextView fabrica = (TextView) view.findViewById(R.id.fabrica);
        TextView status = (TextView) view.findViewById(R.id.status);

        descricao.setText(linha.getDescricao());
        planta.setText(Integer.toString(linha.getPlanta()));
        fabrica.setText(Integer.toString(linha.getFabrica()));
        status.setText(linha.getStatus());

        return view;
    }
}
