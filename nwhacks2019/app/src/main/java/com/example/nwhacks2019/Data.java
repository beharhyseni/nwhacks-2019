package com.example.nwhacks2019;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Data implements Serializable {

    private static volatile Data data_instance;
    private JSONObject results;
    private String text;

    public String getTheQuery() {
        return theQuery;
    }

    public void setTheQuery(String theQuery) {
        this.theQuery = theQuery;
    }

    private String theQuery;

    private List<String> names = new ArrayList<String>();
    private List<String> prices = new ArrayList<String>();
    private List<String> stores = new ArrayList<String>();
    private List<String> images = new ArrayList<String>();


    public void setTextView(String s){
        this.text = s;
    }
    private Data() {
        if (data_instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Data getInstance() {
        if (data_instance == null) { //if there is no instance available... create new one
            synchronized (Data.class) {
                if (data_instance == null) data_instance = new Data();
            }
        }

        return data_instance;
    }
    public String getTextView(){
        return this.text;
    }

    public void setResults(JSONObject results){
        this.results = results;
    }

    public void setData(){
        try{
            Iterator<String> keys = this.results.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                String name = this.results.getJSONObject(key).getString("name");
                String price = this.results.getJSONObject(key).getString("price");
                String store = this.results.getJSONObject(key).getString("store");
                String image = this.results.getJSONObject(key).getString("image");
                names.add(name);
                prices.add('$' + price);
                stores.add(store);
                images.add(image);
            }
        }catch(Exception e){}
    }

    public String[] getNames(){
        return names.toArray(new String[0]);
    }
    public String[] getPrices(){
        return prices.toArray(new String[0]);
    }
    public String[] getStores(){
        return stores.toArray(new String[0]);

    }
    public String[] getImages(){
        return images.toArray(new String[0]);
    }
}