package com.example.abhishek.medsreminder.dbManager;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.abhishek.medsreminder.constants.DBConstants;

/**
 * Created by Abhishek on 29-Jun-17.
 */
public class MedsContract {

    public static final String PATH_MEDS = "meds";

    public static final class MedsEntry implements BaseColumns{
        public static final Uri MEDS_URI = DBConstants.CONTENT_URI.buildUpon().appendPath(PATH_MEDS).build();

        public static final String TABLE_NAME = "medsList";

        public static final String COLUMN_ID = "med_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DOSAGE = "dosage";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_TIME_STAMP = "timestamp";
        public static final String COLUMN_STATUS = "status";

        public static String getMessageIdFromUri(Uri uri) {
            return String.valueOf(uri.getPathSegments().get(0));
        }
    }
}
