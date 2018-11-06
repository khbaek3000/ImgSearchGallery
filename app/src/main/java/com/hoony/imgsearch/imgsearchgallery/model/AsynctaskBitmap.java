package com.hoony.imgsearch.imgsearchgallery.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsynctaskBitmap extends AsyncTask<String, Void, Bitmap> {

    Bitmap bitmap;

    @Override
    protected Bitmap doInBackground(String... strings) {

        // 검색결과로 부터의 데이터에서 얻은 thumbnail과 원본 이미지를 불러오는 역할을 합니다.
        String imgURL = strings[0];

        try{

            URL url = new URL(imgURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            Log.d("img down response code","response code: " + responseCode);

            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            is.close();
            conn.disconnect();

            return bitmap;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
