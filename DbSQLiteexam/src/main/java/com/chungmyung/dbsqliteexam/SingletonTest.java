package com.chungmyung.dbsqliteexam;

/**
 * Created by user on 2017-09-24.
 */

public class SingletonTest {
    private static final SingletonTest ourInstance = new SingletonTest();

    public static SingletonTest getInstance() {
        return ourInstance;
    }

    private SingletonTest() {
    }
}
