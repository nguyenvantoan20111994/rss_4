package framgia.vn.voanews.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import framgia.vn.voanews.R;

/**
 * Created by hoavt on 24/05/2016.
 */
public class HotNewsViewHolder extends RecyclerView.ViewHolder {
    private ImageView mIvHotNews;
    private TextView mTvTitle;
    private TextView mTvTime;

    public HotNewsViewHolder(View itemView) {
        super(itemView);
        mIvHotNews = (ImageView) itemView.findViewById(R.id.iv_hot_news);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_hot_title);
        mTvTime = (TextView) itemView.findViewById(R.id.tv_hot_time);
    }

    public ImageView getIvHotNews() {
        return mIvHotNews;
    }

    public void setIvHotNews(ImageView ivHotNews) {
        mIvHotNews = ivHotNews;
    }

    public TextView getTvHotTitle() {
        return mTvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        mTvTitle = tvTitle;
    }

    public TextView getTvHotTime() {
        return mTvTime;
    }

    public void setTvTime(TextView tvTime) {
        mTvTime = tvTime;
    }
}
