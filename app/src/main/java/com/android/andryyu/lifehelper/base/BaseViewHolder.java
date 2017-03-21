package com.android.andryyu.lifehelper.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/3/13.
 */

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {


    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
