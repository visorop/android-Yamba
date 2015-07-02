package com.marakana.yamba.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by veselin.demirev on 2.7.2015.
 */
public class StatusProvider extends ContentProvider {
    private DbHelper dbHelper = new DbHelper(this.getContext());
    private static final String TAG = StatusProvider.class.getSimpleName();
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE,
                StatusContract.STATUS_DIR);
        sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE
                + "/#", StatusContract.STATUS_ITEM);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case StatusContract.STATUS_DIR:
                Log.d(TAG, "gotType: " + StatusContract.STATUS_TYPE_DIR);
                return StatusContract.STATUS_TYPE_DIR;
            case StatusContract.STATUS_ITEM:
                Log.d(TAG, "gotType: " + StatusContract.STATUS_TYPE_ITEM);
                return StatusContract.STATUS_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri ret = null;
        // Assert correct uri //
        if (sURIMatcher.match(uri) != StatusContract.STATUS_DIR) {
            throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //
        long rowId = db.insertWithOnConflict(StatusContract.TABLE, null,
                values, SQLiteDatabase.CONFLICT_IGNORE); //
        // Was insert successful?
        if (rowId != -1) { //
            long id = values.getAsLong(StatusContract.Column.ID);
            ret = ContentUris.withAppendedId(uri, id); //
            Log.d(TAG, "inserted uri: " + ret);
        // Notify that data for this uri has changed
            getContext().getContentResolver()
                    .notifyChange(uri, null); //
        }
        return ret;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
