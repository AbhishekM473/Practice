package com.example.abhishek.medsreminder.dbManager;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.abhishek.medsreminder.constants.DBConstants;

/**
 * Created by Abhishek on 29-Jun-17.
 */
public class MedsContentProvider extends ContentProvider {

    private Context mContext;
    private DbHelper mDbHelper;
    public static final String DATABASE_NAME = "meds.db";
    private static final int DATABASE_VERSION = 1;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDbHelper = new DbHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String groupBy = uri.getQueryParameter(DBConstants.QUERY_PARAMETER_GROUP_BY);
        String limit = uri.getQueryParameter(DBConstants.QUERY_PARAMETER_LIMIT);
        String notifyUri = uri.getQueryParameter(DBConstants.NOTIFY_URI);
        Cursor cursor = mDbHelper.query(Matcher.getTable(uri), projection, selection, selectionArgs, groupBy, null, sortOrder, limit);
        ContentResolver contentResolver = mContext.getContentResolver();
        cursor.setNotificationUri(contentResolver, uri);
        if (!TextUtils.isEmpty(notifyUri)) {
            contentResolver.notifyChange(Uri.parse(notifyUri), null);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long _id = mDbHelper.insert(Matcher.getTable(uri), values);
        notifyURI(uri);
        Uri returnUri = DBConstants.buildIdForInsert(_id);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsAffected = mDbHelper.delete(Matcher.getTable(uri), selection, selectionArgs);
        notifyURI(uri);
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsAffected = mDbHelper.update(Matcher.getTable(uri), values, selection, selectionArgs);
        notifyURI(uri);
        return rowsAffected;
    }

    private void notifyURI(Uri uri) {
        String notifyUri = uri.getQueryParameter(DBConstants.NOTIFY_URI);
        if (TextUtils.isEmpty(notifyUri)) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
    }
}
