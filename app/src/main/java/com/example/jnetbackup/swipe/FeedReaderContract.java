package com.example.jnetbackup.swipe;

import android.provider.BaseColumns;

/**
 * Created by Haseeb on 7/15/2016.
 */
public final  class FeedReaderContract {
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TEMPERATURE= "title";
        public static final String COLUMN_NAME_SOMKE="updated";
        public static final String COLUMN_NAME_HUMIDITY = "subtitle";
        public static final String COLUMN_NAME_TIME = "subt";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }
}
