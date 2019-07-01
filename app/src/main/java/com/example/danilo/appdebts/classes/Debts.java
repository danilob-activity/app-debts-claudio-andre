package com.example.danilo.appdebts.classes;

/**
 * Created by aluno on 27/06/19.
 */

public class Debts {
    private long mid;
    private Category cod_cat;
    private float valor;
    private String descricao;
    private String paymentDate;
    private  String expirationDate;


    public Debts(){

    }

    public Debts(Category cod_cat, float valor, String descricao, String paymentDate, String expirationDate) {
        this.cod_cat = cod_cat;
        this.valor = valor;
        this.descricao = descricao;
        this.paymentDate = paymentDate;
        this.expirationDate = expirationDate;
    }



    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public Category getCod_cat() {
        return cod_cat;
    }

    public void setCod_cat(Category cod_cat) {this.cod_cat = cod_cat;}

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }


}
