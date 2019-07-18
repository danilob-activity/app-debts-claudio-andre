package com.example.danilo.appdebts.database;

/**
 * Created by aluno on 26/06/19.
 */

public class ScriptDll {

    public static String createTableCategory(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE categoria (");
        sql.append(" id INTEGER NOT NULL PRIMARY KEY,");
        sql.append(" tipo TEXT NOT NULL");
        sql.append(");");
        return sql.toString();
    }

    public  static  String createTableDebts(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE dividas (");
        sql.append("id integer PRIMARY KEY,");
        sql.append(" cod_cat integer NOT NULL,");
        sql.append("valor Real not NULL,");
        sql.append("descricao text not NULL,");
        sql.append("data_vencimento date NOT NULL,");
        sql.append("data_pagamento date,");
        sql.append("FOREIGN KEY (cod_cat) REFERENCES categoria(id)");
        sql.append(");");
        return sql.toString();
    }

    public static String  getCategories(){
        return "select * from categories";
    }

    public  static  String getCategory(){
        return "Select * from categoria where id=?";
    }

    public static String getCategoryName(){
        return "select * from categoria where tipo=?";
    }

    public static String getDebtsByCategory(){
        return "select * from dividas order by cod_cat asc";
    }

    public static String getDebts(){
        return "select * from dividas d, categorias c" +
                "where d.cod_cat = c.id" +
                "order by cod_cat asc";
    }

    
}
