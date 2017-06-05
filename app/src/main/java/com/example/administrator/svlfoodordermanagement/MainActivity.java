package com.example.administrator.svlfoodordermanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    EditText edtuserid,editpass;
    Button btnlogin;
    //ProgressBar pbbar;
    String userid,uPassword;
    Intent i;
    String value;
    private JSONParser jParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtuserid = (EditText) findViewById(R.id.email);
        editpass = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btn);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }
    private void attemptLogin() {

        userid = edtuserid.getText().toString();
        uPassword = editpass.getText().toString();


        Intent i = new Intent(MainActivity.this, TableOrder.class);
        startActivity(i);

        //UserLoginTask userLoginTask= new UserLoginTask(userid, uPassword);
        //userLoginTask.execute("", null);
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
