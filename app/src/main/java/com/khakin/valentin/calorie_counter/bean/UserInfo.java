package com.khakin.valentin.calorie_counter.bean;

public class UserInfo {
    int id;
    String sex, birthday, height, weight, activity;

    public UserInfo(){

    }

    public UserInfo(String _sex, String _birthday, String _height, String _weight, String _activity){
        this.sex = _sex;
        this.birthday = _birthday;
        this.height = _height;
        this.weight = _weight;
        this.activity = _activity;
    }

    public UserInfo(int _id, String _sex, String _birthday, String _height, String _weight, String _activity){
        this.id = _id;
        this.sex = _sex;
        this.birthday = _birthday;
        this.height = _height;
        this.weight = _weight;
        this.activity = _activity;
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
}
