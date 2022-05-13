package com.example.task61d.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.task61d.model.Order;
import com.example.task61d.model.Truck;
import com.example.task61d.model.User;
import com.example.task61d.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create a table with given name and columns
        //users table
        String CREATE_USER_TABLE =
                "CREATE TABLE " + Util.USERS_TABLE_NAME
                        + "(" + Util.USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " //automatically add new id to every new entry
                        + Util.USERS_USERNAME + " TEXT NOT NULL UNIQUE,"
                        + Util.USERS_PASSWORD + " TEXT,"
                        + Util.USERS_FULL_NAME + " TEXT,"
                        + Util.USERS_PHONE_NO + " TEXT)";

        //orders table
        String CREATE_ORDER_TABLE =
                "CREATE TABLE " + Util.ORDERS_TABLE_NAME
                        + "(" + Util.ORDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " //automatically add new id to every new entry
                        + Util.ORDERS_SENDER_ID + " INTEGER NOT NULL,"
                        + Util.ORDERS_RECEIVER_ID + " INTEGER NOT NULL,"
                        + Util.ORDERS_DATE + " TEXT,"
                        + Util.ORDERS_TIME + " TEXT,"
                        + Util.ORDERS_LOC + " TEXT,"
                        + Util.ORDERS_GOOD_TYPE + " TEXT,"
                        + Util.ORDERS_WEIGHT + " INTEGER,"
                        + Util.ORDERS_HEIGHT + " INTEGER,"
                        + Util.ORDERS_WIDTH + " INTEGER,"
                        + Util.ORDERS_LENGTH + " INTEGER,"
                        + Util.ORDERS_TRUCK_TYPE + " TEXT)";

        //trucks table
        String CREATE_TRUCK_TABLE =
                "CREATE TABLE " + Util.TRUCKS_TABLE_NAME
                        + "(" + Util.TRUCKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " //automatically add new id to every new entry
                        + Util.TRUCKS_TYPE + " TEXT,"
                        + Util.TRUCKS_PHONE_NO + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TRUCK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + Util.USERS_TABLE_NAME;
        String DROP_ORDER_TABLE = "DROP TABLE IF EXISTS " + Util.ORDERS_TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        sqLiteDatabase.execSQL(DROP_ORDER_TABLE);

        onCreate(sqLiteDatabase);
    }

    //adds a new user to the database
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USERS_USERNAME, user.get_username());
        contentValues.put(Util.USERS_PASSWORD, user.get_password());
        contentValues.put(Util.USERS_FULL_NAME, user.get_fullName());
        contentValues.put(Util.USERS_PHONE_NO, user.get_phoneNo());

        //make sure user isn't already in database
        if (fetchUser(user.get_username())) {
            return -1;
        }

        //
        long newRowId = db.insert(Util.USERS_TABLE_NAME, null, contentValues);
        db.close();

        return newRowId;
    }

    //adds a new truck to the database
    public long insertTruck(Truck truck) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TRUCKS_TYPE, truck.get_truckType());
        contentValues.put(Util.TRUCKS_PHONE_NO, truck.get_phoneNo());
        Log.e("Truck type", truck.get_truckType());
        Log.e("Truck phone", truck.get_phoneNo());

        //
        long newRowId = db.insert(Util.TRUCKS_TABLE_NAME, null, contentValues);
        db.close();

        return newRowId;
    }

    //adds a new user to the database
    public long insertOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.ORDERS_SENDER_ID, order.get_senderID());
        contentValues.put(Util.ORDERS_RECEIVER_ID, order.get_receiverID());
        contentValues.put(Util.ORDERS_DATE, order.get_date());
        contentValues.put(Util.ORDERS_TIME, order.get_time());
        contentValues.put(Util.ORDERS_LOC, order.get_location());
        contentValues.put(Util.ORDERS_GOOD_TYPE, order.get_goodType());
        contentValues.put(Util.ORDERS_WEIGHT, order.get_weight());
        contentValues.put(Util.ORDERS_HEIGHT, order.get_height());
        contentValues.put(Util.ORDERS_LENGTH, order.get_length());
        contentValues.put(Util.ORDERS_WIDTH, order.get_width());
        contentValues.put(Util.ORDERS_TRUCK_TYPE, order.get_truckType());

        long newRowId = db.insert(Util.ORDERS_TABLE_NAME, null, contentValues);
        db.close();

        return newRowId;
    }

    //selects user with given username and password
    public boolean fetchUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numberOfRows;

        try {
            Cursor cursor = db.query(Util.USERS_TABLE_NAME, new String[]{Util.USERS_ID}, Util.USERS_USERNAME + "=? and " + Util.USERS_PASSWORD + "=?",
                    new String[]{username, password}, null, null, null);
            numberOfRows = cursor.getCount();
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
            numberOfRows = 0;
        }

        if (numberOfRows > 0) { return true; }
        else { return false; }
    }

    //fetches first user with given username
    public boolean fetchUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numberOfRows;

        //run query to search for user with given username
        Cursor cursor = db.query(Util.USERS_TABLE_NAME,
                new String[]{Util.USERS_ID},
                Util.USERS_USERNAME + "=?",
                new String[]{username}, null, null, null);
        numberOfRows = cursor.getCount();

        if (numberOfRows > 0) { return true; }
        else { return false; }
    }

    //returns the user with selected username
    public User getUser(String username) {
        User user;

        //search database for the entry that contains the given username
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.USERS_TABLE_NAME,
                new String[]{"*"},                      //make sure we select all columns
                Util.USERS_USERNAME + "=?",     //only select rows with given username
                new String[]{username}, null, null, null);

        //move to our first position
        if (cursor.moveToFirst()) {
            user = new User();
            user.set_userId(cursor.getInt(0));
            user.set_username(cursor.getString(1));
            user.set_password(cursor.getString(2));
            user.set_fullName(cursor.getString(3));
            user.set_phoneNo(cursor.getString(4));
            return user;
        }

        //didn't get an entry, so return null
        return null;
    }

    //returns user with selected id
    public User getUser(int userID) {
        User user;

        //search database for the entry that contains the given username
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.USERS_TABLE_NAME,
                new String[]{"*"},                      //make sure we select all columns
                Util.USERS_ID + "=?",     //only select rows with given username
                new String[]{String.valueOf(userID)}, null, null, null);

        //move to our first position
        if (cursor.moveToFirst()) {
            user = new User();
            user.set_userId(cursor.getInt(0));
            user.set_username(cursor.getString(1));
            user.set_password(cursor.getString(2));
            user.set_fullName(cursor.getString(3));
            user.set_phoneNo(cursor.getString(4));
            return user;
        }

        //didn't get an entry, so return null
        return null;
    }

    //checks if order with given sender id exists
    public boolean fetchOrder(int senderID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numberOfRows;

        //run query to search for user with given username
        Cursor cursor = db.query(Util.ORDERS_TABLE_NAME,
                new String[]{Util.ORDERS_ID},
                Util.ORDERS_SENDER_ID + "=?",
                new String[]{String.valueOf(senderID)}, null, null, null);
        numberOfRows = cursor.getCount();

        if (numberOfRows > 0) { return true; }
        else { return false; }
    }

    //returns the user with selected username
    public Order getOrder(int orderID) {
        Order order;

        //search database for the entry that contains the given username
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.ORDERS_TABLE_NAME,
                new String[]{"*"},                      //make sure we select all columns
                Util.ORDERS_ID + "=?",     //only select rows with given username
                new String[]{String.valueOf(orderID)}, null, null, null);

        //move to our first position
        if (cursor.moveToFirst()) {
            order = new Order();
            order.set_orderID(cursor.getInt(0));
            order.set_senderID(cursor.getInt(1));
            order.set_receiverID(cursor.getInt(2));
            order.set_date(cursor.getString(3));
            order.set_time(cursor.getString(4));
            order.set_location(cursor.getString(5));
            order.set_goodType(cursor.getString(6));
            order.set_weight(cursor.getString(7));
            order.set_height(cursor.getString(8));
            order.set_width(cursor.getString(9));
            order.set_length(cursor.getString(10));
            order.set_truckType(cursor.getString(11));
            return order;
        }

        //didn't get an entry, so return null
        return null;
    }

    //returns a list of all users in database
    public List<Order> fetchAllOrdersFrom(int senderID) {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //define query in string format, then run that query
        Cursor cursor = db.query(Util.ORDERS_TABLE_NAME,
                new String[]{"*"},                      //make sure we select all columns
                Util.ORDERS_SENDER_ID + "=?",     //only select rows with given username
                new String[]{String.valueOf(senderID)}, null, null, null);
        Log.e("Sending from Home", "Attempting to add orders");

        //starts from first row in database, each iteration cycles to next row until hitting last
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.set_orderID(cursor.getInt(0));
                order.set_senderID(cursor.getInt(1));
                order.set_receiverID(cursor.getInt(2));
                order.set_date(cursor.getString(3));
                order.set_time(cursor.getString(4));
                order.set_location(cursor.getString(5));
                order.set_goodType(cursor.getString(6));
                order.set_weight(cursor.getString(7));
                order.set_height(cursor.getString(8));
                order.set_width(cursor.getString(9));
                order.set_length(cursor.getString(10));
                order.set_truckType(cursor.getString(11));

                orderList.add(order);
            }while (cursor.moveToNext());
        }
        else { Log.e("Order Fail", "Failed to add orers"); }

        //filled our list, so return it
        return orderList;
    }

    //returns a list of all orders in database
    public List<Order> fetchAllOrders() {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //define query in string format, then run that query
        String selectAll = "SELECT * FROM " + Util.ORDERS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);
        Log.e("Sending from Home", "Attempting to add orders");

        //starts from first row in database, each iteration cycles to next row until hitting last
        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                Order order = new Order();
                order.set_orderID(cursor.getInt(0));
                order.set_senderID(cursor.getInt(1));
                order.set_receiverID(cursor.getInt(2));
                order.set_date(cursor.getString(3));
                order.set_time(cursor.getString(4));
                order.set_location(cursor.getString(5));
                order.set_goodType(cursor.getString(6));
                order.set_weight(cursor.getString(7));
                order.set_height(cursor.getString(8));
                order.set_width(cursor.getString(9));
                order.set_length(cursor.getString(10));
                order.set_truckType(cursor.getString(11));

                orderList.add(order);
                Log.e("Order ID", String.valueOf(order.get_orderID()));
                Log.e("Sender ID", String.valueOf(order.get_senderID()));
                Log.e("receiver ID", String.valueOf(order.get_receiverID()));
                Log.e("Order ID", String.valueOf(orderList.get(i).get_orderID()));
                Log.e("Sender ID", String.valueOf(orderList.get(i).get_senderID()));
                Log.e("receiver ID", String.valueOf(orderList.get(i).get_receiverID()));
                i++;
            }while (cursor.moveToNext());
        }
        else { Log.e("Order Fail", "Failed to add orers"); }

        //filled our list, so return it
        return orderList;
    }

    //returns a list of all users in database
    public List<User> fetchAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //define query in string format, then run that query
        String selectAll = "SELECT * FROM " + Util.USERS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //starts from first row in database, each iteration cycles to next row until hitting last
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.set_userId(cursor.getInt(0));
                user.set_username(cursor.getString(1));
                user.set_password(cursor.getString(2));
                user.set_fullName(cursor.getString(3));
                user.set_phoneNo(cursor.getString(4));

                userList.add(user);
            }while (cursor.moveToNext());
        }

        //filled our list, so return it
        return userList;
    }

    //returns a list of all trucks in database
    public List<Truck> fetchAllTrucks() {
        List<Truck> truckList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //define query in string format, then run that query
        String selectAll = "SELECT * FROM " + Util.TRUCKS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //starts from first row in database, each iteration cycles to next row until hitting last
        if (cursor.moveToFirst()) {
            do {
                Truck truck = new Truck();
                truck.set_truckID(cursor.getInt(0));
                truck.set_truckType(cursor.getString(1));
                truck.set_phoneNo(cursor.getString(2));

                truckList.add(truck);
            }while (cursor.moveToNext());
            return truckList;
        }

        //no trucks added, so return null
        return null;
    }
}