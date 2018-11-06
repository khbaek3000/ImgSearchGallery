package com.hoony.imgsearch.imgsearchgallery.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hoony.imgsearch.imgsearchgallery.R;

import java.util.ArrayList;

public class GvAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ItemsData> mItems;
    int mLayout;
    LayoutInflater inf;

    public GvAdapter(Context context,ArrayList<ItemsData> items, int layout){

        mContext = context;
        mItems = items;
        mLayout = layout;
        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ImageView imageView;
        if(convertView == null){
            convertView = inf.inflate(mLayout, null);

        }
        imageView = (ImageView) convertView.findViewById(R.id.imageview);
        imageView.setImageBitmap(mItems.get(position).bitThumbnail);

        return convertView;
    }
}
