package com.chungmyung.dbsqliteexam;

/**
 * Created by user on 2017-09-19.
 */

public class User {
    private String email ;
    private String password ;

    // 반드시 빈생성자 있어야 함.


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
