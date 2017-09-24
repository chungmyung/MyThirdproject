package com.chungmyung.dbsqliteexam;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.email_edit)
    EditText mEmailEdit;
    @BindView(R.id.password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.new_password_edit)
    EditText mNewPasswordEdit;
    @BindView(R.id.result_text)
    TextView mResultText;

    private UserDbHelper mDbHelper;
    private Thread mThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDbHelper = UserDbHelper.getInstance(this);

        showResult();

    }

    public void SignUp() {
        try {

            mThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(UserContract.UserEntry.COLUMN_NAME_EMAL, mEmailEdit.getText().toString());
                    values.put(UserContract.UserEntry.CONLUMN_NAME_PASSWORD, mPasswordEdit.getText().toString());

                    long newId = db.insert(UserContract.UserEntry.TABLE_NAME,
                            null,
                            values);

                    if (newId == -1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "에러", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "가입됨", Toast.LENGTH_SHORT).show();
                                showResult();
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
        }
        mThread.start();
    }


    public void SignIn(View view) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME,
                null,  // columns..
                UserContract.UserEntry.COLUMN_NAME_EMAL + "= '"
                        + mEmailEdit.getText().toString() + " ' AND"
                        + UserContract.UserEntry.CONLUMN_NAME_PASSWORD + "= '"
                        + mPasswordEdit.getText().toString() + " ' ",
                // where
                null, // selection args
                null,
                null,
                null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
                cursor.close();
            } else {
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void UpdatePassword() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.CONLUMN_NAME_PASSWORD, mNewPasswordEdit.getText().toString());

        int count = db.update(UserContract.UserEntry.TABLE_NAME,
                values,
                UserContract.UserEntry.COLUMN_NAME_EMAL + " = ? ",
                new String[]{mEmailEdit.getText().toString()});

        if (count > 0) {
            showResult();
        }
    }

    public void deleteAccount(View view) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int count = db.delete(UserContract.UserEntry.TABLE_NAME,
                UserContract.UserEntry.COLUMN_NAME_EMAL + " ='"
                        + mEmailEdit.getText().toString() + " ' AND "
                        + UserContract.UserEntry.CONLUMN_NAME_PASSWORD + " ='"
                        + mPasswordEdit.getText().toString() + " ' ",
                null);    // 이것도 삭제된 수를 리턴)


        if (count > 0) {
            Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show();
            showResult();
        }
    }


    public void showResult() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();  // 불러올수 있는 것 다 갖고 오기.
        Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME,
                null,  //  select column  모름면 다 null..
                null,  //  From table_name
                null,  // Where condition.. 전체 조건..
                null,  // Groug by  column_name
                null,  // Having condition..  전체 조건 안에서 세부 조건...
                null    // Order by column _name 정렬... 오름차순. 내림차순..
        );

        if (cursor != null) {
            StringBuilder stringBuilder = new StringBuilder();  //비동기에 Threadstable
            cursor.moveToFirst();  // 맨앞의 앞자료 -1.
            while (cursor.moveToNext()) {
                String email = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_EMAL));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.CONLUMN_NAME_PASSWORD));

                stringBuilder.append(email).append(",").append(password).append("\n");
            }
            mResultText.setText(stringBuilder.toString());
        }
        cursor.close();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mThread != null && mThread.isAlive()) {
            mThread.interrupt();
        }

    }

}




