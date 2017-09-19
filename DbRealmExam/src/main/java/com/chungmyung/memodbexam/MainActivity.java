package com.chungmyung.memodbexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @butterknife.BindView(R.id.email_edit)
    EditText mEmailEdit;
    @butterknife.BindView(R.id.password_edit)
    EditText mPasswordEdit;
    @butterknife.BindView(R.id.new_password_edit)
    EditText mNewPasswordEdit;
    @butterknife.BindView(R.id.result_text)
    TextView mResultText;

    private Realm mRealm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

        mRealm = Realm.getDefaultInstance();

    }

    public void SignUp() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class);
                user.setEmail(mEmailEdit.getText().toString());
                user.setPassword(mPasswordEdit.getText().toString());

                ShowResult();
            }
        });

    }

    public void SignIn() {

    }

    public void UpdatePassword() {

    }

    public void DeleteUser() {

    }
    public void ShowResult() {
        mResultText.setText(mRealm.where(User.class).findAll().toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }
}
