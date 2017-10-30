package com.android.andryyu.lifehelper.widget.recycleView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

/**
 * Created by yufei on 2017/10/27.
 */

public class XRecyclerView extends RecyclerView {

    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(){
        addOnScrollListener(new ImageAutoLoadScrollListener());
    }

    public class ImageAutoLoadScrollListener extends OnScrollListener{

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState){
                case RecyclerView.SCROLL_STATE_IDLE://空闲状态
                    if (getContext() != null)
                        Glide.with(getContext()).resumeRequests();
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING://当屏幕滚动且用户使用触碰或手指还在屏幕上
                    if (getContext() != null)
                        Glide.with(getContext()).pauseRequests();
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING://由于用户的操作，屏幕产生惯性滑动
                    if (getContext() != null)
                        Glide.with(getContext()).pauseRequests();
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
