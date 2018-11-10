package com.hoony.imgsearch.imgsearchgallery.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AsynctaskModule extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {

        //검색 키워드에 대한 데이터를 네이버 api에서 받아오는 역할을 합니다.
        String clientId = "fRjOFUMjaOU4099diaRf";
        String clientSecret = "Y0emNa1tXK";
        int display = 30; //초기설정 12
        int start = Integer.parseInt(strings[1]);

        try{
            String keyword = URLEncoder.encode(strings[0], "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/image?query=" + keyword +
                    "&display=" + display + "&start=" + start + "&sort=sim";
            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", clientId);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            conn.connect();


            int responseCode = conn.getResponseCode();
            Log.d("response code","response code: " + responseCode);

            InputStream inputStream = null;
            BufferedReader bufferedReader = null;

            if(responseCode == HttpURLConnection.HTTP_OK){
                inputStream = conn.getInputStream();
            }
            else{
                inputStream = conn.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine())!=null){
                sb.append(line);
            }

            bufferedReader.close();
            conn.disconnect();

            String data = sb.toString();
            Log.d("Data", "Data : " + data);

            return  data;

        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }

    }
}
