package com.chungmyung.dbsqliteexam;

import android.provider.BaseColumns;

/**
 * Created by user on 2017-09-23.
 */

public final class UserContract {
    private UserContract () {}


    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_EMAL = "email";
        public static final String CONLUMN_NAME_PASSWORD = "password";


    }
}
