package com.khakin.valentin.calorie_counter.bean;

public class Product {
    private int id;
    private String name, p, f, c, ccal, weight;

    public Product(){

    }

    public Product(String _name, String _p, String _f, String _c, String _ccal, String _weight){
        name = _name;
        p = _p;
        f = _f;
        c = _c;
        ccal = _ccal;
        weight = _weight;
    }

    public Product(int _id, String _name, String _p, String _f, String _c, String _ccal, String _weight){
        id = _id;
        name = _name;
        p = _p;
        f = _f;
        c = _c;
        ccal = _ccal;
        weight = _weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getCcal() {
        return ccal;
    }

    public void setCcal(String ccal) {
        this.ccal = ccal;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
