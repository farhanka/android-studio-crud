package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

public class DetailActivity extends AppCompatActivity {

    private DataMahasiswa db;
    Button btn_submit, btn_hapus;
    EditText et_nama, et_npm, et_jurusan;
    ImageView pic;
    Bitmap bitmapPic;
    byte[] bytePic;
    int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent bundle = getIntent();

        db = new DataMahasiswa(this);
        try {
            db.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        init();

        String id = bundle.getStringExtra("id");
        String nama = bundle.getStringExtra("nama");
        String npm   = bundle.getStringExtra("npm");
        String jurusan = bundle.getStringExtra("jurusan");
        byte[] img = bundle.getByteArrayExtra("img");

//        if(img == null) {
//            pic.invalidate();
//            BitmapDrawable drawable = (BitmapDrawable) pic.getDrawable();
//            bitmapPic = drawable.getBitmap();
//        }

        if(db.getImage(id) == null) {
            pic.invalidate();
            BitmapDrawable drawable = (BitmapDrawable) pic.getDrawable();
            bitmapPic = drawable.getBitmap();
        }else{
            bitmapPic = db.getImage(id);
            pic.setImageBitmap(bitmapPic);

        }


        et_nama.setText(nama);
        et_npm.setText(npm);
        et_jurusan.setText(jurusan);

        submit(id);
        hapus(id);
        setGambar(id);

    }

    private void init(){
        et_npm = findViewById(R.id.et_edit_npm);
        et_nama = findViewById(R.id.et_edit_nama);
        et_jurusan = findViewById(R.id.et_edit_jurusan);
        btn_hapus = findViewById(R.id.btn_hapus);
        pic = findViewById(R.id.iv_pic);
        btn_submit = findViewById(R.id.btn_edit_submit);
    }

    private void hapus(String id){
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteOne(id);
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                Toast.makeText(DetailActivity.this, "Data ID : "+id + " Terhapus", Toast.LENGTH_LONG).show();

            }
        });

    }
    private void submit(String id){
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_nama = et_nama.getText().toString();
                String new_npm = et_npm.getText().toString();
                String new_jurusan = et_jurusan.getText().toString();

                if( TextUtils.isEmpty(et_npm.getText())){
                    Toast.makeText(DetailActivity.this, "Gagal!", Toast.LENGTH_SHORT).show();
                    et_npm.requestFocus();
                    et_npm.setError( "NPM tidak boleh kosong!" );

                }else if( TextUtils.isEmpty(et_nama.getText())){
                    Toast.makeText(DetailActivity.this, "Gagal!", Toast.LENGTH_SHORT).show();
                    et_nama.requestFocus();
                    et_nama.setError( "Nama tidak boleh kosong!" );

                }else if( TextUtils.isEmpty(et_jurusan.getText())) {
                    Toast.makeText(DetailActivity.this, "Gagal!", Toast.LENGTH_SHORT).show();
                    et_jurusan.requestFocus();
                    et_jurusan.setError("Jurusan tidak boleh kosong!");

                }else {
                    startActivity(new Intent(DetailActivity.this, MainActivity.class));
                    Toast.makeText(DetailActivity.this, "Data ID : " + id + " Berhasil diperbarui", Toast.LENGTH_LONG).show();
                    db.updateOne(id, new_npm, new_nama, new_jurusan );
                    bytePic = db.bitmapToByte(bitmapPic);
                    db.insertImg(bytePic,id);

                }
            }
        });
    }

    private void setGambar(String id){
        pic.setOnClickListener(new View.OnClickListener() {
            private static final int RESULT_LOAD_IMG = 1;

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            }
        });
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final int MAX_IMG_SIZE = 50000000; // 5 MegaByte?
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                int x = selectedImage.getByteCount();
                if(selectedImage.getByteCount() < MAX_IMG_SIZE) {
                    bitmapPic = selectedImage;
                    pic.setImageBitmap(selectedImage);
                }else{
                    Toast.makeText(this, "Ukuran foto terlalu besar", Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(DetailActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(DetailActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}