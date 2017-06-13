package com.foodorder.management.svlfoodordermanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtuserid,editpass;
    Button btnlogin;
    //ProgressBar pbbar;
    String LoginID,UserPassword;
    Intent i;
    String value;
    Context context;
    private JSONParser jParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtuserid = (EditText) findViewById(R.id.email);
        //editpass = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btnJoin);

        btnlogin.setOnClickListener(this);

        /*
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        */
    }

    public void rl_main_onClick(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void attemptLogin() {

        LoginID = edtuserid.getText().toString();
        UserPassword = editpass.getText().toString();


        Intent i = new Intent(MainActivity.this, TableOrder.class);
        startActivity(i);

        //UserLoginTask userLoginTask= new UserLoginTask(userid, uPassword);
        //userLoginTask.execute("", null);
    }

    @Override
    public void onClick(View v) {
        login();

    }

    private void login() {
        LoginID = edtuserid.getText().toString();
       // UserPassword = editpass.getText().toString();


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, LoginID);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(MainActivity.this, TableOrder.class);
                            startActivity(intent);
                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(MainActivity.this, "Invalid mail address..", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_EMAIL, LoginID);
                //params.put(Config.KEY_PASSWORD, UserPassword);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public class UserLoginTask extends AsyncTask<String,String,String > {

        String z = "";
        Boolean isSuccess = false;
        String userid;
        String password;

        UserLoginTask(String userID, String uPassword) {
            userid = userID;
            //i.putExtra("usid",userid);
            password = uPassword;
        }

        @Override
        protected void onPreExecute() {
           // pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String success) {

            //String v = "admin";
            //pbbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, success, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Log.d("Success", z);
                //Intent i = new Intent(MainActivity.this, FoodOrder.class);
                i = new Intent(MainActivity.this, TableOrder.class);
                //Toast.makeText(MainActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                i.putExtra("key", value);
                startActivity(i);
                //finish();
            } else {
                Log.d("Error", z);
            }

        }
        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            if (userid.trim().equals("") || password.trim().equals("")) {
                z = "Please enter User Id and Password";
            } else {
                try {
                    Connection con;
                    ConnectionClass connectionClass = new ConnectionClass();
                    con = connectionClass.CONN();
                    //con = Connection.CONN;
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "SELECT top 1 UserID,LogInID,[UserPassword] FROM [tbl_UserInfo] Where LogInID='" + userid + "' and UserPassword='" + password + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            value = rs.getString("UserID");
                            z = "Login successfull";
                            isSuccess = true;
                        } else {
                            z = "Invalid Credentials";
                            isSuccess = false;
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }
    }
}
