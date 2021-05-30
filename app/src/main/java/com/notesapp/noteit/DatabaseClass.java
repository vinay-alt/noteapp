package com.notesapp.noteit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseClass extends SQLiteOpenHelper {

    Context context;
    private static final String DatabaseName = "MyNotes";
    private static final int DatabaseVersion = 1;

    private static final String TableName = "Mynotes";
    private static final String ColumnId = "id";
    private static final String ColumnTitle = "title";
    private static final String ColumnDesc = "description";

    public DatabaseClass(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create Table " + TableName +
                " ("+  ColumnId +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ColumnTitle +" TEXT, "+
                ColumnDesc +" TEXT );";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TableName);
        onCreate(db);
    }

    void addNotes(String title, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColumnTitle, title);
        cv.put(ColumnDesc, desc);
        long resultvalue = db.insert(TableName, null, cv);
        if (resultvalue==-1) {
            Toast.makeText(context, "Data Not Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "Select * from "+ TableName;
        SQLiteDatabase database= this.getReadableDatabase();
        Cursor cursor = null;
        if (database!=null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAllNotes() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM "+ TableName;
        database.execSQL(query);
    }

    void updateNotesdata(String title, String desc, String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColumnTitle, title);
        cv.put(ColumnDesc, desc);
        long result = database.update(TableName, cv, "id=?", new String[]{id});
        if(result==-1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Notes Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSingleItem(String id) {
        SQLiteDatabase database = this.getWritableDatabase();

        long result = database.delete(TableName,"id=?", new String[]{id});
        if(result==-1){
            Toast.makeText(context, "Item Not Deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
        }
    }

}
