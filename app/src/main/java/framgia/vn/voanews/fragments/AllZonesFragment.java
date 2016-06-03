package framgia.vn.voanews.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import framgia.vn.voanews.R;
import framgia.vn.voanews.activities.NewsDetailActivity;
import framgia.vn.voanews.adapters.NewsAdapter;
import framgia.vn.voanews.asyntask.AsyncResponse;
import framgia.vn.voanews.asyntask.ReadRssAsyntask;
import framgia.vn.voanews.constant.Constant;
import framgia.vn.voanews.data.model.News;
import framgia.vn.voanews.data.service.NewsContract;
import framgia.vn.voanews.data.service.NewsRepository;
import framgia.vn.voanews.utils.CheckConnectionUtil;
import io.realm.Realm;

/**
 * Created by toannguyen201194 on 23/05/2016.
 */
public class AllZonesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String LINK_BUND = "link";
    private static final String TITLE_BUND = "title";
    private final int PAGE_PER = 20;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewNView;
    private String mLinkRss;
    private String mTitleRss;
    private NewsRepository mNewsRepository;
    private Realm mRealm;
    private NewsAdapter mAdapter;
    private List<News> mNewses = new ArrayList<>();
    private List<News> mShowedNews = new ArrayList<>();
    private boolean mIsLoading;

    public static AllZonesFragment newInstance(String link, String title) {
        AllZonesFragment allZonesFragment = new AllZonesFragment();
        Bundle args = new Bundle();
        args.putString(LINK_BUND, link);
        args.putString(TITLE_BUND, title);
        allZonesFragment.setArguments(args);
        return allZonesFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLinkRss = getArguments().getString(LINK_BUND);
            mTitleRss = getArguments().getString(TITLE_BUND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allzones, container, false);
        initViews(view);
        mRealm = Realm.getDefaultInstance();
        mNewsRepository = new NewsRepository(mRealm);
        loadData();

        return view;
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerViewNView = (RecyclerView) view.findViewById(R.id.rv_news);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.blurGrey);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerViewNView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsAdapter(mShowedNews);
        mRecyclerViewNView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                News news = mShowedNews.get(position);
                mNewsRepository.updateNewsToViewedNews(news);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(NewsRepository.TITLE_FIELD, news.getTitle());
                intent.putExtra(NewsRepository.CATEGORY_FIELD, news.getCategory());
                getActivity().startActivity(intent);
                mAdapter.notifyItemChanged(position);
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        mRecyclerViewNView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (!mIsLoading) {
                        if (totalItemCount <= lastVisibleItem + 1) {
                            mIsLoading = true;
                            loadMore();
                        }
                        
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadData() {
        if (CheckConnectionUtil.isInternetOn(getContext())) {
            new ReadRssAsyntask(getActivity(), new AsyncResponse() {
                @Override
                public void processFinish(List<News> output) {
                    if (output != null) {
                        mNewsRepository.insertNews(output, new NewsContract.OnInsertNewsListener() {
                            @Override
                            public void onSuccess() {
                                mNewses.clear();
                                mShowedNews.clear();
                                mNewses.addAll(mNewsRepository.getNewsByCategory(mTitleRss));
                                mShowedNews.addAll(mNewses.subList(mShowedNews.size(), mShowedNews.size() + PAGE_PER));
                                mAdapter.notifyDataSetChanged();
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }
            }).execute(mLinkRss, mTitleRss);

        } else {
            mNewses.clear();
            mShowedNews.clear();
            mNewses.addAll(mNewsRepository.getNewsByCategory(mTitleRss));
            mShowedNews.addAll(mNewses.subList(mShowedNews.size(), mShowedNews.size() + PAGE_PER));
            mAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), getString(R.string.connect_network_error), Toast.LENGTH_LONG).show();
        }

    }

    private void loadMore() {
        if(mShowedNews.size() == mNewses.size()) {
            mIsLoading = false;
            return;
        }
        mShowedNews.add(null);
        mAdapter.notifyItemChanged(mShowedNews.size() - 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mShowedNews.remove(mShowedNews.size() - 1);
                mAdapter.notifyItemRemoved(mShowedNews.size() - 1);
                addShowedNews();
                mAdapter.notifyDataSetChanged();
                mIsLoading = false;
            }
        }, Constant.TIME_DELAY);
    }

    private void addShowedNews() {
        int start = mShowedNews.size();
        if(mShowedNews.size() + PAGE_PER > mNewses.size())
            mShowedNews.addAll(mNewses.subList(start, mNewses.size()));
        else
            mShowedNews.addAll(mNewses.subList(start, start + PAGE_PER));
    }

    @Override
    public void onDestroyView() {
        mRealm.close();
        super.onDestroyView();

    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
