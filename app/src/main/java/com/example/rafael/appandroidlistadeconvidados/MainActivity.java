package com.example.rafael.appandroidlistadeconvidados;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.rafael.appandroidlistadeconvidados.dominio.entidades.Cliente;
import com.example.rafael.appandroidlistadeconvidados.dominio.repositorio.ClienteDao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ClienteDao dao;
    private List<Cliente> ListaweatherSamples = new ArrayList<>();
    private AdapterRecyclerView adapterRecyclerView;
    private RecyclerView recyclerView;
    private TestClass testClass;
    private static final int READ_REQUEST_CODE = 42;
    static EditText editTex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean teste;

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
        toolbar.setTitle("Lista de Casamento");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler);

        dao = new ClienteDao(this);

        testClass = TestClass.getInstance();
        testClass.vem(dao.buscarTodos());

        teste = dao.checaBancoVazio();

        if (teste) {
            Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
            intent2.setType("text/csv/*");
            startActivityForResult(intent2, READ_REQUEST_CODE);

        } else {
            ListaweatherSamples = testClass.ListaweatherSamples;
            mostra(ListaweatherSamples);
        }

        editTex = (EditText) findViewById(R.id.txtsearch);
        editTex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                charSequence = charSequence.toString().toLowerCase();

                final List<Cliente> clienteList = new ArrayList<>();

                for(int j=0;j< ListaweatherSamples.size();j++){

                    final String text = ListaweatherSamples.get(j).nomeSobrenome.toLowerCase();

                    if(text.contains(charSequence)){
                        clienteList.add(ListaweatherSamples.get(j));
                    }
                }
                mostra(clienteList);
                adapterRecyclerView.updateList();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void mostra(List<Cliente> clientes) {

        adapterRecyclerView = new AdapterRecyclerView(clientes, this, 1);
        recyclerView.setAdapter(adapterRecyclerView);

        LinearLayoutManager layout = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(layout);
        String teste;

    }

    //Receber resultado
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        Uri uri;
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uri = resultData.getData();
                lerDadosDoArquivo(uri);
            }
        }
    }

    private void lerDadosDoArquivo(Uri uri) {
        Cliente cliente;
        InputStream is = null;

        try {
            is = getContentResolver().openInputStream(uri);
        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(";");

                cliente = new Cliente();
                cliente.mesa = tokens[0];

                if (tokens[1].length() > 0) {
                    cliente.convidado = tokens[1];

                }
                else {
                    cliente.convidado = Integer.toString(0);
                }

                if (tokens.length >= 3 && tokens[2].length() > 0) {
                    cliente.confirmacao = tokens[2];
                }
                else {
                    cliente.confirmacao = Integer.toString(0);
                }

                if(tokens.length >= 4  && tokens[3].length() > 0){
                    cliente.nomeSobrenome = tokens[3];
                }
                else {
                    cliente.nomeSobrenome = Integer.toString(0);
                }

                cliente.compareceuFesta = "0";

                dao.inserir(cliente);
            }
            testClass.vem(dao.buscarTodos());
            ListaweatherSamples = testClass.ListaweatherSamples;

            mostra(ListaweatherSamples);

            adapterRecyclerView.updateList();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {

            case R.id.action_settings:
                dao.deletarTabela();
                resolve();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void resolve() {
        Intent intent3 = new Intent(Intent.ACTION_GET_CONTENT);
        intent3.setType("text/csv/*");
        startActivityForResult(intent3, READ_REQUEST_CODE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //limpa o search
        editTex.getText().clear();
        adapterRecyclerView.updateList();

    }
}
