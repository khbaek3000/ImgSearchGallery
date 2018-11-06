package com.hoony.imgsearch.imgsearchgallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hoony.imgsearch.imgsearchgallery.model.AsynctaskBitmap;

public class ImgViewActivity extends AppCompatActivity {

    Bitmap bitmap;
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgview);
        img = (ImageView) findViewById(R.id.iv_imgview_img);

        Intent intent = getIntent();
        String imglink = intent.getStringExtra("imglink");
        final String title = intent.getStringExtra("title");

        AsynctaskBitmap bitmapAsynctask = new AsynctaskBitmap();

        try{
            bitmap = bitmapAsynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imglink).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        img.setImageBitmap(bitmap);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
            }
        });


    }
}
