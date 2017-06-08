package com.foodorder.management.svlfoodordermanagement;

import android.app.ProgressDialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableOrder extends AppCompatActivity{

    Boolean isSuccess = false;
    String z = "";
    ListView lists;
    String userid;
    private ProgressDialog pDialog;
    private JSONParser jParser;
    private JSONArray jsonarray;
    private ArrayList<String> alist;
    private static String url = "http://localhost/foodorder/get.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_order);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        alist = new ArrayList<String>();
        lists = (ListView) findViewById(R.id.list);
        jParser=new JSONParser();

        //new DownloadJSON().execute();
        getData();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userid = extras.getString("key");
            //getData();
        }

        //new getId().execute();

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

    private void getData() {

        final StringRequest stringRequest = new StringRequest("http://ingtechbd.com/demo/foodorder/get.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            jsonarray = j.getJSONArray("alltable");
                            //Toast.makeText(TableOrder.this, ""+jsonarray, Toast.LENGTH_SHORT).show();
                            getStudents(jsonarray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getStudents(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                String tableno = json.getString("TableNo");
                //Toast.makeText(TableOrder.this, ""+tableno, Toast.LENGTH_SHORT).show();
                //Adding the name of the student to array list
                alist.add(json.getString(Config.TBL_NO));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TableOrder.this, R.layout.list_food, R.id.lblcountryname, alist);
        lists.setAdapter(adapter);

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
    }
}
