package com.example.task61d.model;

public class Truck {
    private int _truckID;
    private String _phoneNo;
    private String _truckType;


    public Truck(String _phoneNo, String _truckType) {
        this._phoneNo = _phoneNo;
        this._truckType = _truckType;
    }

    public Truck() {
    }

    public int get_truckID() {
        return _truckID;
    }

    public void set_truckID(int _truckID) {
        this._truckID = _truckID;
    }

    public String get_phoneNo() {
        return _phoneNo;
    }

    public void set_phoneNo(String _phoneNo) {
        this._phoneNo = _phoneNo;
    }

    public String get_truckType() {
        return _truckType;
    }

    public void set_truckType(String _truckType) {
        this._truckType = _truckType;
    }
}
