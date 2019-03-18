package com.khakin.valentin.calorie_counter.parse;

import com.khakin.valentin.calorie_counter.bean.UserInfo;
import org.xmlpull.v1.XmlPullParser;

public class UserParser {

    String _sex;
    String _birthday;
    String _height;
    String _weight;
    String _activity;

    public UserInfo parse(XmlPullParser xpp){
        boolean status = true;
        UserInfo userInfo = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("user".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            userInfo = new UserInfo();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("user".equalsIgnoreCase(tagName)){
                                inEntry = false;
                            } else if("sex".equalsIgnoreCase(tagName)){
                                _sex = textValue;
                                assert userInfo != null;
                                userInfo.setSex(textValue);
                            } else if("birthday".equalsIgnoreCase(tagName)){
                                _birthday = textValue;
                                assert userInfo != null;
                                userInfo.setBirthday(textValue);
                            } else if("height".equalsIgnoreCase(tagName)){
                                _height = textValue;
                                assert userInfo != null;
                                userInfo.setHeight(textValue);
                            } else if("weight".equalsIgnoreCase(tagName)){
                                _weight = textValue;
                                assert userInfo != null;
                                userInfo.setWeight(textValue);
                            } else if("activity".equalsIgnoreCase(tagName)){
                                _activity = textValue;
                                assert userInfo != null;
                                userInfo.setActivity(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }

        return userInfo;
    }
}
