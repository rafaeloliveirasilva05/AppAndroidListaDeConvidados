package com.example.rafael.appandroidlistadeconvidados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rafael.appandroidlistadeconvidados.database.DadosOpenHelper;

/**
 * Created by Rafael on 26/09/2017.
 */

public class DbGateway {

    private static DbGateway gw;
    private SQLiteDatabase db;

    private DbGateway(Context ctx){
        DadosOpenHelper helper = new DadosOpenHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public static DbGateway getInstance(Context ctx){
        if(gw == null)
            gw = new DbGateway(ctx);
        return gw;
    }

    public SQLiteDatabase getDataBase(){
        return this.db;
    }
}
