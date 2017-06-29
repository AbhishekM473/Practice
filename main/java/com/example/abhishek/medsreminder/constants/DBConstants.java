package com.example.abhishek.medsreminder.constants;

import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by Abhishek on 29-Jun-17.
 */
public class DBConstants {
    public static final String QUERY_PARAMETER_LIMIT = "LIMIT";
    public static final String QUERY_PARAMETER_GROUP_BY = "GROUP BY";
    public static final String NOTIFY_URI = "NOTIFY_URI";

    public static final String CONTENT_AUTHORITY = "com.example.abhishek.medsreminder";
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final int MEDICINES = 876;

    public static Uri buildIdForInsert(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
