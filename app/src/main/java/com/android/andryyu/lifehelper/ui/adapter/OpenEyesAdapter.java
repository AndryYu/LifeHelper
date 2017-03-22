package com.android.andryyu.lifehelper.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseViewHolder;
import com.android.andryyu.lifehelper.data.entity.HomePicEntity;
import com.android.andryyu.lifehelper.ui.act.VideoDetailActivity;
import com.android.andryyu.lifehelper.utils.AnimRecyclerViewAdapter;
import com.android.andryyu.lifehelper.utils.ImageUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yufei on 2017/3/21.
 */

public class OpenEyesAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    public static final int VIDEO = 1;
    public static final int TEXT = 2;


    private Context mContext;

    List<HomePicEntity.IssueListEntity.ItemListEntity> mItemList;

    public OpenEyesAdapter(List<HomePicEntity.IssueListEntity.ItemListEntity> lists) {
        mItemList = lists;
    }

    @Override
    public int getItemViewType(int position) {
        HomePicEntity.IssueListEntity.ItemListEntity itemListEntity = mItemList.get(position);
        if ("video".equals(itemListEntity.getType())) {
            return VIDEO;
        }
        if("banner2".equals(itemListEntity.getType()))
            return TEXT;
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case VIDEO:
                return new OpenEyesVedioHolder(mContext,
                        LayoutInflater.from(mContext).inflate(R.layout.item_openeyes_vedio, parent, false));
            case TEXT:
                return new OpenEyesTextHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_openeyes_text, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        HomePicEntity.IssueListEntity.ItemListEntity itemListEntity = mItemList.get(position);
        switch (itemType) {
            case VIDEO:
                ((OpenEyesVedioHolder) holder).bind(itemListEntity);
                break;
            case TEXT:
                ((OpenEyesTextHolder) holder).bind(itemListEntity);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


        class OpenEyesVedioHolder extends BaseViewHolder<HomePicEntity.IssueListEntity.ItemListEntity> {

        @BindView(R.id.iv)
        ImageView mIv;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.rl_text)
        RelativeLayout mRlText;
        @BindView(R.id.ll_moban)
        LinearLayout mLlMoban;

        private Context mContext;
        public OpenEyesVedioHolder(Context context,View itemView) {
            super(itemView);
            this.mContext = context;
        }

        protected void bind(HomePicEntity.IssueListEntity.ItemListEntity itemListEntity) {
            String feed = "1";
            String title = "1";
            String category = "1";
            int duration = 0;
            String text = "1";

            //得到不同类型所需要的数据
            feed = itemListEntity.getData().getCover().getFeed();
            title = itemListEntity.getData().getTitle();
            category = itemListEntity.getData().getCategory();
            category = "#" + category + "  /  ";
            duration = itemListEntity.getData().getDuration();

            int last = duration % 60;
            String stringLast;
            if (last <= 9) {
                stringLast = "0" + last;
            } else {
                stringLast = last + "";
            }

            String durationString;
            int minit = duration / 60;
            if (minit < 10) {
                durationString = "0" + minit;
            } else {
                durationString = "" + minit;
            }
            String stringTime = durationString + "' " + stringLast + '"';

            Uri uri = Uri.parse(feed);
            ImageUtil.loadUrl(mContext, uri, mIv);
            mTvTitle.setText(title);
            mTvTime.setText(category + stringTime);
            mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, VideoDetailActivity.class);
                    Bundle bundle = new Bundle();
                    HomePicEntity.IssueListEntity.ItemListEntity.DataEntity data = itemListEntity.getData();

                    bundle.putString("title", data.getTitle());
                    //获取到时间
                    int duration = data.getDuration();
                    int mm = duration / 60;//分
                    int ss = duration % 60;//秒
                    String second = "";//秒
                    String minute = "";//分
                    if (ss < 10) {
                        second = "0" + String.valueOf(ss);
                    } else {
                        second = String.valueOf(ss);
                    }
                    if (mm < 10) {
                        minute = "0" + String.valueOf(mm);
                    } else {
                        minute = String.valueOf(mm);//分钟
                    }
                    bundle.putString("time", "#" + data.getCategory() + " / " + minute + "'" + second + '"');
                    bundle.putString("desc", data.getDescription());//视频描述
                    bundle.putString("blurred", data.getCover().getBlurred());//模糊图片地址
                    bundle.putString("feed", data.getCover().getFeed());//图片地址
                    bundle.putString("video", data.getPlayUrl());//视频播放地址
                    bundle.putInt("collect", data.getConsumption().getCollectionCount());//收藏量
                    bundle.putInt("share", data.getConsumption().getShareCount());//分享量
                    bundle.putInt("reply", data.getConsumption().getReplyCount());//回复数量
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }


    class OpenEyesTextHolder extends BaseViewHolder<HomePicEntity.IssueListEntity.ItemListEntity> {

        @BindView(R.id.tv_home_text)
        TextView mTvHomeText;

        public OpenEyesTextHolder(View itemView) {
            super(itemView);
        }

        protected void bind(HomePicEntity.IssueListEntity.ItemListEntity itemListEntity) {
           String  text = itemListEntity.getData().getText();
            mTvHomeText.setText(text);

            String image = itemListEntity.getData().getImage();

            if (!TextUtils.isEmpty(image)) {
                mTvHomeText.setTextSize(20);
                mTvHomeText.setText("-Weekend  special-");
            }
        }
    }
}
