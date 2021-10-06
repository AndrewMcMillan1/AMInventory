package com.example.aminventory;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.GridView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Inventory table variables
    public static final String DB_NAME = "data.db";
    public static final String INVENTORY_TABLE = "inventory_table";
    public static final String COL_ID = "ID";
    public static final String COL_ITEM = "ITEM";
    public static final String COL_QTY = "QUANTITY";

    //User table variables
    public static final String USER_DB_NAME = "user.db";
    public static final String USER_TABLE = "user_table";
    public static final String USER_COL_ID = "ID";
    public static final String USER_COL_NAME = "NAME";
    public static final String USER_COL_PASSWORD = "PASSWORD";


    SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create tables
        db.execSQL("CREATE TABLE " + INVENTORY_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM + " TEXT, " + COL_QTY + " INT)");
        db.execSQL("CREATE TABLE " + USER_TABLE + " (" + USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_COL_NAME + " TEXT, " + USER_COL_PASSWORD + " TEXT)");

        /////
        /*
        private void createTables() throws SQLiteException {

            String userTable = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_COL_NAME + " TEXT, " + USER_COL_PASSWORD + " TEXT)");
            String inventoryTable = "CREATE TABLE IF NOT EXISTS " + INVENTORY_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM + " TEXT, " + COL_QTY + " INT)");

            db.execSQL(userTable);
            db.execSQL(inventoryTable);


        }
        */



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean isUserRegistered(String username, String password) {
        String sqlUser = "Select count(*) from " + USER_TABLE + " where username = " + username + " and password = " + password;
        SQLiteStatement statement = getReadableDatabase().compileStatement(sqlUser);
        long l = statement.simpleQueryForLong();
        statement.close();

        if (l == 1) {
            return true;
        }
        else{
            return false;
        }
    }

    /////
    //////
    //////
    // Validate existing users


    //Add a user to the user table
    public boolean addUser(UserModel userModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_COL_ID, userModel.getId());
        cv.put(USER_COL_NAME, userModel.getUsername());
        cv.put(USER_COL_PASSWORD, userModel.getPassword());


        long add = db.insert(USER_TABLE, null, cv);
        if (add == -1) {
            return false;
        }
        else {
            return true;
        }

    }


    /////////
    ///////

    public int idQuery(String username, String password) {
        String sqlUser = "Select USER_COL_ID from " + USER_TABLE + " where username = " + username + " and password = " + password;
        SQLiteStatement statement = getReadableDatabase().compileStatement(sqlUser);
        int id = Math.toIntExact(statement.simpleQueryForLong());
        statement.close();

        return id;

    }


         /////////
        //////////
        ///////////


    //Add an item to the item table
    public boolean addItem(ItemModel itemModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_ITEM, itemModel.getName());
        cv.put(COL_QTY, itemModel.getQuantity());

        long add = db.insert(INVENTORY_TABLE, null, cv);
        if (add == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    //Get items for grid display in home activity
    public List<ItemModel> getItems() {

        List<ItemModel> theItems = new ArrayList<>();
        String query = "SELECT * FROM " + INVENTORY_TABLE;

        ////////
        ////////
        ////////
        //String query = "SELECT * FROM " + INVENTORY_TABLE ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int itemID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                int itemQuantity = cursor.getInt(2);

                ItemModel itemAttr = new ItemModel(itemID, itemName, itemQuantity);
                theItems.add(itemAttr);

            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return theItems;
    }

    //Delete an item
    public void removeData2 (ItemModel itemModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INVENTORY_TABLE, COL_ID + " = ? ", new String[] {String.valueOf(itemModel.getId())});

    }


    //Edit an item
    public void updateItem(ItemModel itemModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ITEM, itemModel.getName());
        contentValues.put(COL_QTY, itemModel.getQuantity());

        db.update(INVENTORY_TABLE, contentValues, COL_ID + " = ? ", new String[] {String.valueOf(itemModel.getId())});




    }


}