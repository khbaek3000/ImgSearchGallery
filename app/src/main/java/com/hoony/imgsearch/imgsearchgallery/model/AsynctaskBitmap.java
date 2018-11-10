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

            InputStream is;

            if(strings[1].equals("thumb")) {
                // 썸네일일 경우 작은크기로 resize
                is = conn.getInputStream();
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, null, options);

                // width200 height 100에 맞춰 resize
                options.inSampleSize = calculateInSampleSize(options, 200, 100);
                options.inJustDecodeBounds = false;

                is.close();

                is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(url.openStream(), null, options);
                is.close();
            }
            else if(strings[1].equals("original")){
                // 원본사진으로 볼 경우
                is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            }


            conn.disconnect();

            return bitmap;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
    public  static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){

        //이미지를 작게 resize 하기 위한 메서드
        final  int height = options.outHeight;
        final  int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width >reqWidth){
            final int halfHeihgt = height/2;
            final int halfWidth = width/2;

            while((halfHeihgt/inSampleSize) >= reqHeight && (halfWidth/inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
