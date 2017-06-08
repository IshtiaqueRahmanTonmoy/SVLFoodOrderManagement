package com.foodorder.management.svlfoodordermanagement;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 10/27/2016.
 */
public class CustomListViewAdapter extends ArrayAdapter<Foods> {

    Context context;
    ViewHolder holder = null;
    static HashMap<String, String> map = new HashMap<>();
    View row = null;
    List<Foods> list = null;
    private ArrayList<Foods> arraylist = new ArrayList<Foods>();
    //HashMap<String,Integers> positiveNumbers = new HashMap<String,Integers>();
    Integer count=0;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<Foods> list) {
        super(context, resourceId, list);
        this.context = context;
        this.list = list;
        this.arraylist = new ArrayList<Foods>();
        this.arraylist.addAll(list);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(arraylist);
        } else {
            for (Foods wp : arraylist) {
                if (wp.getFoodname().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    /*private view holder class*/
    private class ViewHolder {

        TextView txtid, txtname, txtrate, quantity;
        ImageView listimage;
        public String uniqueKey;
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

    public View getView(final int position, View convertView, ViewGroup parent) {

        Foods rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            row = mInflater.inflate(R.layout.custom_listview, null);
            holder = new ViewHolder();
            holder.txtid = (TextView) row.findViewById(R.id.foodid);
            holder.txtname = (TextView) row.findViewById(R.id.foodname);
            holder.txtrate = (TextView) row.findViewById(R.id.foodrate);
            holder.listimage = (ImageView) row.findViewById(R.id.list_image);
            holder.uniqueKey = String.valueOf(position);

            row.setTag(holder);
        } else {
            row = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtid.setText(rowItem.getFoodid());
        holder.txtname.setText(rowItem.getFoodname());
        holder.txtrate.setText(rowItem.getFoodrate());
        holder.listimage.setImageResource(R.mipmap.ic_launcher);
        //holder.quantity.setText(""+rowItem.getQuantity());
        return row;
    }
}