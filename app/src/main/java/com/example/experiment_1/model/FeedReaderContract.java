package com.example.experiment_1.model;

import android.provider.BaseColumns;

/**
 * @author ylqq
 */
public final class FeedReaderContract {
    /**
     * 为了防止其他类无意中实例化这个“协同类”
     */
    private FeedReaderContract() {
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
