package com.example.arthur.graficos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arthur on 02/05/15.
 */
public class ListaLinhasActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaLinhas);

        List<Linha> linhas = new ArrayList<Linha>();
        linhas.add(new Linha(1, "Linha 1", "Status1"));
        linhas.add(new Linha(2, "Linha 2", "Status2"));
        linhas.add(new Linha(3, "Linha 3", "Status3"));

        LinhaAdapter adapter = new LinhaAdapter(this, linhas);

        ListView lista = (ListView) findViewById(R.id.lista_linhas);
        lista.setAdapter(adapter);



    }
}