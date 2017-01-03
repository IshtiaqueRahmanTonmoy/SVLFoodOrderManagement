package com.example.administrator.svlfoodordermanagement;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 10/27/2016.
 */
public class CustomListViewAdapter extends ArrayAdapter<Foods> {

    Context context;
    ViewHolder holder = null;
    static HashMap<String, String> map = new HashMap<>();
    View row = null;
    List<Foods> items;
    //HashMap<String,Integers> positiveNumbers = new HashMap<String,Integers>();
    Integer count=0;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<Foods> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
    }

    /*private view holder class*/
    private class ViewHolder {

        TextView txtid, txtname, txtrate, quantity;
        ImageView listimage;
        public String uniqueKey;
    }


    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Foods getItem(int position) {
        return items.get(position);
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