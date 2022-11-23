package com.example.ecommerceapplication.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.ecommerceapplication.data.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DBActivity extends SQLiteAssetHelper {
    private static final String DBName = "CommerceDB.db";

    public DBActivity(Context context){
        super(context, DBName, null, 1);
    }

    @SuppressLint("Range")
    public List<Order> getCartItems(){
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

    public void addItemToCart(Order order){
        SQLiteDatabase database = getReadableDatabase();

        String query = String.format("INSERT INTO OrderInfo(Id, Name, Quantity, Price) VALUES ('%s','%s','%s','%s');",
                order.getItemId(),
                order.getItemName(),
                order.getQuantity(),
                order.getPrice());
        database.execSQL(query);
    }
}
