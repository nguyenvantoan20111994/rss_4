package framgia.vn.voanews.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import framgia.vn.voanews.R;
import framgia.vn.voanews.adapters.NewsAdapter;

/**
 * Created by hoavt on 24/05/2016.
 */
public class HotNewsViewHolder extends RecyclerView.ViewHolder {
    private ImageView mIvHotNews;
    private TextView mTvTitle;
    private TextView mTvTime;
    private int mPosition;
    private NewsAdapter.OnItemClickListener mOnItemClickListener;
    public HotNewsViewHolder(View itemView, NewsAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        mOnItemClickListener = onItemClickListener;
        mIvHotNews = (ImageView) itemView.findViewById(R.id.iv_hot_news);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_hot_title);
        mTvTime = (TextView) itemView.findViewById(R.id.tv_hot_time);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onClick(mPosition);
            }
        });
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

    public void setPosition(int position) {
        mPosition = position;
    }
}
