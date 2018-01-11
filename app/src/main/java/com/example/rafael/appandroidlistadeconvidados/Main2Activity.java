package com.example.rafael.appandroidlistadeconvidados;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rafael.appandroidlistadeconvidados.dominio.entidades.Cliente;
import com.example.rafael.appandroidlistadeconvidados.dominio.repositorio.ClienteDao;

import java.util.Collections;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private List<Cliente> clientes;
    private AdapterRecycler2View adapterRecycler2View;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
        toolbar.setTitle("Lista de Casamento");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String mesa = intent.getStringExtra("mesa");

        ClienteDao clienteDao = new ClienteDao(this);
        clientes = clienteDao.buscarAlguns(mesa);

        Collections.sort(clientes);


        recyclerView = findViewById(R.id.recycler2);

        adapterRecycler2View = new AdapterRecycler2View(clientes, this,2);
        recyclerView.setAdapter(adapterRecycler2View);

        LinearLayoutManager layout = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(layout);

        adapterRecycler2View.updateList();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }

}
