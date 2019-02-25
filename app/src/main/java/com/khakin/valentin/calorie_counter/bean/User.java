package com.khakin.valentin.calorie_counter.bean;

public class User {
    int id;
    String sex, birthday, height, weight, activity;
    String p, f , c, total;

    public User(){

    }

    public User(int _id, String _sex, String _birthday, String _height, String _weight, String _activity, String _p, String _f, String _c, String _total){
        this.id = _id;
        this.sex = _sex;
        this.birthday = _birthday;
        this.height = _height;
        this.weight = _weight;
        this.activity = _activity;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
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
