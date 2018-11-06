package com.hoony.imgsearch.imgsearchgallery.model;

import android.graphics.Bitmap;

public class ItemsData {

    String title;
    String imgLink;
    Bitmap bitImage;
    Bitmap bitThumbnail;
    int sizeheight;
    int sizewidth;

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitImage() {
        return bitImage;
    }

    public void setBitImage(Bitmap bitImage) {
        this.bitImage = bitImage;
    }

    public Bitmap getBitThumbnail() {
        return bitThumbnail;
    }

    public void setBitThumbnail(Bitmap bitThumbnail) {
        this.bitThumbnail = bitThumbnail;
    }

    public int getSizeheight() {
        return sizeheight;
    }

    public void setSizeheight(int sizeheight) {
        this.sizeheight = sizeheight;
    }

    public int getSizewidth() {
        return sizewidth;
    }

    public void setSizewidth(int sizewidth) {
        this.sizewidth = sizewidth;
    }
}
