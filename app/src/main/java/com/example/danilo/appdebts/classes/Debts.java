package com.example.danilo.appdebts.classes;

/**
 * Created by aluno on 27/06/19.
 */

public class Debts {
    private long mid;
    private float valor;
    private String descricao;
    private String paymentDate;
    private  String expirationDate;


    public Debts(){

    }

    public Debts(Category category, float valor, String paymentDate, String expirationDate) {
        this.valor = valor;
        this.paymentDate = paymentDate;
        this.expirationDate = expirationDate;
    }
}
