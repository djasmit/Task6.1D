package com.example.task61d.model;

import androidx.annotation.NonNull;

public class User {
    private int _userId;
    private String _username;
    private String _password;
    private String _fullName;
    private String _phoneNo;


    public User(String _username, String _password, String _fullName, String _phoneNo) {
        this._username = _username;
        this._password = _password;
        this._fullName = _fullName;
        this._phoneNo = _phoneNo;
    }

    public User() {
    }

    @NonNull @Override
    public String toString() {
        return _username;
    }

    public int get_userId() {
        return _userId;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_fullName() {
        return _fullName;
    }

    public void set_fullName(String _fullName) {
        this._fullName = _fullName;
    }

    public String get_phoneNo() {
        return _phoneNo;
    }

    public void set_phoneNo(String _phoneNo) {
        this._phoneNo = _phoneNo;
    }
}
