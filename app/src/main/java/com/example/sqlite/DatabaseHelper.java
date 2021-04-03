package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.sql.Blob;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mahasiswa.db";

    public static final String TABLE_MAHASISWA = "tb_mahasiswa";

    public static final String KEY_ID = "id";
    public static final String KEY_NPM = "npm";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_JURUSAN = "jurusan";
    public static final String KEY_IMAGE = "img";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MAHASISWA = "CREATE TABLE " + TABLE_MAHASISWA +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_NPM + " TEXT,"
                    + KEY_NAMA + " TEXT,"
                    + KEY_JURUSAN + " TEXT,"
                    + KEY_IMAGE + " BLOB)";

        db.execSQL(CREATE_TABLE_MAHASISWA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    }

