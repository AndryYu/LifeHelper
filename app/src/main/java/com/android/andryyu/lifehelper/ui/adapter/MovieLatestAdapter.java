package com.android.andryyu.lifehelper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseViewHolder;
import com.android.andryyu.lifehelper.entity.Weather;
import com.android.andryyu.lifehelper.entity.douban.HotMovieBean;
import com.android.andryyu.lifehelper.entity.zhihu.BaseEntity;
import com.android.andryyu.lifehelper.entity.zhihu.Story;
import com.android.andryyu.lifehelper.utils.AnimRecyclerViewAdapter;
import com.android.andryyu.lifehelper.utils.ImageUtil;
import com.android.andryyu.lifehelper.utils.StringFormatUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/10/30.
 */

public class MovieLatestAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private List<HotMovieBean.SubjectsBean> mData;
    private static final int ITEM_MOVIE = 0;
    private Context mContext;

    public MovieLatestAdapter(List<HotMovieBean.SubjectsBean> data) {
        //super(R.layout.item_movie_latest,data);
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_MOVIE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_movie_latest, parent, false));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HotMovieBean.SubjectsBean entity = mData.get(position);
        ((MovieLatestAdapter.ViewHolder) holder).bind((HotMovieBean.SubjectsBean) entity);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(HotMovieBean.SubjectsBean positionData, View view);
    }


    static class ViewHolder  extends BaseViewHolder<HotMovieBean.SubjectsBean>{
        @BindView(R.id.iv_one_photo)
        ImageView ivOnePhoto;
        @BindView(R.id.tv_one_title)
        TextView tvOneTitle;
        @BindView(R.id.tv_one_genres)
        TextView tvOneGenres;
        @BindView(R.id.tv_one_directors)
        TextView tvOneDirectors;
        @BindView(R.id.tv_one_casts)
        TextView tvOneCasts;
        @BindView(R.id.tv_collect_count)
        TextView tvCollectCount;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.ll_one_item)
        LinearLayout llOneItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(HotMovieBean.SubjectsBean bean) {
            tvOneTitle.setText(bean.getTitle());
            tvOneDirectors.setText(StringFormatUtil.formatLatestName(bean.getDirectors()));
            tvOneCasts.setText(StringFormatUtil.formatLatestCastsName(bean.getCasts()));
            tvOneGenres.setText("评分：" + bean.getRating().getAverage());
            tvCollectCount.setText(bean.getCollect_count()+"人看过");
            ImageUtil.loadMovieLatestImg(ivOnePhoto,bean.getImages().getLarge());
        }
    }
}
