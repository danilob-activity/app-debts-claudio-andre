package com.example.danilo.appdebts.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.database.ScriptDll;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 27/06/19.
 */

public class DebtsDAO {
    private SQLiteDatabase mConnection;
    public DebtsDAO(SQLiteDatabase connection){
        mConnection = connection;
    }

    public void insert(Debts deb){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cod_cat", deb.getCod_cat());
        contentValues.put("descricao", deb.getDescricao());
        contentValues.put( "valor",deb.getValor());
        contentValues.put("data_pagamento", deb.getPaymentDate());
        contentValues.put("data_vencimento", deb.getExpirationDate());
        mConnection.insertOrThrow( "dividas",null, contentValues);
        Log.d("DebtsDAO","Inserção realizada com sucesso!");
    }

    public void remove(long id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        mConnection.delete("dividas","id = ?",params);
    }

    public void alter(Debts deb){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cod_cat", deb.getCod_cat());
        contentValues.put( "descricao",deb.getDescricao());
        contentValues.put("valor", deb.getValor());
        contentValues.put("data_pagamento", deb.getPaymentDate());
        contentValues.put("data_vencimento",deb.getExpirationDate());
        String[] params = new String[1];
        params[0] = String.valueOf(deb.getMid());
        mConnection.update("dividas",contentValues, "id = ?",params);
    }

    public List<Debts> listCategories() {
        List<Debts> debts = new ArrayList<Debts>();
        Cursor result = mConnection.rawQuery(ScriptDll.getCategories(), null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            do {
                Debts deb = new Debts();
                deb.setMid(result.getInt(result.getColumnIndexOrThrow("id")));
                deb.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
                debts.add(deb);
                Log.d("DebtsDAO", "Listando: " + deb.getMid() + " - " + deb.getCod_cat() + "-" + deb.getDescricao() + "-" + deb.getValor() + "-" + deb.getPaymentDate() + "-" + deb.getExpirationDate());
            } while (result.moveToNext());
            result.close();
        }
        return debts;
    }

    public Debts getDebts(long id){
        Debts deb = new Debts();
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = mConnection.rawQuery(ScriptDll.getCategory(), params);
        if(result.getCount()>0){
            result.moveToFirst();
            deb.setMid(result.getInt(result.getColumnIndexOrThrow("id")));
            deb.setCod_cat(result.getLong(result.getColumnIndexOrThrow("cod_cat")));
            deb.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
            deb.setValor(result.getFloat(result.getColumnIndexOrThrow("valor")));
            deb.setPaymentDate(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
            deb.setExpirationDate(result.getString(result.getColumnIndexOrThrow("data_vencimento")));
            result.close();
            return deb;
        }
        return null;}
}
