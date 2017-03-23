package com.android.andryyu.lifehelper.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by yufei on 2017/3/16.
 */

public class ImageUtil {

    public static void load(Context context, @DrawableRes int imageRes, ImageView view) {
        Glide.with(context).load(imageRes).crossFade().into(view);
    }

    public static void loadUrl(Context context, Uri uri, ImageView view){
        Glide.with(context)
                .load(uri)
                .thumbnail(0.5f)
                .into(view);
    }

    public static void loadUrlWithZoom(Context context, Uri uri, ImageView view){
        Glide.with(context)
                .load(uri)
                .thumbnail(0.5f)
                .centerCrop()
                .into(view);
    }

    public static void clear(Context context) {
        Glide.get(context).clearMemory();
    }
}
