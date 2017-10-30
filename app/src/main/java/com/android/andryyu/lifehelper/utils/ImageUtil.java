package com.android.andryyu.lifehelper.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.android.andryyu.lifehelper.R;
import com.bumptech.glide.Glide;
import com.github.quinn.iconlibrary.IconicFontDrawable;
import com.github.quinn.iconlibrary.icons.Icon;

/**
 * Created by yufei on 2017/3/16.
 */

public class ImageUtil {

    public static void setIconFont(Context context, ImageView iv, Icon icon, int rsid) {
        IconicFontDrawable iconDraw = new IconicFontDrawable(context);
        iconDraw.setIcon(icon);
        iconDraw.setIconColor(context.getResources().getColor(rsid));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            iv.setBackground(iconDraw);
        else
            iv.setBackgroundDrawable(iconDraw);
    }

    public static void load(Context context, @DrawableRes int imageRes, ImageView view) {
        Glide.with(context).load(imageRes).crossFade().into(view);
    }

    public static void loadUrl(Context context, Uri uri, ImageView view){
        Glide.with(context)
                .load(uri)
                .thumbnail(0.5f)
                .into(view);
    }

    public static void loadCenterCrop(Context context, String url, ImageView view, int defaultResId){
        Glide.with(context).load(url).centerCrop().dontAnimate().placeholder(defaultResId).into(view);
    }

    public static void loadUrlWithZoom(Context context, Uri uri, ImageView view){
        Glide.with(context)
                .load(uri)
                .thumbnail(0.5f)
                .centerCrop()
                .into(view);
    }

    private static int getDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.mipmap.img_two_bi_one;
            case 2:
                return R.mipmap.img_four_bi_three;
            case 3:
                return R.mipmap.img_one_bi_one;
            case 4:
                return R.mipmap.img_default_movie;
        }
        return R.mipmap.img_four_bi_three;
    }

    public static void loadMovieLatestImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .override(125, 165)
                .placeholder(getDefaultPic(4))
                .error(getDefaultPic(4))
                .into(imageView);
    }


    public static void clear(Context context) {
        Glide.get(context).clearMemory();
    }
}
