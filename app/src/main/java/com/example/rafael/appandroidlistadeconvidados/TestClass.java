package com.example.rafael.appandroidlistadeconvidados;


import com.example.rafael.appandroidlistadeconvidados.dominio.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 06/01/18.
 */

public class TestClass {

    private static TestClass uniqueInstance;
    public List<Cliente> ListaweatherSamples = new ArrayList<>();

    private TestClass(){}

    public static synchronized TestClass getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new TestClass();
        }
        return uniqueInstance;
    }

    public void vem(List<Cliente> clientes){
        ListaweatherSamples = clientes;
    }

    public void apaga(int codigo){
        for (int i =0; i<ListaweatherSamples.size();i++){
            if(ListaweatherSamples.get(i).condigo == codigo){
                ListaweatherSamples.remove(i);
            }
        }
    }
    public void muda(int codigo){

        for (int i =0; i<ListaweatherSamples.size();i++){
            if(ListaweatherSamples.get(i).condigo == codigo){
                ListaweatherSamples.get(i).compareceuFesta = "1";
            }
        }
    }
}
