package com.example.abhishek.medsreminder.dbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by Abhishek on 29-Jun-17.
 */
public class DbHelper extends SQLiteOpenHelper {

    private Context mContext;
    private SQLiteQueryBuilder mSQLiteQueryBuilder = new SQLiteQueryBuilder();

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + MedsContract.MedsEntry.TABLE_NAME + " (" +
                MedsContract.MedsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedsContract.MedsEntry.COLUMN_NAME + " TEXT, " +
                MedsContract.MedsEntry.COLUMN_DOSAGE + " TEXT, " +
                MedsContract.MedsEntry.COLUMN_DATE + " TEXT, " +
                MedsContract.MedsEntry.COLUMN_TIME + " TEXT, " +
                MedsContract.MedsEntry.COLUMN_TIME_STAMP + " TEXT, " +
                MedsContract.MedsEntry.COLUMN_STATUS + " TEXT);";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MedsContract.MedsEntry.TABLE_NAME);
        onCreate(db);
    }

    public Cursor query(String table, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        SQLiteDatabase db = getReadableDatabase();
        mSQLiteQueryBuilder.setTables(table);
        return mSQLiteQueryBuilder.query(db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit);
    }

    public long insert(String table, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(table, whereClause, whereArgs);
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(table, values, whereClause, whereArgs);
    }


}
