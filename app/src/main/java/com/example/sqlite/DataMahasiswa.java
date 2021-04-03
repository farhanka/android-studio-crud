package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.sqlite.DatabaseHelper.KEY_ID;
import static com.example.sqlite.DatabaseHelper.KEY_IMAGE;
import static com.example.sqlite.DatabaseHelper.KEY_JURUSAN;
import static com.example.sqlite.DatabaseHelper.KEY_NAMA;
import static com.example.sqlite.DatabaseHelper.KEY_NPM;
import static com.example.sqlite.DatabaseHelper.TABLE_MAHASISWA;

public class DataMahasiswa {
    private SQLiteDatabase database;
    private DatabaseHelper dbhelper;

    public DataMahasiswa(Context context){
        dbhelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public void addMahasiswa(Mahasiswa mahasiswa){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_NPM, mahasiswa.get_npm());
        values.put(DatabaseHelper.KEY_NAMA, mahasiswa.get_nama());
        values.put(DatabaseHelper.KEY_JURUSAN, mahasiswa.get_jurusan());

        //inserting row
        database.insert(TABLE_MAHASISWA, null, values);
    }

    public List<Mahasiswa> getAllMahasiswa(){
        List<Mahasiswa> listMahasiswa = new ArrayList<Mahasiswa>();

        //select all data mahasiswa
        String allMahasiswa = "SELECT * FROM " + TABLE_MAHASISWA;
        Cursor cursor = database.rawQuery(allMahasiswa, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.set_id(Integer.parseInt(cursor.getString(0)));
                mahasiswa.set_npm(cursor.getString(1));
                mahasiswa.set_nama(cursor.getString(2));
                mahasiswa.set_jurusan(cursor.getString(3));

                //adding mahasiswa to the list
                listMahasiswa.add(mahasiswa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listMahasiswa;
    }

    public void deleteOne(String id) {
        String queryString = "DELETE FROM " + TABLE_MAHASISWA + " WHERE " + KEY_ID + " = "+ id;
        database.execSQL(queryString);
    }

    public void updateOne(String id, String new_npm, String new_nama, String new_jurusan){
        database = dbhelper.getWritableDatabase();
        String queryString = "UPDATE " + TABLE_MAHASISWA +
                " SET "
                + KEY_NPM + " = '" + new_npm +"',"
                + KEY_NAMA + " = '" + new_nama + "',"
                + KEY_JURUSAN + " = '" + new_jurusan+
                "' WHERE " + KEY_ID + " = " +id;
//        String m = "UPDATE tb_mahasiswa SET nama='aaaaaaaaaaa' WHERE id=8";
        database.execSQL(queryString);
    }

    public void insertImg(byte[] imgbyte, String id  ){
        database = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_IMAGE, imgbyte);
        database.update(TABLE_MAHASISWA, cv, KEY_ID +" = "+id,null);
    }


    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap getImage(String id){
        database = dbhelper.getReadableDatabase();
        Bitmap pic = null;
        String getPicQuery = "SELECT "+ KEY_IMAGE + " FROM " + TABLE_MAHASISWA + " WHERE id="+ id;
        Cursor cursor = database.rawQuery(getPicQuery,null);
        if(cursor.moveToFirst()){
            byte[] bytepic = cursor.getBlob(0);
            if(bytepic == null){
                return pic;
            }
            pic = BitmapFactory.decodeByteArray(bytepic, 0, bytepic.length);
            return pic;
        }
        return pic;
    }
}
