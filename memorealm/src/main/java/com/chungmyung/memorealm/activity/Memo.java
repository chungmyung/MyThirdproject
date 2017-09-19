package com.chungmyung.memorealm.activity;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by user on 2017-09-18.
 */

public class Memo extends RealmObject {

    private static AtomicInteger INTEGER_COUNTER;

    @PrimaryKey
    private int id;

    @Required
    private String title;
    private String memo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

//  새 ID를 추가 해주는 코드
    public static int getNewId(Realm realm) {
        if (INTEGER_COUNTER == null) {
            INTEGER_COUNTER = new AtomicInteger(0);

            Number maxId = realm.where(Memo.class).max("id");
            if (maxId != null) {
                INTEGER_COUNTER.set(maxId.intValue() + 1);
            }
        }
        return INTEGER_COUNTER.getAndIncrement();
    }

    @Override
    public String toString() {
        return "Memo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
