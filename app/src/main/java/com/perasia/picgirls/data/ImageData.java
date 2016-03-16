package com.perasia.picgirls.data;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

public class ImageData implements Parcelable {
    private static final String TAG = ImageData.class.getSimpleName();

    public static final String ID = "_id";
    public static final String TYPE = "type";
    public static final String URL = "url";
    public static final String WIDTH = "width";
    public static final String HEIGTH = "heigth";

    int id;
    int type;
    private String url;
    private int width;
    private int height;

    public ImageData() {

    }

    public ImageData(Parcel source) {
        id = source.readInt();
        type = source.readInt();
        url = source.readString();
        width = source.readInt();
        height = source.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel source) {
            return new ImageData(source);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    public ImageData(String url) {
        this();
        this.url = url;
    }

    public ImageData(String url, int type) {
        this(url);
        this.type = type;
    }

    public static ImageData getFixedImage(Context context, String url, int type) throws ExecutionException, InterruptedException {
        ImageData imageData = new ImageData(url, type);
        Bitmap bitmap = Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get();

        imageData.setWidth(bitmap.getWidth());
        imageData.setHeight(bitmap.getHeight());
        return imageData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
