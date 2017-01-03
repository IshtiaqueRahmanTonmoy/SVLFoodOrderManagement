package com.example.administrator.svlfoodordermanagement;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableOrder extends AppCompatActivity{

    Boolean isSuccess = false;
    String z = "";
    ListView lists;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_order);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        lists = (ListView) findViewById(R.id.list);

        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem =(lists.getItemAtPosition(position).toString());

                Intent intent = new Intent(TableOrder.this,FoodOrder.class);
                intent.putExtra("item",selectedItem);
                intent.putExtra("values",userid);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userid = extras.getString("key");
        }

        new getId().execute();
    }

    private class getId extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection con;
                        ConnectionClass connectionClass = new ConnectionClass();
                        con = connectionClass.CONN();
                        //con = Connection.CONN;
                        if (con == null) {
                            z = "Error in connection with SQL server";
                        } else {
                            String query = "select * from tbl_TableNo";
                            Log.d("outputvalue",query);
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(query);

                            List<String> l = new ArrayList<String>();
                            while (rs.next()) {
                                l.add(rs.getString("TableNo"));
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TableOrder.this,
                                    R.layout.list_food, R.id.lblcountryname, l);
                            lists.setAdapter(adapter);
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        z = "Exceptions";
                    }
                }
            });

            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}
