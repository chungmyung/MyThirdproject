package com.chungmyung.memorealm.activity.Models;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by user on 2017-09-18.
 */

public class Memo extends RealmObject {

    private static AtomicInteger INTEGET_COUNTER;

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

    public static int getNewId(Realm realm) {
        if (INTEGET_COUNTER == null) {
            INTEGET_COUNTER = new AtomicInteger(0);

            Number maxId = realm.where(Memo.class).max("id");
            if (maxId != null) {
                INTEGET_COUNTER.set(maxId.intValue() + 1);
            }
        }
        return  INTEGET_COUNTER.getAndIncrement();

    }

    @Override
    public String toString() {
             final StringBuffer sb = new StringBuffer("Memo{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", memo=").append(memo).append('\'');
        sb.append('}');
        return  sb.toString();
    }
}
