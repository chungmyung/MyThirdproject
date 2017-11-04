package com.chungmyung.dbsqliteexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user on 2017-09-23.
 */

public class UserDbHelper extends SQLiteOpenHelper {

    private static UserDbHelper ourInstance;

    private static AtomicInteger sConnectionCount = new AtomicInteger(0);

    public static synchronized UserDbHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new UserDbHelper(context);
        }
        sConnectionCount.incrementAndGet();
        return ourInstance;
    }

    public void close() {
        if (sConnectionCount.decrementAndGet() == 0) {
            sConnectionCount = null;
        }
    }


    public static final String DATABASE_NAME = "User.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_USER =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                    "._ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.UserEntry.COLUMN_NAME_EMAL + "TEXT," +
                    UserContract.UserEntry.CONLUMN_NAME_PASSWORD + "TEXT" +
                    " );";

    private UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //DB에 접근하는 최초 동작일 때. table이 많은 만큼 반복된다..
        sqLiteDatabase.execSQL(SQL_CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // DB가 업그래이드 될때 기입하는 부분... 거의 제조사 소관.
        sqLiteDatabase.execSQL("DROP TABLE " + UserContract.UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL(SQL_CREATE_USER);

    }

    public long insert(ContentValues values) {

        SQLiteDatabase db = getWritableDatabase();
        long newId = db.insert(UserContract.UserEntry.TABLE_NAME,
                null,
                values);
        return newId;
    }


    public long insert(String email, String password) {
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAL, email);
        values.put(UserContract.UserEntry.CONLUMN_NAME_PASSWORD, password);
        return insert(values);
    }


    public boolean SignIn(String email, String password) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME,
                null,  // columns..
                UserContract.UserEntry.COLUMN_NAME_EMAL + "= '"
                        + email + " ' AND"
                        + UserContract.UserEntry.CONLUMN_NAME_PASSWORD + "= '"
                        + password + " ' ",
                // where
                null, // selection args
                null,
                null,
                null);

        if (cursor == null) {
            return false;
        }

        return cursor.getCount() > 0;
    }

    public boolean UpdatePassword(String oldPassword, String newPassword) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.CONLUMN_NAME_PASSWORD, newPassword);

        int count = db.update(UserContract.UserEntry.TABLE_NAME,
                values,
                UserContract.UserEntry.COLUMN_NAME_EMAL + " = ? ",
                new String[]{oldPassword});

        return count > 0;
    }
}



