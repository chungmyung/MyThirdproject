package com.chungmyung.memodbexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @butterknife.BindView(R.id.email_edit)
    EditText mEmailEdit;
    @butterknife.BindView(R.id.password_edit)
    EditText mPasswordEdit;
    @butterknife.BindView(R.id.new_password_edit)
    EditText mNewPasswordEdit;
    @butterknife.BindView(R.id.result_text)
    TextView mResultText;

    private Realm mRealm;
//    private RealmAsyncTask mTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

        mRealm = Realm.getDefaultInstance();

        showResult();

    }

    public void SignUp() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                //추가.
                User user = realm.createObject(User.class);
                user.setEmail(mEmailEdit.getText().toString());
                user.setPassword(mPasswordEdit.getText().toString());

                showResult();
            }
        });
    }


    public void SignIn() {
        User user = mRealm.where(User.class)
                .equalTo("email", mEmailEdit.getText().toString())
                .equalTo("password", mPasswordEdit.getText().toString())
                .findFirst();

        if (user != null) {  //user가 존재하면
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show();
        }


    }

    public void UpdatePassword() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // 쿼리.
                User user = mRealm.where(User.class)
                        .equalTo("email", mEmailEdit.getText().toString())
                        .equalTo("password", mPasswordEdit.getText().toString())
                        .findFirst();
                if (user != null) {
                    //수정
                    user.setPassword(mNewPasswordEdit.getText().toString());
                }
                showResult();
            }
        });
    }

    public void deleteAccount(View view) {
          mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = mRealm.where(User.class)
                        .equalTo("email", mEmailEdit.getText().toString())
                        .equalTo("password", mPasswordEdit.getText().toString())
                        .findFirst();
                if (user != null) {
                    //삭제
                    user.deleteFromRealm();
                }
                showResult();
            }
        });
    }

    public void showResult() {
        RealmResults<User>  user = mRealm.where(User.class).findAll();
        mResultText.setText(user.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }
}
