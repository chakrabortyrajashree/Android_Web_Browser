package com.example.myfirstwebbrowser;
import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_name = "Rima.db";
    public static final String Table_name = "bookmark";
    public static final String col_id = "id";
   // public static final String col_name = "title";
    //public static final String col_marks = "marks";

    public static final String col_title = "title";
    public static final String col_Url = "url";

    private static final int VERSION = 3;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_name, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_name +" (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT , url TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Table_name);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String title,String url)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(col_id,id);
        cv.put(col_title,title);
        cv.put(col_Url,url);
        Long result = db.insert(Table_name,null,cv);
        if(result == -1 )
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public ArrayList<HashMap<String,String>> Showdata()
    {

        Log.d("ItemBooK","Show Bookmark.. Showdata");

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String,String>> userlist = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+Table_name,null);
        while(cursor.moveToNext())
        {
            HashMap<String,String> user = new HashMap<>();
            //user.put("id",cursor.getString(cursor.getColumnIndex(col_id)));
            user.put("title",cursor.getString(cursor.getColumnIndex(col_title)));
            user.put("url",cursor.getString(cursor.getColumnIndex(col_Url)));
            userlist.add(user);
        }
        return userlist;
    }

    /*
    public boolean update(String id,String name,String marks)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_id,id);
        cv.put(col_name,name);
        cv.put(col_marks,marks);
        db.update(Table_name,cv,"Id = ?",new String[] { id });
        return true;
    }*/

    public Integer delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_name,"Id = ?",new String[] {id});
    }

    public void alter()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE NAME = ' "+Table_name+" ' "));
    }
}