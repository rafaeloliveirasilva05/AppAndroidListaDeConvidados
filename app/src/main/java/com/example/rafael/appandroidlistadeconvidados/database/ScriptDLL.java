package com.example.rafael.appandroidlistadeconvidados.database;

/**
 * Created by Rafael on 24/09/2017.
 */

public class ScriptDLL {

    public static String getCreatTableCliente(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS CLIENTE( ");
        sql.append("    CODIGO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("    MESA    VARCHAR (10) NOT NULL DEFAULT(''),");
        sql.append("    CONVIDADO VARCHAR (10) NOT NULL DEFAULT(''),");
        sql.append("    CONFIRMACAO VARCHAR (10) NOT NULL DEFAULT(''),");
        sql.append("    NOMESOBRENOME VARCHAR (40) NOT NULL DEFAULT(''),");
        sql.append("    COMPARECEUFESTA VARCHAR (4) NOT NULL DEFAULT(''))");

        return sql.toString();
    }
}
