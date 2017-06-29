package com.example.abhishek.medsreminder.dbManager;

import android.content.UriMatcher;
import android.net.Uri;

import com.example.abhishek.medsreminder.constants.DBConstants;

/**
 * Created by Abhishek on 29-Jun-17.
 */
public class Matcher {

    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(DBConstants.CONTENT_AUTHORITY, MedsContract.PATH_MEDS, DBConstants.MEDICINES);
    }

    public static synchronized String getTable(Uri uri){
        switch (mUriMatcher.match(uri)){
            case DBConstants.MEDICINES:
                return MedsContract.MedsEntry.TABLE_NAME;
        }

        return null;
    }
}
