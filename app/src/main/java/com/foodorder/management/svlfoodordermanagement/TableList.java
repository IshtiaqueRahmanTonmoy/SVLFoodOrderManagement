package com.foodorder.management.svlfoodordermanagement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TableList extends AppCompatActivity {

    SystemClass sys;
    Context context;
    ListView list;
    String listitems;
    Button placeorder,total;
    FinalCartAddingCustom finalcartadd;
    double totals = 0;
    List<Foods> cartList;
    List<Foods> rowItems;
    //CountClass countcl;
    List<String> listval;
    TextView text;
    public static Context contextOfApplication;
    Foods foods;
    String id,name,stock,quantity,prices,totalss;
    List<Foods> lists;
    List<String> itemcheck;
    String TableNo;
    String Status = "1";
    String UnicID,BillCreateDate,UserID;
    Intent intent;
    String listSerializedToJson;
    ArrayList<Foods> resultpass = new ArrayList<>();
    TextView cartproduct;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        itemcheck = new ArrayList<String>();
        lists = new ArrayList<Foods>();
        //foods = new Foods();
        sys = new SystemClass();
        contextOfApplication = getApplicationContext();
        //countcl = new CountClass();
        list = (ListView) findViewById(R.id.list_view);
        placeorder = (Button) findViewById(R.id.button1);
        //total = (Button) findViewById(R.id.button2);
        rowItems = new ArrayList<Foods>();
        //text = (TextView) findViewById(R.id.textView);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        String carListAsString = getIntent().getStringExtra("listget");
        TableNo = getIntent().getStringExtra("valuepass");
        //Toast.makeText(TableList.this, ""+valuepass, Toast.LENGTH_SHORT).show();

        UserID = getIntent().getStringExtra("val");
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        UnicID = TableNo+String.valueOf(n);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        //System.out.println(dateFormat.format(date));

        BillCreateDate = dateFormat.format(date);
        //Toast.makeText(TableList.this,""+d,Toast.LENGTH_LONG).show();

        Gson gson = new Gson();
        Type type = new TypeToken<List<Foods>>(){}.getType();
        cartList = gson.fromJson(carListAsString, type);

        /*
        for(Foods f : cartList) {
          //Log.d("v",f.getQuantity()+f.getFoodname()+f.getFoodrate()+f.getFoodid());
            Toast.makeText(TableList.this, ""+f.getQuantity()+f.getFoodname()+f.getFoodrate()+f.getFoodid(), Toast.LENGTH_SHORT).show();
        }
        */

        finalcartadd = new FinalCartAddingCustom(this,R.layout.activity_table_list,cartList);
        list.setAdapter(finalcartadd);

        list.setItemsCanFocus(true);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getContext());
                alertbox.setCancelable(true);
                alertbox.setMessage("Are you sure you want to delete ?");
                alertbox.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(
                                    DialogInterface arg0, int arg1) {

                                cartList.remove(position);
                                finalcartadd.notifyDataSetChanged();
                                finalcartadd.notifyDataSetInvalidated();
                            }
                        });

                alertbox.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface arg0, int arg1) {

                            }
                        });

                alertbox.show();
                return false;
            }
        });

        finalcartadd.notifyDataSetChanged();

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TableList.this,stock+" "+quantity+""+id+""+name ,Toast.LENGTH_SHORT).show();
                Toast.makeText(TableList.this, "Your Selected Foods Has Been Ordered", Toast.LENGTH_SHORT).show();
                /*
                for(Foods s : lists){
                    String id = s.getFoodid().toString();
                    String name = s.getFoodname().toString();
                    String price = s.getPrices().toString();
                    Log.d("pr",price);
                    String qtys = s.getQuantity().toString();
                    Log.d("qty",qtys);
                    String tot = s.getFoodrate().toString();

                    //Insertdata insert = new Insertdata(id,name,price,qtys,tot);
                    //insert.execute("",null);
                    Inserttable(id,name,price,qtys,tot);
                    Log.d("idval",id);
                }
              */
            }
        });
    }

    private void Inserttable(final String ItemCode, final String Item, final String Price, final String Quantity, final String Total) {
        showpDialog();

        StringRequest request = new StringRequest(Request.Method.POST, "http://ingtechbd.com/demo/foodorder/insettblorder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("ItemCode", ItemCode);
                parameters.put("Item", Item);
                parameters.put("TableNo", TableNo);
                parameters.put("Price", Price);
                parameters.put("Quantity", Quantity);
                parameters.put("Total", Total);
                parameters.put("Status", Status);
                parameters.put("UnicID", UnicID);
                parameters.put("BillCreateDate", BillCreateDate);
                parameters.put("UserID", UserID);

                hidepDialog();

                return parameters;

            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        for(int i=0;i<cartList.size();i++){

            String pid = cartList.get(i).getFoodid();
            String pname = cartList.get(i).getFoodname();
            String prate = cartList.get(i).getFoodrate();
            String pqty = cartList.get(i).getQuantity();

            //Toast.makeText(TableList.this, ""+pqty, Toast.LENGTH_SHORT).show();
            //Log.d("pqty",pqty);
            //cartproduct.setText(pqty);
            resultpass.add(i,new Foods(pid,pname,prate,pqty));
        }

        Intent intentpass = new Intent("custom-message-pass");
        String listSerializedToJson = new Gson().toJson(resultpass);

        Log.d("listserialize",listSerializedToJson);
        intentpass.putExtra("listget", listSerializedToJson);
        //startActivity(intent);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intentpass);
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            prices = intent.getStringExtra("price");
            quantity = intent.getStringExtra("qty");
            totalss = intent.getStringExtra("total");

            Log.d("quant",quantity);
            lists.add(new Foods(id,name,prices,quantity,totalss));
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("finalcart", String.valueOf(finalcartadd.list));
        //outState.putParcelableArrayList(STATE_LIST, finalcartadd.list);
    }
}
