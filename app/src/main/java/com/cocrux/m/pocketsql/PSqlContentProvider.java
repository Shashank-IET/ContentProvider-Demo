package com.cocrux.m.pocketsql;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Shashank on 14/11/17.
 */

public class PSqlContentProvider extends ContentProvider {

    // NO_MATCH is for indicating root node, that the matcher is root node
    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final String AUTHORITY = "com.cocrux.m.pocketsql";
    public static final int ID_TABLE_T1_1 = 100;
    public static final int ID_TABLE_T1_2 = 101;
    public static final int ID_TABLE_T2_1 = 110;
    public static final int ID_TABLE_T2_2 = 111;

    // A content URI is a URI that identifies data in a provider.
    // Content URIs include the symbolic name of the entire provider (its authority)
    // and a name that points to a table or file (a path).
    // The optional id part points to an individual row in a table.
    public static final Uri CONTENT_URI_1 = Uri.parse("content://" + AUTHORITY
            + "/" + PSqlSqliteHelper.TABLE_T1);
    public static final Uri CONTENT_URI_2 = Uri.parse("content://" + AUTHORITY
            + "/" + PSqlSqliteHelper.TABLE_T2);

    static {
        mUriMatcher.addURI(AUTHORITY, PSqlSqliteHelper.TABLE_T1, ID_TABLE_T1_1);
        mUriMatcher.addURI(AUTHORITY, PSqlSqliteHelper.TABLE_T1 + "/#", ID_TABLE_T1_2);

        mUriMatcher.addURI(AUTHORITY, PSqlSqliteHelper.TABLE_T2, ID_TABLE_T2_1);
        mUriMatcher.addURI(AUTHORITY, PSqlSqliteHelper.TABLE_T1 + "/#", ID_TABLE_T2_2);
    }

    PSqlSqliteHelper db;

    @Override
    public boolean onCreate() {
        db = new PSqlSqliteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = mUriMatcher.match(uri);
        String table;
        switch (uriType) {
            case ID_TABLE_T1_1:
                table = PSqlSqliteHelper.TABLE_T1;
                break;
            case ID_TABLE_T1_2:
                table = PSqlSqliteHelper.TABLE_T1;
                queryBuilder.appendWhere(PSqlSqliteHelper.COL_ID + "="
                        + uri.getLastPathSegment());
                break;
            case ID_TABLE_T2_1:
                table = PSqlSqliteHelper.TABLE_T2;
                break;
            case ID_TABLE_T2_2:
                table = PSqlSqliteHelper.TABLE_T2;
                queryBuilder.appendWhere(PSqlSqliteHelper.COL_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        queryBuilder.setTables(table);
        Cursor cursor = queryBuilder.query(db.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = mUriMatcher.match(uri);
        String table;
        switch (uriType) {
            case ID_TABLE_T1_1:
                table = PSqlSqliteHelper.TABLE_T1;
                break;
            case ID_TABLE_T2_1:
                table = PSqlSqliteHelper.TABLE_T2;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Log.d("msg", "table: " + uri.getEncodedPath() + " : " + table);
        SQLiteDatabase db_w = db.getWritableDatabase();
        long id = db_w.insertWithOnConflict(table, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db_w.close();
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = mUriMatcher.match(uri);
        String table;
        switch (uriType) {
            case ID_TABLE_T1_1:
                table = PSqlSqliteHelper.TABLE_T1;
                break;
            case ID_TABLE_T2_1:
                table = PSqlSqliteHelper.TABLE_T2;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Log.d("msg", "using table: " + uri.getEncodedPath() + " : " + table);
        SQLiteDatabase db_w = db.getWritableDatabase();
        int result = db_w.delete(table, selection, selectionArgs);
        db_w.close();
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = mUriMatcher.match(uri);
        String table;
        switch (uriType) {
            case ID_TABLE_T1_1:
                table = PSqlSqliteHelper.TABLE_T1;
                break;
            case ID_TABLE_T2_1:
                table = PSqlSqliteHelper.TABLE_T2;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Log.d("msg", "using table: " + uri.getEncodedPath());
        SQLiteDatabase db_w = db.getWritableDatabase();
        int result = db_w.update(table, values, selection, selectionArgs);
        db_w.close();
        return result;
    }
}
