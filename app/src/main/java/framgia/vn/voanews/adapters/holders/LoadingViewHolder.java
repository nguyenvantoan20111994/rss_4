package framgia.vn.voanews.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import framgia.vn.voanews.R;

/**
 * Created by nghicv on 06/06/2016.
 */
public class LoadingViewHolder extends RecyclerView.ViewHolder {
    private ProgressBar mProgressBarLoading;
    public LoadingViewHolder(View itemView) {
        super(itemView);
        mProgressBarLoading = (ProgressBar) itemView.findViewById(R.id.progress_bar);
    }

    public ProgressBar getProgressBar() {
        return mProgressBarLoading;
    }
}
