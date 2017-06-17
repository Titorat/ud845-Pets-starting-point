package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abdallah Amr on 6/11/2017.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME="shelter.dp";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PetContract.PetEntry.TABLE_NAME + " (" +
                    PetContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    PetContract.PetEntry.COLUMN_PET_NAME + " TEXT," +
                    PetContract.PetEntry.COLUMN_PET_BREED + " TEXT,"+
                    PetContract.PetEntry.COLUMN_PET_GENDER+ " INTEGER ,"+
                    PetContract.PetEntry.COLUMN_PET_WEIGHT+ " INTEGER )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetContract.PetEntry.TABLE_NAME;

    public PetDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
