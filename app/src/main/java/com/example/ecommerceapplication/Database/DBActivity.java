package com.example.ecommerceapplication.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.ecommerceapplication.Entity.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DBActivity extends SQLiteAssetHelper {
    private static final String DBName = "CommerceDB.db";
    private static int version = 1;

    private SQLiteDatabase database;

    public DBActivity(Context context){
        super(context, DBName, null, version);
        database = context.openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null, null);
    }


    @SuppressLint("Range")
    public List<Order> GetCarts(){
        SQLiteDatabase Database = getReadableDatabase();
        SQLiteQueryBuilder QueryBuilder = new SQLiteQueryBuilder();

        String[] SQLSelect = {  "Id", "Name","Quantity", "Price" };
        String SQLTable = "OrderInfo";

        QueryBuilder.setTables(SQLTable);
        Cursor cursor = QueryBuilder.query(Database, SQLSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                result.add(new Order(cursor.getString(cursor.getColumnIndex("Id")),
                        cursor.getString(cursor.getColumnIndex("Name")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price"))
                        ));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void SaveToCart(Order order){
        SQLiteDatabase Database = getReadableDatabase();
        String SQLQuery = String.format("INSERT INTO OrderInfo(Id, Name, Quantity, Price) VALUES ('%s', '%s', '%s', '%s');",
                order.getId(),
                order.getName(),
                order.getQuantity(),
                order.getPrice());

        Database.execSQL(SQLQuery);
    }

    public void ClearCart(){
        SQLiteDatabase Database = getReadableDatabase();
        String SQLQuery = "DELETE FROM OrderInfo";
        Database.execSQL(SQLQuery);
    }
}
