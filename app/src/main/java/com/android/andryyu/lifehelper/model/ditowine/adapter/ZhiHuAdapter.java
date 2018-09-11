package com.android.andryyu.lifehelper.model.ditowine.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseViewHolder;
import com.android.andryyu.lifehelper.entity.zhihu.BaseEntity;
import com.android.andryyu.lifehelper.entity.zhihu.Story;
import com.android.andryyu.lifehelper.model.ditowine.ui.ZhiHuStoryActivity;
import com.android.andryyu.lifehelper.utils.AnimRecyclerViewAdapter;
import com.android.andryyu.lifehelper.utils.ImageUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yufei on 2017/3/21.
 */

public class ZhiHuAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {

    List<BaseEntity> mList;
    private static final int ITEM_STORY = 0;


    private Context mContext;

    public ZhiHuAdapter(List<BaseEntity> data) {
        mList = data;
    }


    @Override
    public int getItemViewType(int position) {
        return ITEM_STORY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ZhiHuHolder(mContext,
                LayoutInflater.from(mContext).inflate(R.layout.item_zhihu_story, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        BaseEntity entity = mList.get(position);
        ((ZhiHuHolder) holder).bind((Story) entity);
    }

    @Override
    public int getItemCount() {
            return mList.size();
    }


    class ZhiHuHolder extends BaseViewHolder<BaseEntity> {

        @BindView(R.id.item_iv_pic)
        ImageView mItemIvPic;
        @BindView(R.id.item_tv_title)
        TextView mItemTvTitle;
        @BindView(R.id.layout_item_zhihu)
        RelativeLayout mLayoutItemZhihu;
        private Context mContext;

        public ZhiHuHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
        }

        public void bind(Story story) {
            List<String> url = story.getImages();
            String title = story.getTitle();

            ImageUtil.loadUrl(mContext, Uri.parse(url.get(0)), mItemIvPic);
            mItemTvTitle.setText(title);
            mLayoutItemZhihu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ZhiHuStoryActivity.class);
                    intent.putExtra(ZhiHuStoryActivity.ARG_STORY, story);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
