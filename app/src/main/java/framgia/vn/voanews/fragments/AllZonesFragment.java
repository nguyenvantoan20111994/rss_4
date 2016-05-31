package framgia.vn.voanews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import framgia.vn.voanews.R;
import framgia.vn.voanews.asyntask.AsyncResponse;
import framgia.vn.voanews.asyntask.ReadRssAsyntask;
import framgia.vn.voanews.data.model.News;
import framgia.vn.voanews.data.service.NewsContract;
import framgia.vn.voanews.data.service.NewsRepository;
import framgia.vn.voanews.utils.CheckConnectionUtil;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by toannguyen201194 on 23/05/2016.
 */
public class AllZonesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String LINK_BUND = "link";
    private static final String TITLE_BUND = "title";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mLinkRss;
    private String mTitleRss;
    private NewsRepository mNewsRepository;
    private Realm mRealm;

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
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.blurGrey);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRealm = Realm.getDefaultInstance();
        mNewsRepository = new NewsRepository(mRealm);
        loadData();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadData() {
        if (CheckConnectionUtil.isInternetOn(getContext()) == true) {
            new ReadRssAsyntask(getActivity(), new AsyncResponse() {
                @Override
                public void processFinish(List<News> output) {
                    if (output != null) {
                        mNewsRepository.insertNews(output, new NewsContract.OnInsertNewsListener() {
                            @Override
                            public void onSuccess() {
                                // TODO: 31/05/2016
                            }
                        });
                    }
                }
            }).execute(mLinkRss, mTitleRss);

        } else {
            Toast.makeText(getActivity(), getString(R.string.connect_network_error), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroyView() {
        mRealm.close();
        super.onDestroyView();

    }

    @Override
    public void onRefresh() {
        loadData();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
