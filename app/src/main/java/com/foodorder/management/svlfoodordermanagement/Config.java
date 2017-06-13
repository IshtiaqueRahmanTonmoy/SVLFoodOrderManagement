package com.foodorder.management.svlfoodordermanagement;

/**
 * Created by TONMOYPC on 4/23/2017.
 */
public class Config {
    //JSON URL
    public static final String DATA_URL = "http://localhost/foodorder/get.php";

    //Tags used in the JSON String
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_RESULT = "alltable";
    public static final String TBL_ID = "TableID";
    public static final String TBL_NO = "TableNo";
    public static final String JSON_ARRAY = "result";

    //JSON array name   /foodorder/login.php

    public static final String LOGIN_URL = "http://ingtechbd.com/demo/foodorder/login.php";
    public static final String DATA_FETCH = "http://ingtechbd.com/demo/foodorder/getfoodbyid.php?FoodName=";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "LoginID";
    public static final String KEY_PASSWORD = "UserPassword";

    public static final String KEY_FOODID = "FoodID";
    public static final String KEY_FOODNAME = "FoodName";
    public static final String KEY_RATE = "Rate";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

}
