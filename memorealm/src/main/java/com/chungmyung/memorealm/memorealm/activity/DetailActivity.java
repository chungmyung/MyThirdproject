package com.chungmyung.memorealm.memorealm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.chungmyung.memorealm.R;
import com.chungmyung.memorealm.memorealm.Models.Memo;
import com.chungmyung.memorealm.memorealm.fragment.MemoDetailFragment;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Memo memo = null;
        if (getIntent() != null) {
            int id = getIntent().getIntExtra("id", -1);
            // 딴곳에서 안쓰고 여기서 만 Realm을 시니까.. 전역으로 하지 않고.
            Realm realm = Realm.getDefaultInstance();
            // id가 하나인것을 찾아서 첫번째 것을 return해라...
            memo = realm.where(Memo.class).equalTo("id", id).findFirst();
//            realm.beginTransaction();  읽어ㅇ오는 것은 beginTransaction으로 하지 않음.
//              realm.commitTransaction(); 잘못된 코드... 위의 것과 같이..
            realm.close();
        }
        if (savedInstanceState == null) {
            if (memo == null) {
                // 추가
                addFragmentTransaction(new MemoDetailFragment());
            } else {
                // 수정 Instance가 없으니 DetailFragment에 가서 newInstance ()만든다.
                addFragmentTransaction(MemoDetailFragment.newInstance(memo));
            }
        }

    }

    private void addFragmentTransaction(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }

    private void replaceFragmentTransaction(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
