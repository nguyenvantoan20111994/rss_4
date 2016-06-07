package framgia.vn.voanews.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import framgia.vn.voanews.R;
import framgia.vn.voanews.adapters.holders.HotNewsViewHolder;
import framgia.vn.voanews.adapters.holders.LoadingViewHolder;
import framgia.vn.voanews.adapters.holders.OtherNewsViewHolder;
import framgia.vn.voanews.data.model.News;
import framgia.vn.voanews.utils.TimeUtils;

/**
 * Created by hoavt on 24/05/2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int ITEM_HOT_NEW = 6;
    public final int ITEM_OTHER_NEW = 9;
    public final int ITEM_LOADING = 0;
    // The mNewses to display in your RecyclerView
    private List<News> mNewses;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter(List<News> items) {
        mNewses = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mNewses.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mNewses.get(position) == null)
            return ITEM_LOADING;
        return position == 0 ? ITEM_HOT_NEW : ITEM_OTHER_NEW;
    }

    /**
     * This method creates different RecyclerView.ViewHolder objects based on the item view type.\
     *
     * @param viewGroup ViewGroup container for the item
     * @param viewType  type of view to be inflated
     * @return viewHolder to be inflated
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (mContext == null)
            mContext = viewGroup.getContext();
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ITEM_HOT_NEW:
                View hotNewsView = inflater.inflate(R.layout.item_hot_news, viewGroup, false);
                viewHolder = new HotNewsViewHolder(hotNewsView, mOnItemClickListener);
                break;
            case ITEM_OTHER_NEW:
                View otherNewsView = inflater.inflate(R.layout.item_other_news, viewGroup, false);
                viewHolder = new OtherNewsViewHolder(otherNewsView, mOnItemClickListener);
                break;
            case ITEM_LOADING:
                View view = inflater.inflate(R.layout.item_loading, viewGroup, false);
                viewHolder = new LoadingViewHolder(view);
        }
        return viewHolder;
    }

    /**
     * This method internally calls onBindViewHolder(ViewHolder, int) to update the
     * RecyclerView.ViewHolder contents with the item at the given position
     * and also sets up some private fields to be used by RecyclerView.
     *
     * @param viewHolder The type of RecyclerView.ViewHolder to populate
     * @param position   Item position in the viewgroup.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ITEM_HOT_NEW:
                HotNewsViewHolder hotNewsViewHolder = (HotNewsViewHolder) viewHolder;
                hotNewsViewHolder.setPosition(position);
                configHotNewsHolder(hotNewsViewHolder, position);
                break;
            case ITEM_OTHER_NEW:
                OtherNewsViewHolder otherNewsViewHolder = (OtherNewsViewHolder) viewHolder;
                otherNewsViewHolder.setPosition(position);
                configOtherNewsHolder(otherNewsViewHolder, position);
                break;
        }
    }

    private void configOtherNewsHolder(OtherNewsViewHolder otherNewsViewHolder, int position) {
        News newsItem = mNewses.get(position);
        if (newsItem != null) {
            Picasso.with(mContext)
                    .load(newsItem.getEnclosure())
                    .into(otherNewsViewHolder.getIvNews());
            otherNewsViewHolder.getTvTitle().setText(newsItem.getTitle());
            if (newsItem.isViewed())
                otherNewsViewHolder.getTvTitle().setTextColor(ContextCompat.getColor(mContext, R.color.colorViewedNews));
            else
                otherNewsViewHolder.getTvTitle().setTextColor(ContextCompat.getColor(mContext, R.color.black));
            otherNewsViewHolder.getTvTime().setText(TimeUtils.toStringDate(newsItem.getDate()));
        }
    }

    private void configHotNewsHolder(HotNewsViewHolder hotNewsViewHolder, int position) {
        News newsItem = mNewses.get(position);
        if (newsItem != null) {
            Picasso.with(mContext)
                    .load(newsItem.getEnclosure())
                    .placeholder(R.drawable.ic_breaking_news)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(hotNewsViewHolder.getIvHotNews());
            hotNewsViewHolder.getTvHotTitle().setText(newsItem.getTitle());
            if (newsItem.isViewed())
                hotNewsViewHolder.getTvHotTitle().setTextColor(ContextCompat.getColor(mContext, R.color.colorViewedNews));
            else
                hotNewsViewHolder.getTvHotTitle().setTextColor(ContextCompat.getColor(mContext, R.color.black));
            hotNewsViewHolder.getTvHotTime().setText(TimeUtils.toStringDate(newsItem.getDate()));
        }
    }

    public List<News> getItemsArr() {
        return mNewses;
    }

    public void setItemsArr(List<News> items) {
        mNewses = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
