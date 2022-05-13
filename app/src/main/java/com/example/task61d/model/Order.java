package com.example.task61d.model;

public class Order {
    public Order() {
    }

    private int _receiverID;
    private int _senderID;
    private int _orderID;
    private String _date;
    private String _time;
    private String _location;
    private String _goodType;
    private String _weight;
    private String _width;
    private String _length;
    private String _height;
    private String _truckType;

    public Order(int _senderID, int _receiverID, String _date, String _time, String _location, String _goodType, String _weight, String _height, String _width, String _length, String _truckType) {
        this._receiverID = _receiverID;
        this._senderID = _senderID;
        this._date = _date;
        this._time = _time;
        this._location = _location;
        this._goodType = _goodType;
        this._weight = _weight;
        this._width = _width;
        this._length = _length;
        this._height = _height;
        this._truckType = _truckType;
    }

    public int get_receiverID() {
        return _receiverID;
    }

    public void set_receiverID(int _receiverID) {
        this._receiverID = _receiverID;
    }

    public int get_senderID() {
        return _senderID;
    }

    public void set_senderID(int _senderID) {
        this._senderID = _senderID;
    }

    public int get_orderID() {
        return _orderID;
    }

    public void set_orderID(int _orderID) {
        this._orderID = _orderID;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_goodType() {
        return _goodType;
    }

    public void set_goodType(String _goodType) {
        this._goodType = _goodType;
    }

    public String get_truckType() {
        return _truckType;
    }

    public void set_truckType(String _truckType) {
        this._truckType = _truckType;
    }

    public String get_weight() {
        return _weight;
    }

    public void set_weight(String _weight) {
        this._weight = _weight;
    }

    public String get_width() {
        return _width;
    }

    public void set_width(String _width) {
        this._width = _width;
    }

    public String get_length() {
        return _length;
    }

    public void set_length(String _length) {
        this._length = _length;
    }

    public String get_height() {
        return _height;
    }

    public void set_height(String _height) {
        this._height = _height;
    }
}
