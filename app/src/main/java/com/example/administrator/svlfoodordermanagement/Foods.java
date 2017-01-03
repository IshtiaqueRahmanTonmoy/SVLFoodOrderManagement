package com.example.administrator.svlfoodordermanagement;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/20/2016.
 */

public class Foods {
    String foodname,foodrate,foodid,quantity,prices;
    private List<String> ratelist = new ArrayList<>();
    private List<String> qtylist;

    public Foods(String foodid, String foodname, String foodrate) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.foodrate = foodrate;

    }

    public Foods(String foodrate) {
        this.foodrate = foodrate;

        //qtylist = new ArrayList<>();

        savetoList(foodrate);
        //ratelist.add(foodrate);
        //Log.d("ratelist",ratelist.toString());
        //qtylist.add(quantity);
    }

    public Foods(String foodrate, String quantity) {
        this.foodrate = foodrate;
        this.quantity = quantity;
    }


    private void savetoList(String foodrate) {
        ratelist.add(foodrate);
    }

    public Foods(List<Foods> list) {

    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    Foods(String foodid, String foodname, String foodrate, String quantity)
    {
        this.foodid = foodid;
        this.foodname = foodname;
        this.foodrate = foodrate;
        this.quantity = quantity;
    }

    Foods(String foodid, String foodname, String prices,String quantity, String foodrate)
    {
        this.foodid = foodid;
        this.foodname = foodname;
        this.prices = prices;
        this.quantity = quantity;
        this.foodrate = foodrate;
    }

    public List<String> getRateList() {
        return ratelist;
    }

    public List<String> getQuantityList() {
        return qtylist;
    }


    public String getValueAtIndex(int index ) { return ratelist.get( index ); }

    String getNumber(int pos)
    {
        return ratelist.get(pos);
    }

    public String getFoodname() {
        return foodname;
    }

    public String toString() {
        return "Foodrate.:" + this.foodrate + "quantity:" + this.quantity;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodrate() {
        return foodrate;
    }

    public void setFoodrate(String foodrate) {
        this.foodrate = foodrate;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }
}
