package com.example.rafael.appandroidlistadeconvidados.dominio.entidades;

import android.support.annotation.NonNull;

/**
 * Created by Rafael on 24/09/2017.
 */

public class Cliente implements Comparable<Cliente> {

    public int condigo;
    public String mesa;
    public String convidado;
    public String confirmacao;
    public String nomeSobrenome;
    public String compareceuFesta;

    @Override
    public String toString() {
        return "Mesa: " + mesa + "\n" +
                "Convidado: " + convidado + "\n" +
                "Confirmacao: " + confirmacao + "\n"+
                "NomeSobrenome: " + nomeSobrenome + "\n"+
                "CompareceuFesta: " + compareceuFesta + "\n";
    }

    @Override
    public int compareTo(@NonNull Cliente cliente) {
        return nomeSobrenome.compareTo(cliente.nomeSobrenome);
    }
}
