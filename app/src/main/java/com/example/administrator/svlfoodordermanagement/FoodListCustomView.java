package com.example.administrator.svlfoodordermanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/20/2016.
 */
public class FoodListCustomView extends BaseAdapter {

    Context context;
    List<Foods> list;
    SystemClass sys;
    //CartSavingSqlite sqlit
    Foods value;
    List<Foods> foods;
    Intent myIntent;
    int i;
    String id,name,rate;
    ArrayList<Foods> result = new ArrayList<>();

    public FoodListCustomView(Context context, List<Foods> list) {

        //sqlite = new CartSavingSqlite(context);

        this.context = context;
        this.list = list;
        foods = new ArrayList<Foods>();
        sys = new SystemClass();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewholder;
        if (convertView == null) {

            viewholder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);

            convertView.setClickable(true);
            convertView.setFocusable(true);

            //convertView = inflater.inflate(R.layout.bookdistributioncustomlayout, null);

            viewholder.foodid = (TextView) convertView.findViewById(R.id.foodid);
            viewholder.foodname = (TextView) convertView.findViewById(R.id.foodname);
            viewholder.foodrate = (TextView) convertView.findViewById(R.id.foodrate);
            //viewholder.plusbutton = (Button) convertView.findViewById(R.id.btnplus);
            //viewholder.plusbutton = (ImageView) convertView.findViewById(R.id.cart_plus_img);
            convertView.setTag(viewholder);
        }

        else  {
            viewholder = (ViewHolder)convertView.getTag();
        }

        Foods items = list.get(position);
        viewholder.foodid.setText(items.getFoodid());
        viewholder.foodname.setText(items.getFoodname());
        viewholder.foodrate.setText(items.getFoodrate());

        return convertView;
    }

    private static class ViewHolder {
        public TextView foodname,foodid,foodrate,quantity;
        public ImageView plusbutton,minusbutton;
    }
}
