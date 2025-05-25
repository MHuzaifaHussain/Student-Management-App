package com.example.studentmanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Database helper class for managing SQLite database operations
public class DataBaseHelper_1 extends SQLiteOpenHelper {

    // Static variable to define the name of the database
    public static final String databaseName = "SignLog.db";

    // Constructor for initializing the database helper
    public DataBaseHelper_1(@Nullable Context context) {
        super(context, "SignLog.db", null, 1); // Database name: SignLog.db, version: 1
    }

    // Method called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        // Create a table named "users" with "email" as primary key and "password" as a column
        MyDatabase.execSQL("create Table users(email TEXT primary key, password TEXT)");
    }

    // Method called when the database version changes (to upgrade the schema)
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        // Drop the existing "users" table if it exists
        MyDB.execSQL("drop Table if exists users");
    }

    // Method to insert user data (email and password) into the "users" table
    public Boolean insertData(String email, String password) {
        // Get a writable instance of the database
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        // Create ContentValues to hold the data to be inserted
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email); // Add email to the values
        contentValues.put("password", password); // Add password to the values

        // Insert data into the "users" table and get the result
        long result = MyDatabase.insert("users", null, contentValues);

        // Return true if insertion was successful, otherwise return false
        return result != -1;
    }

    // Method to check if an email already exists in the "users" table
    public Boolean checkEmail(String email) {
        // Get a writable instance of the database
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        // Query the "users" table to find a record with the given email
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});

        // If the cursor has data (email exists), return true; otherwise, return false
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
}
