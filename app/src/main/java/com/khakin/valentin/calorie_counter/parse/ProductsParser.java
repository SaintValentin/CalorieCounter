package com.khakin.valentin.calorie_counter.parse;

import com.khakin.valentin.calorie_counter.bean.Product;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class ProductsParser {
    private ArrayList<Product> products;

    public ProductsParser(){
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts(){
        return  products;
    }

    public boolean parse(XmlPullParser xpp){
        boolean status = true;
        Product currentProduct = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("product".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentProduct = new Product();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("product".equalsIgnoreCase(tagName)){
                                products.add(currentProduct);
                                inEntry = false;
                            } else if("name".equalsIgnoreCase(tagName)){
                                currentProduct.setName(textValue);
                                System.out.println(textValue);
                            } else if("p".equalsIgnoreCase(tagName)){
                                currentProduct.setP(textValue);
                            } else if("f".equalsIgnoreCase(tagName)){
                                currentProduct.setF(textValue);
                            } else if("c".equalsIgnoreCase(tagName)){
                                currentProduct.setC(textValue);
                            } else if("ccal".equalsIgnoreCase(tagName)){
                                currentProduct.setCcal(textValue);
                            } else if("weight".equalsIgnoreCase(tagName)){
                                currentProduct.setWeight(textValue);
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
        return  status;
    }
}
