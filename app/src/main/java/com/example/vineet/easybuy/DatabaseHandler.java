package com.example.vineet.easybuy;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "EasyBuy";
    private static final int DATABASE_VERSION =3;

    // Database Name
    private static final String DATABASE_NAME = "eastbuy.db";

    // Contacts table name
    private static final String TABLE_FORM_DATA = "formdatanew";

    // Contacts Table Columns names

    private static final String NAME = "name";
    private static final String Price = "price";
    private static final String COUNT = "count";




    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableeasybuy="create table if not exists formdatanew(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,price TEXT,count TEXT)";
        db.execSQL(tableeasybuy);


    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORM_DATA);

        // Create tables again
        onCreate(db);
    }


    // Adding new Form
    public String addNewForm(String text, String text1, String text2) {
        String val="false";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, text);
        values.put(Price, text1);
        values.put(COUNT, text2);

        // Inserting Row
        long rowInserted =   db.insert(TABLE_FORM_DATA, null, values);

        if(rowInserted != -1){
            val="true";
        }
        else
        {
            val="false";

        }
        db.close(); // Closing database connection
return val;
    }






    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FORM_DATA;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void updateNewtag(String count, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COUNT,count);
        //Log.e("Count",values+"");
        try {
           int upd =  db.update(TABLE_FORM_DATA, values,"id="+id, null);

           Log.e("upd", String.valueOf(upd));

        }catch (Exception e){
            Log.e("upd", "Error");
        }
        db.close();
    }


    public Cursor getData1(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FORM_DATA;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public void clearDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }



}
