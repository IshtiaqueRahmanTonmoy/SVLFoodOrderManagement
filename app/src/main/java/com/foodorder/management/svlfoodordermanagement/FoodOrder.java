package com.foodorder.management.svlfoodordermanagement;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FoodOrder extends AppCompatActivity {

    ListView listView;
    ResultSet rs;
    ConnectionClass connectionClass = new ConnectionClass();
    EditText edtSearch;
    Button buttonAdd;
    List<Foods> itemList;
    SystemClass sys;
    ArrayList<Foods> result = new ArrayList<>();
    String foodid,foodname,foodrate;
    String fid,fname,frate,quantity;
    String id,name,rate,qty;
    List<Foods> foods;
    String qnty="1";;
    String items,values;
    int i=0,x=0;
    List<Foods> cartList;
    private int mNotificationsCount = 0;
    private Button btnNotifCount;
    private JSONArray jsonarray;
    private ProgressDialog loading;
    FoodListCustomView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order);

        cartList = new ArrayList<Foods>();
        Intent intent = getIntent();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message-pass"));

        items = intent.getExtras().getString("item");
        values = intent.getExtras().getString("values");

        foods = new ArrayList<>();
        sys = new SystemClass();
        itemList = new ArrayList<Foods>();

        edtSearch=(EditText)findViewById(R.id.txtSearch);

        listView = (ListView) findViewById(R.id.listView);

        getData();



       /*
        String query = "SELECT [FoodID],[FoodName],[Rate] FROM [tbl_FoodList]";
        ResultSet rs;
        Connection conn;
        conn = connectionClass.CONN();

        try {

            Statement statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {

                final String foodid = rs.getString("FoodID");
                final String foodname = rs.getString("FoodName");
                String rate = rs.getString("Rate");

                itemList.add(new Foods(foodid,foodname,rate,qnty));
                //listView.setAdapter(new FoodListCustomView(context,itemList));
                CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                        R.layout.activity_food_order, itemList);
                listView.setAdapter(adapter);
                listView.setItemsCanFocus(true);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //sys.rowDeleted();
                        i = i + 1;
                        Foods clickedObj = (Foods)parent.getItemAtPosition(position);

                        fid = clickedObj.getFoodid().toString();
                        fname = clickedObj.getFoodname().toString();
                        frate = clickedObj.getFoodrate().toString();
                        quantity = clickedObj.getQuantity().toString();

                        foods.add(new Foods(fid,fname,frate,quantity));
                        sys.setFoods(foods);

                        new FetchCountTask().execute();
                        //Toast.makeText(FoodOrder.this, "Items added.."+i, Toast.LENGTH_SHORT).show();
                    }
                });
                //listView.setOnItemClickListener(new BaseAdapter.);
            }
        }

        catch (Exception ex)
        {
        }
        */
        //listView.setOnClickListener(new);
    }

    public void rl_main_onClick(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void getData() {
        final StringRequest stringRequest = new StringRequest("http://ingtechbd.com/demo/foodorder/getfoods.php",
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

                int FoodId = json.getInt("FoodID");
                fid = String.valueOf(FoodId);
                Log.d("fidvalue",fid);

                fname = json.getString("FoodName");
                Log.d("fname",fname);

                Double Rate = json.getDouble("Rate");
                rate = String.valueOf(Rate);
                //Toast.makeText(FoodOrder.this, ""+tableno, Toast.LENGTH_SHORT).show();
                //Adding the name of the student to array list
                itemList.add(new Foods(fid,fname,rate,qnty));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.activity_food_order, itemList);
        listView.setAdapter(adapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = edtSearch.getText().toString().toLowerCase(Locale.getDefault());
                //Toast.makeText(FoodOrder.this, ""+text, Toast.LENGTH_SHORT).show();
                //Log.d("out",text);
                adapter.filter(text);
                //SearchItem();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //sys.rowDeleted();
                i = i + 1;
                Foods clickedObj = (Foods)parent.getItemAtPosition(position);

                fid = clickedObj.getFoodid().toString();
                fname = clickedObj.getFoodname().toString();
                frate = clickedObj.getFoodrate().toString();
                quantity = clickedObj.getQuantity().toString();

                foods.add(new Foods(fid,fname,frate,quantity));
                sys.setFoods(foods);

                new FetchCountTask().execute();
                //Toast.makeText(FoodOrder.this, "Items added.."+i, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            sys.getFoods().clear();
            String carListAsString = intent.getStringExtra("listget");

            Gson gson = new Gson();
            Type type = new TypeToken<List<Foods>>(){}.getType();
            cartList = gson.fromJson(carListAsString, type);
            //Log.d("carlist",cartList.get(0.getFoodrate());

            updateNotificationsBadge(cartList.size());

            for(int i=0;i<cartList.size();i++){
                String id = cartList.get(i).getFoodid();
                String name = cartList.get(i).getFoodname();
                String rate = cartList.get(i).getFoodrate();
                String quantity = cartList.get(i).getQuantity();

                //Toast.makeText(FoodOrder.this, ""+quantity, Toast.LENGTH_SHORT).show();
                foods.add(new Foods(id,name,rate,quantity));
                sys.setFoods(foods);

                Log.d("passedid",name);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, mNotificationsCount);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_notifications:
                passvalues();
                break;
           }
        return true;

    }

    private void passvalues() {

        result.clear();
        int size = sys.getFoods().size();
        Log.d("size", String.valueOf(size));
        for(int i=0;i<size;i++){

            id = sys.getFoods().get(i).getFoodid();
            name = sys.getFoods().get(i).getFoodname();
            rate = sys.getFoods().get(i).getFoodrate();
            qty = sys.getFoods().get(i).getQuantity();

            result.add(i,new Foods(id,name,rate));
        }

        Intent intent = new Intent(FoodOrder.this, TableList.class);
        String listSerializedToJson = new Gson().toJson(result);
        intent.putExtra("listget", listSerializedToJson);
        intent.putExtra("valuepass",items);
        intent.putExtra("val",values);
        startActivity(intent);
    }

    public void SearchItem(){

        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String param = edtSearch.getText().toString().trim();
        String url = Config.DATA_FETCH+'"'+param+'"';

        //Log.d("value output",url);

        //String reqUrl = "http://ingtechbd.com/demo/foodorder/getfoodbyid.php?";
        //Uri.Builder builder = Uri.parse(reqUrl).buildUpon();
        //builder.appendQueryParameter("FoodName=", param);
        //String finalUri = builder.build().toString();


        //String url = "http://ingtechbd.com/demo/foodorder/getfoodbyid.php?FoodName=" + param +"";

        //Uri uri = Uri.parse("http://ingtechbd.com/demo/foodorder/getfoodbyid.php?FoodName=");
        //String paramValue = uri.getQueryParameter(param);

        Log.d("the value",url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(FoodOrder.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        /*
        List<Foods> itemsearchlist = new ArrayList<Foods>();
        Editable itemCode=edtSearch.getText();
        String query = "SELECT [FoodID],[FoodName],[Rate] FROM [tbl_FoodList] WHERE [FoodName] LIKE '%"+itemCode+"%' OR [FoodID] LIKE '%"+itemCode+"%'";
        ResultSet rs;
        Connection conn;
        conn = connectionClass.CONN();

        try {

            Statement statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                String foodid = rs.getString("FoodID");
                String foodname = rs.getString("FoodName");
                String rate = rs.getString("Rate");

                itemsearchlist.add(new Foods(foodid,foodname,rate));
                listView.setAdapter(new FoodListCustomView(FoodOrder.this,itemsearchlist));
            }
        } catch (Exception ex)
        {
        }
        */
    }

    private void showJSON(String response) {

        List<Foods> itemsearchlist = new ArrayList<Foods>();
        String foodid="";
        String foodname="";
        String rate = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int i=0;i<result.length();i++)
            {
                JSONObject c=result.getJSONObject(i);
                HashMap<String,String>map=new HashMap<String, String>();
                foodid = c.getString(Config.KEY_FOODID);
                foodname = c.getString(Config.KEY_FOODNAME);
                rate = c.getString(Config.KEY_RATE);

                //Toast.makeText(FoodOrder.this, "foodid"+foodid+"foodname"+foodname, Toast.LENGTH_SHORT).show();
                Log.d("all values",foodid+foodname+rate);

                itemsearchlist.add(new Foods(foodid,foodname,rate));
                listView.setAdapter(new FoodListCustomView(FoodOrder.this,itemsearchlist));

                 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         Toast.makeText(FoodOrder.this, "clicked", Toast.LENGTH_SHORT).show();
                     }
                 });
                /*
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //sys.rowDeleted();
                        x = x + 1;
                        Foods clickedObj = (Foods)parent.getItemAtPosition(position);

                        fid = clickedObj.getFoodid().toString();
                        fname = clickedObj.getFoodname().toString();
                        frate = clickedObj.getFoodrate().toString();
                        quantity = clickedObj.getQuantity().toString();

                        foods.add(new Foods(fid,fname,frate,quantity));
                        sys.setFoods(foods);

                        new FetchCountTask().execute();
                        //Toast.makeText(FoodOrder.this, "Items added.."+i, Toast.LENGTH_SHORT).show();
                    }
                });
                */


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;
        invalidateOptionsMenu();
    }

    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            mNotificationsCount++;
            return mNotificationsCount;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }
}
