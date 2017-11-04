package com.chungmyung.dbfirebaseexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {



    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private EditText mNewPasswordEdit;
    private TextView mResultText;

    private FirebaseAnalytics mFirebaseAnalytics ;


    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mEmailEdit = (EditText) findViewById(R.id.email_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mNewPasswordEdit = (EditText) findViewById(R.id.new_password_edit);
        mResultText = (TextView) findViewById(R.id.result_text);
    }


    public void SignIn(View view) {


    }

    public void SignUp(View view) {
    }

    public void updatePassword(View view) {
    }

    public void deleteAccount(View view) {

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


}
