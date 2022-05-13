package com.example.task61d.util;

public class Util {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "truck_app_db";

    //user database
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_ID = "user_id";
    public static final String USERS_FULL_NAME = "full_name";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_PHONE_NO = "phone_no";

    //truck database
    public static final String TRUCKS_TABLE_NAME = "trucks";
    public static final String TRUCKS_ID = "truck_id";
    public static final String TRUCKS_TYPE = "type";
    public static final String TRUCKS_PHONE_NO = "phone_no";

    //orders database
    public static final String ORDERS_TABLE_NAME = "orders";
    public static final String ORDERS_ID = "order_id";
    public static final String ORDERS_SENDER_ID = "sender_id";
    public static final String ORDERS_RECEIVER_ID = "receiver_id";
    public static final String ORDERS_DATE = "date";
    public static final String ORDERS_TIME = "time";
    public static final String ORDERS_LOC = "location";
    public static final String ORDERS_WEIGHT = "weight";
    public static final String ORDERS_LENGTH = "length";
    public static final String ORDERS_HEIGHT = "height";
    public static final String ORDERS_WIDTH = "width";
    public static final String ORDERS_GOOD_TYPE = "good_type";
    public static final String ORDERS_TRUCK_TYPE = "truck_type";
}
