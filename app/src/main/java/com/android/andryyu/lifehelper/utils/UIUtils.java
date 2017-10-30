package com.android.andryyu.lifehelper.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.andryyu.lifehelper.entity.news.NewsInfo;

/**
 * Created by Quinn on 7/24/15.
 */
public class UIUtils {

    // 新闻列表头部
    private static final int HAS_HEAD = 1;
    private static final String NEWS_ITEM_PHOTO_SET = "photoset";
    private static final String NEWS_ITEM_SPECIAL = "special";

    public static void crossfade(final View currView, final View nextView) {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        if (nextView != null) {
            nextView.setAlpha(0f);
            nextView.setVisibility(View.VISIBLE);
            nextView.animate().alpha(1f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    nextView.setVisibility(View.VISIBLE);
                }
            });
        }

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        if (currView != null) {
            currView.setVisibility(View.GONE);
            currView.animate().alpha(0f).setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            currView.setVisibility(View.GONE);
                        }
                    });
        }
    }

    /**
     * 关闭输入法
     * @param context
     */
    public static void closeInputMethod(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(((Activity) context)
                        .getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static int getColorWrap(Context context, int colorRsid) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.getColor(context, colorRsid);
        }else{
            return context.getResources().getColor(colorRsid);
        }
    }

    /**
     * 判断是否为广告
     *
     * @param newsBean
     * @return
     */
    public static boolean isAbNews(@NonNull NewsInfo newsBean) {
        return (newsBean.getHasHead() == HAS_HEAD &&
                newsBean.getAds() != null && newsBean.getAds().size() > 1);
    }

    public static boolean isNewsPhotoSet(String skipType) {
        return NEWS_ITEM_PHOTO_SET.equals(skipType);
    }

    /**
     * 判断新闻类型
     *
     * @param skipType
     * @return
     */
    public static boolean isNewsSpecial(String skipType) {
        return NEWS_ITEM_SPECIAL.equals(skipType);
    }
}
