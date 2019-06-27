package com.example.danilo.appdebts.classes;

/**
 * Created by aluno on 27/06/19.
 */

public class Category {
    private long mid;
    private String mtipo;

    public Category() {

    }

    public Category(String mtipo) {
        this.mtipo = mtipo;
    }


    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public String getMtipo() {
        return mtipo;
    }

    public void setMtipo(String mtipo) {
        this.mtipo = mtipo;
    }





}
