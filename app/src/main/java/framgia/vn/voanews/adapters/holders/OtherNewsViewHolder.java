package framgia.vn.voanews.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import framgia.vn.voanews.R;

/**
 * Created by hoavt on 24/05/2016.
 */
public class OtherNewsViewHolder extends RecyclerView.ViewHolder {
    private ImageView mIvNews;
    private TextView mTvTitle;
    private TextView mTvTime;

    public OtherNewsViewHolder(View itemView) {
        super(itemView);
        mIvNews = (ImageView) itemView.findViewById(R.id.iv_news);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mTvTime = (TextView) itemView.findViewById(R.id.tv_times);
    }

    public ImageView getIvNews() {
        return mIvNews;
    }

    public void setIvHotNews(ImageView ivHotNews) {
        mIvNews = ivHotNews;
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        mTvTitle = tvTitle;
    }

    public TextView getTvTime() {
        return mTvTime;
    }

    public void setTvTime(TextView tvTime) {
        mTvTime = tvTime;
    }
}

