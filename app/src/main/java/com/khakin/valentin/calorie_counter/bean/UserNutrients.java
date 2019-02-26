package com.khakin.valentin.calorie_counter.bean;

public class UserNutrients {
    private int id;
    private String p, f, c, total;

    public UserNutrients(){

    }

    public UserNutrients(String _p, String _f, String _c, String _total){
        this.p = _p;
        this.f = _f;
        this.c = _c;
        this.total = _total;
    }

    public UserNutrients(int _id, String _p, String _f, String _c, String _total){
        this.id = _id;
        this.p = _p;
        this.f = _f;
        this.c = _c;
        this.total = _total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
