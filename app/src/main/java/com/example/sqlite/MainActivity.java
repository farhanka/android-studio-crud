package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DataMahasiswa db;

    Button btn_tambah;
    TableRow tb;
    TextView tv_no, tv_npm, tv_nama, tv_jurusan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataMahasiswa(this);
        TableLayout tl = findViewById(R.id.tl);
        try {
            db.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Mahasiswa> listMahasiswa = db.getAllMahasiswa();
        int nomor = 1;

        if(listMahasiswa.isEmpty()){
            TextView empty = new TextView(this);
            empty.setText("Belum ada Data Mahasiswa");
            empty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            empty.setPadding(0,30,0,0);
            Log.d("EMP", "Kosong!");
            tl.addView(empty);

        }else {
            for (Mahasiswa mhs : listMahasiswa) {
                TableRow tr = new TableRow(this);
                TableLayout.LayoutParams trParams = new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                trParams.setMargins(0, 4, 0, 0);
                //            tr.setBackgroundColor(Color.rgb(255,240,255));
                tr.setLayoutParams(trParams);
                tr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent data = new Intent(MainActivity.this, DetailActivity.class);
                        data.putExtra("nama", mhs.get_nama());
                        data.putExtra("npm", mhs.get_npm());
                        data.putExtra("jurusan", mhs.get_jurusan());
                        data.putExtra("id", Integer.toString(mhs.get_id()));
                        startActivity(data);
                    }
                });
                TextView tv_no = new TextView(this);
                TextView tv_npm = new TextView(this);
                TextView tv_nama = new TextView(this);
                TextView tv_jurusan = new TextView(this);

                tv_no.setText(Integer.toString(nomor));
                nomor++;
                tv_no.setTextSize(20);
                tv_no.setPadding(2, 0, 40, 0);
                tv_no.setTextColor(Color.BLACK);
                tv_no.setBackgroundColor(Color.rgb(240, 240, 240));

                tv_npm.setText(mhs.get_npm());
                tv_npm.setTextSize(20);
                tv_npm.setPadding(40, 0, 40, 0);
                tv_npm.setTextColor(Color.BLACK);
                tv_npm.setBackgroundColor(Color.rgb(230, 230, 230));

                tv_nama.setText(mhs.get_nama());
                tv_nama.setTextSize(20);
                tv_nama.setPadding(40, 0, 40, 0);
                tv_nama.setTextColor(Color.BLACK);
                tv_nama.setBackgroundColor(Color.rgb(220, 220, 220));

                tv_jurusan.setText(mhs.get_jurusan());
                tv_jurusan.setTextSize(20);
                tv_jurusan.setPadding(40, 0, 0, 0);
                tv_jurusan.setTextColor(Color.BLACK);
                tv_jurusan.setBackgroundColor(Color.rgb(200, 200, 200));


                tr.addView(tv_no);
                tr.addView(tv_npm);
                tr.addView(tv_nama);
                tr.addView(tv_jurusan);
                tl.addView(tr);
            }
        }

        db.close();
        btn_tambah  = findViewById(R.id.btn_tambah);
        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FormBaruActivity.class));
            }
        });
    }



}