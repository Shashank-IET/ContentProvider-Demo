package com.cocrux.m.pocketsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shashank on 14/11/17.
 */

public class PSqlSqliteHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PSQL_DB";

    public static final String COL_ID = "_id";

    public static final String TABLE_T1 = "table1";
    public static final String T1_COL_NAME = "name";
    public static final String T1_COL_ROLL = "roll";
    public static final String T1_COL_AGGREGATE = "aggregate";

    public static final String TABLE_T2 = "table2";
    public static final String T2_COL_CLASS = "class";
    public static final String T2_COL_ROLL = "roll";
    public static final String T2_COL_ADDRESS = "address";


    public PSqlSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_T1 + " (" +
                COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                T1_COL_NAME + " TEXT NOT NULL, " +
                T1_COL_AGGREGATE + " TEXT NOT NULL, " +
                T1_COL_ROLL + " INTEGER NOT NULL )");

        db.execSQL("CREATE TABLE " + TABLE_T2 + " (" +
                COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT ," +
                T2_COL_CLASS + " TEXT NOT NULL, " +
                T2_COL_ADDRESS + " TEXT NOT NULL, " +
                T2_COL_ROLL + " INTEGER NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_T1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_T2);
    }
}
