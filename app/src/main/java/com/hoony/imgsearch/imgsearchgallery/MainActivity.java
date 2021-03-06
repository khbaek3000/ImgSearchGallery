package com.hoony.imgsearch.imgsearchgallery;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.hoony.imgsearch.imgsearchgallery.model.AsynctaskBitmap;
import com.hoony.imgsearch.imgsearchgallery.model.AsynctaskModule;
import com.hoony.imgsearch.imgsearchgallery.model.GvAdapter;
import com.hoony.imgsearch.imgsearchgallery.model.ItemsData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etSearch;
    ImageButton ibtnSearch;
    GridView gvGallery;
    String keyword;
    String mResult;
    int start =1;
    int display = 60;
    int total;
    Parcelable state;

    ArrayList<ItemsData> mArrayList;
    GvAdapter gvAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //에디트텍스트, 검색버튼, 그리드뷰 선언
        etSearch = (EditText) findViewById(R.id.et_main_search);
        ibtnSearch = (ImageButton) findViewById(R.id.btn_main_search);
        gvGallery = (GridView) findViewById(R.id.gv_main_gallery);

        mArrayList = new ArrayList<>();
        gvAdapter = new GvAdapter(getApplicationContext(), mArrayList, R.layout.row);
        gvGallery.setAdapter(gvAdapter);

        if(state !=null)
            gvGallery.onRestoreInstanceState(state);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ibtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = etSearch.getText().toString();
                if(!(keyword.equals(""))){
                    start = 1;
                    mResult = search(keyword);
                    resultProcess(mResult);
                }
            }
        });

        gvGallery.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                // 아래로 더 이상 스크롤 할 수 없을 경우
               if(!gvGallery.canScrollVertically(1)){
                   mResult = scrollSearch(keyword);
                  resultProcess(mResult);

               }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        gvGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ItemsData sendData = mArrayList.get(position);
                Intent intent = new Intent(MainActivity.this, ImgViewActivity.class);
                intent.putExtra("imglink", sendData.getImgLink());
                intent.putExtra("title", sendData.getTitle());

                startActivity(intent);
            }
        });

    }

    public String search(String string){

        // 처음 검색 키워드를 입력하고 검색할 경우 검색결과를 불러오는 메서드
        String result="";
        mArrayList.clear();
        start = 1;
        AsynctaskModule searchAsynctask = new AsynctaskModule();
        try{
            result = searchAsynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, string, ""+1).get();


        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public String scrollSearch(String string){
        // 아래로 스크롤 시 검색결과를 더 불러오는 메서드
        String result = "";
        start += display;

        AsynctaskModule scrollAsynctask = new AsynctaskModule();
        try{
            result = scrollAsynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, string, ""+start).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public void resultProcess(String result){

        // 검색 결과로 얻은 json 데이터를 객체에 저장하는 메서드
        try {
            JSONObject jsonObject = new JSONObject(result);
            total = jsonObject.getInt("total");
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString("title");
                String imgLink = item.getString("link");
                String thumbLink = item.getString("thumbnail");
                Bitmap thumbnail;
                int sizeheight = item.getInt("sizeheight");
                int sizewidth = item.getInt("sizewidth");
                AsynctaskBitmap bitmapAsynctask = new AsynctaskBitmap();
                ItemsData itemsData = new ItemsData();

                try{
                    thumbnail = bitmapAsynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, thumbLink).get();
                    itemsData.setBitThumbnail(thumbnail);

                }catch (Exception e){
                    e.printStackTrace();
                }

                itemsData.setTitle(title);
                itemsData.setImgLink(imgLink);
                itemsData.setSizeheight(sizeheight);
                itemsData.setSizewidth(sizewidth);

                mArrayList.add(itemsData);
            }

            gvAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("state save", "상태 저장");
        state = gvGallery.onSaveInstanceState();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
