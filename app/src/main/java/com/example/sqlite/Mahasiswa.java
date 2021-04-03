package com.example.sqlite;

public class Mahasiswa {
    int _id;
    String _npm;
    String _nama;
    String _jurusan;
    String img;

    public Mahasiswa() {
    }

    public Mahasiswa(String _npm, String _nama, String _jurusan) {
        this._npm = _npm;
        this._nama = _nama;
        this._jurusan = _jurusan;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_npm() {
        return _npm;
    }

    public void set_npm(String _npm) {
        this._npm = _npm;
    }

    public String get_nama() {
        return _nama;
    }

    public void set_nama(String _nama) {
        this._nama = _nama;
    }

    public String get_jurusan() {
        return _jurusan;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void set_jurusan(String _jurusan) {
        this._jurusan = _jurusan;
    }
}
