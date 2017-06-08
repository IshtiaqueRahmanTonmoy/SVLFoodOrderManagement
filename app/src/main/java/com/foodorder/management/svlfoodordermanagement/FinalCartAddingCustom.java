package com.foodorder.management.svlfoodordermanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/20/2016.
 */
public class FinalCartAddingCustom extends ArrayAdapter<Foods> {

    Context context;
    List<Foods> list;
    List<Double> countlist;
    SystemClass sys;
    List<Foods> foods;
    //CountClass count;
    ArrayList<Foods> twoitems;
    DeleteListener dListener;

    public FinalCartAddingCustom(Context context, int resource, List<Foods> list) {

        super(context, resource, list);
        //intent = new Intent();
        twoitems = new ArrayList<>();
        //sqlite = new CartSavingSqlite(context);
        countlist = new ArrayList<>();
        //count = new CountClass();
        this.context = context;
        this.list = list;
        this.dListener = dListener;
        //this.mCallback = listener;
        foods = new ArrayList<Foods>();
        sys = new SystemClass();
        //count.setFoods(list.get);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Foods getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

          final ViewHolder viewholder;

          final Foods items = list.get(position);
          final int temp = position;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

              convertView = mInflater.inflate(R.layout.row_layout, null);
              viewholder = new ViewHolder();
              viewholder.foodid = (TextView) convertView.findViewById(R.id.foodid);
              viewholder.foodname = (TextView) convertView.findViewById(R.id.foodname);
              viewholder.foodrate = (TextView) convertView.findViewById(R.id.foodrate);
              viewholder.quantity = (TextView) convertView.findViewById(R.id.cart_product_quantity_tv);
              //viewholder.edit = (EditText) convertView.findViewById(R.id.price);
              viewholder.plusbutton = (ImageView) convertView.findViewById(R.id.cart_plus_img);
              viewholder.minusbutton = (ImageView) convertView.findViewById(R.id.cart_minus_img);
              //viewholder.delete = (Button) convertView.findViewById(R.id.deleteorder);

              convertView.setTag(viewholder);
          }

        else  {

            viewholder = (ViewHolder)convertView.getTag();
        }

        viewholder.foodid.setText(items.getFoodid());
        viewholder.foodname.setText(items.getFoodname());
        viewholder.foodrate.setText(items.getFoodrate());

        viewholder.plusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float total=0;
                int mValue = Integer.parseInt(viewholder.quantity.getText().toString());
                //double rate = Double.parseDouble(viewholder.foodrate.getText().toString());
                mValue++;

                 total = Float.parseFloat(list.get(position).getFoodrate())* mValue;

                viewholder.foodrate.setText(String.valueOf(total));
                viewholder.quantity.setText(String.valueOf(mValue));

                Intent intentplus = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intentplus.putExtra("id",viewholder.foodid.getText().toString());
                intentplus.putExtra("name",viewholder.foodname.getText().toString());
                intentplus.putExtra("price",items.getFoodrate());
                Log.d("ratess",items.getFoodrate());
                intentplus.putExtra("qty",viewholder.quantity.getText().toString());
                intentplus.putExtra("total",viewholder.foodrate.getText().toString());

                LocalBroadcastManager.getInstance(context).sendBroadcast(intentplus);
            }
        });

        viewholder.minusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double total=0;
                int mValue = Integer.parseInt(viewholder.quantity.getText().toString());
                //double rate = Double.parseDouble(viewholder.foodrate.getText().toString());
                mValue--;

                if (mValue < 0) {
                    System.out.println("not valid");
                } else
                {
                    total = Double.parseDouble(list.get(position).getFoodrate())* mValue;;
                    //grandtotal = grandtotal - total;
                    viewholder.foodrate.setText(String.valueOf(total));
                    viewholder.quantity.setText(String.valueOf(mValue));

                    Intent intentminus = new Intent("custom-message");
                    //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                    intentminus.putExtra("id",viewholder.foodid.getText().toString());
                    intentminus.putExtra("name",viewholder.foodname.getText().toString());
                    intentminus.putExtra("price",items.getFoodrate());
                    Log.d("ratess",items.getFoodrate());
                    intentminus.putExtra("qty",viewholder.quantity.getText().toString());
                    intentminus.putExtra("total",viewholder.foodrate.getText().toString());

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intentminus);
                    Log.d("viewholder",viewholder.foodrate.getText().toString());
                }
            }
        });

                return convertView;
    }

    private static class ViewHolder {
        public TextView foodname,foodid,foodrate,quantity;
        public ImageView plusbutton,minusbutton;
    }
}
