package com.example.administrator.svlfoodordermanagement;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/24/2016.
 */
class SystemClass implements DeleteListener{
    List<Foods> list = new ArrayList<Foods>();

    public SystemClass(String id,String name,String rate,String quantity){
        list.add(new Foods(id,name,rate,quantity));
    }

    public SystemClass() {

    }

    public List<Foods> getFoods() {
        return this.list;
    }

    public void setFoods(List<Foods> list) {
        this.list = list;
    }

    @Override
    public void rowDeleted(int pos) {
          list.remove(pos);
          //setFoods(list);
    }
}
