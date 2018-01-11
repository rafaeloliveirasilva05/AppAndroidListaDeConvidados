package com.example.rafael.appandroidlistadeconvidados.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.example.rafael.appandroidlistadeconvidados.DbGateway;
import com.example.rafael.appandroidlistadeconvidados.dominio.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 26/09/2017.
 */

public class ClienteDao {

    private DbGateway gw;
    private Context ctx;

    public ClienteDao(Context ctx){
        gw = DbGateway.getInstance(ctx);
        this.ctx = ctx;
    }

    public void inserir(Cliente cliente){

        ContentValues contentValues =  new ContentValues();

        contentValues.put("MESA"    , cliente.mesa);
        contentValues.put("CONVIDADO" , cliente.convidado);
        contentValues.put("CONFIRMACAO" , cliente.confirmacao);
        contentValues.put("NOMESOBRENOME" , cliente.nomeSobrenome);
        contentValues.put("COMPARECEUFESTA" , cliente.compareceuFesta);

        gw.getDataBase().insertOrThrow("CLIENTE",null,contentValues);
    }

    public List<Cliente> buscarTodos(){

        List<Cliente> clientes = new ArrayList<Cliente>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO,MESA,CONVIDADO,CONFIRMACAO,NOMESOBRENOME,COMPARECEUFESTA");
        sql.append("    FROM CLIENTE");

        Cursor resultado = gw.getDataBase().rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();

            do {
                Cliente cli  =  new Cliente();
                cli.condigo       = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                cli.mesa          = resultado.getString(resultado.getColumnIndexOrThrow("MESA"));
                cli.convidado     = resultado.getString(resultado.getColumnIndexOrThrow("CONVIDADO"));
                cli.confirmacao   = resultado.getString(resultado.getColumnIndexOrThrow("CONFIRMACAO"));
                cli.nomeSobrenome = resultado.getString(resultado.getColumnIndexOrThrow("NOMESOBRENOME"));
                cli.compareceuFesta = resultado.getString(resultado.getColumnIndexOrThrow("COMPARECEUFESTA"));

                clientes.add(cli);

            }while (resultado.moveToNext());
        }
        return clientes;
    }

    public void update(int codigo){

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE CLIENTE SET COMPARECEUFESTA = '1' WHERE CODIGO = " + codigo);

        gw.getDataBase().execSQL(String.valueOf(sql));
    }

    public List<Cliente> buscarAlguns(String mesa){

        List<Cliente> clientes = new ArrayList<Cliente>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *");
        sql.append(" FROM CLIENTE");
        sql.append(" WHERE MESA = " + mesa);


        Cursor resultado = gw.getDataBase().rawQuery(sql.toString(),null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();

            do {
                Cliente cli  =  new Cliente();
                cli.condigo       = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                cli.mesa          = resultado.getString(resultado.getColumnIndexOrThrow("MESA"));
                cli.convidado     = resultado.getString(resultado.getColumnIndexOrThrow("CONVIDADO"));
                cli.confirmacao   = resultado.getString(resultado.getColumnIndexOrThrow("CONFIRMACAO"));
                cli.nomeSobrenome = resultado.getString(resultado.getColumnIndexOrThrow("NOMESOBRENOME"));
                cli.compareceuFesta = resultado.getString(resultado.getColumnIndexOrThrow("COMPARECEUFESTA"));

                clientes.add(cli);

            }while (resultado.moveToNext());
        }
        return clientes;
    }

    public void deletarTabela(){
        gw.getDataBase().execSQL("DELETE FROM CLIENTE");
        //gw.getDataBase().close();
    }

    public boolean checaBancoVazio(){

        Cursor cursor = gw.getDataBase().rawQuery("SELECT EXISTS (SELECT 1 FROM CLIENTE)", null);
        cursor.moveToFirst();

        if(cursor.getInt(0) == 0){
            return true;
        }
        else {
            return false;
        }
    }
}
