package com.example.android.myscannerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import static android.database.sqlite.SQLiteDatabase.openDatabase;


public class SignUpHelper extends SQLiteOpenHelper {
    // Database attributes
    public static final String DB_NAME = "Users";
    public static final int DB_VERSION = 1;
    public static SQLiteDatabase database;
    public static SQLiteOpenHelper dbHandler;

// Table attributes

    public static final String TABLE_ACCOUNT = "user_table";

    //Account Table
    public static final String EMAIL_ID = "emailId";
    public static final String PIN = "pin";

//    private static final String[] allColumns = {
//            EMAIL_ID,
//            PIN
//    };

    public SignUpHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        dbHandler=new SignUpHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //database=db;
        //database=dbHandler.getWritableDatabase();

        String AccountTable = "create table if not exists " + TABLE_ACCOUNT + " ( " + BaseColumns._ID + " integer primary key autoincrement, "
                + EMAIL_ID + " text not null, "

                + PIN + "text );";

        db.execSQL(AccountTable);
//        dbHandler.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        onCreate(db);

    }

    public static String retrieveData(String emailId) {
//        SQLiteDatabase db = this.getWritableDatabase();
      //  SQLiteDatabase db=openDatabase(DB_NAME, null, Context.MODE_PRIVATE);
        String pwd=null;

        SQLiteDatabase dataBase=dbHandler.getReadableDatabase();
        if (dataBase==null)
            return null;
        //ContentValues row=new ContentValues();
        Log.d("Retrieve data",emailId);
        Cursor cc=dataBase.rawQuery("SELECT pin from user_table where emailId=?",new String[]{emailId});
        //cc.moveToFirst();
        Log.d("ColumnIndex-PIN", String.valueOf(cc.getColumnIndex(PIN)));
        if(cc.moveToNext()){
            pwd=cc.getString(cc.getColumnIndex(PIN));
        }
       // Log.d("Retrieve Data",String.valueOf(row));
        cc.close();
        database.close();

        return pwd;


    }
}

/*public class SignUpHelper extends SQLiteOpenHelper {
    public static final int database_version=1;
    public static final String DB_NAME = "Users";
    public static final String TABLE_ACCOUNT = "user_table";
    public static final int DB_VERSION = 1;
    public static SQLiteDatabase database;
    public static SQLiteOpenHelper dbHandler;
    public static final String EMAIL_ID = "emailId";
    public static final String PIN = "pin";

    public String CREATE_QUERY =
            "CREATE TABLE " +
                    TABLE_ACCOUNT + " (" +
                    EMAIL_ID  + " TEXT," +
                    PIN  + " INTEGER );";


    public SignUpHelper(Context context) {
        super(context, DB_NAME, null, database_version);
        Log.d("DatabaseOperstions","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("DatabaseOperations","Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void putInformation(SQLiteOpenHelper dop, String name, String pass){
        SQLiteDatabase sq=dop.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(EMAIL_ID,name);
        cv.put(PIN,pass.toString());
        Long k=sq.insert(TABLE_ACCOUNT,null,cv);
        Log.d("Database operations","one raw inserted");
    }
    public void getInformation(SQLiteOpenHelper dop,String name,String pass){
        SQLiteDatabase sq=dop.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(EMAIL_ID,name);
        cv.put(PIN,pass.toString());
        Long k=sq.insert(TABLE_ACCOUNT,null,cv);
        Log.d("Database operations","one raw inserted");
    }
}*/










