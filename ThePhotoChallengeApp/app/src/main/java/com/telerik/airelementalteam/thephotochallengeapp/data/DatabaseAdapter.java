package com.telerik.airelementalteam.thephotochallengeapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "challengeDatabase";
    private static final String TABLE_NAME = "NAMETABLE";
    private static final int DATABASE_VERSION = 1;
    private static final String UID = "_id";
    private static final String NAME = "Name";
    private static final String PASSWORD = "Password";
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +NAME+ " VARCHAR(255), " + PASSWORD + " VARCHAR(255));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
    private Context context;


    private SQLiteDatabase db = null;

    public DatabaseAdapter(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long insertData(String name, String password)
    {
        //db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseAdapter.NAME, name);
        contentValues.put(DatabaseAdapter.PASSWORD, password);
        long id = this.db.insert(DatabaseAdapter.TABLE_NAME, null, contentValues);
        return id;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public long insertName(String countryName)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, countryName);
        return this.db.insert(NAME, null, contentValues);
    }

    public void openDB()
    {
        if(this.db == null)
        {
            this.db = this.getWritableDatabase();
        }
    }

    public String[] getAllNames()
    {
        Cursor cursor = this.db.query(TABLE_NAME, new String[] {NAME}, null, null, null, null, null);

        if(cursor.getCount() >0)
        {
            String[] str = new String[cursor.getCount()];
            int i = 0;

            while (cursor.moveToNext())
            {
                str[i] = cursor.getString(cursor.getColumnIndex(NAME));
                i++;
            }
            return str;
        }
        else
        {
            return new String[] {};
        }
    }

    public void close() {
        this.db.close();
    }
}
