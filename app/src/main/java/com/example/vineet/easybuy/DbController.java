package com.example.vineet.easybuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin3 on 20-02-2018.
 */

public class DbController extends SQLiteOpenHelper {
    SQLiteDatabase db;
    Context context;

    public DbController(Context context)
    {

        super(context,"EasyBuy", null,2);
        this.context = context;
        // db = this.getWritableDatabase();
        //context.deleteDatabase("Esy.db");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String table1="create table if not exists table1(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DropLocation TEXT,PickupLocation TEXT,Content TEXT,valueOfcontent TEXT,Instruction TEXT,Charges TEXT)";
        sqLiteDatabase.execSQL(table1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updatetable1(String id, String DropLocation,String PickupLocation,String Content,String valueOfcontent,
                             String Instruction,String Charges,String Amount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("DropLocation",DropLocation);
        values.put("PickupLocation",PickupLocation);
        values.put("Content",Content);
        values.put("valueOfcontent",valueOfcontent);
        values.put("Instruction",Instruction);
        values.put("Charges",Charges);

        db.update("table1", values,"id="+id, null);

        db.close();

    }


    public boolean inserttable1(String DropLocation,String PickupLocation,String Content,String valueOfcontent,
                                String Instruction,String Charges) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("DropLocation",DropLocation);
            values.put("PickupLocation",PickupLocation);
            values.put("Content",Content);
            values.put("valueOfcontent",valueOfcontent);
            values.put("Instruction",Instruction);
            values.put("Charges",Charges);


            long insert = db.insert("table1", null, values);

            db.close();

            return  true;

        }catch (Exception e){
            return  false;
        }

    }






    public boolean deleteFromtable1(String id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from table1 where id='"+id+"'");

            return true;
        }catch (SQLException e){
            return  false;
        }

        //db.execSQL("delete from "+tablename);
    }


    public void deletetable1()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from table1");
    }




}
