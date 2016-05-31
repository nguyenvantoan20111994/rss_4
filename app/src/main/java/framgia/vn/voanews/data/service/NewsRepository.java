package framgia.vn.voanews.data.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import framgia.vn.voanews.data.model.News;
import framgia.vn.voanews.utils.LinkRssUtil;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by nghicv on 24/05/2016.
 */
public class NewsRepository {
    public static final String TITLE_FIELD = "mTitle";
    public static final String CATEGORY_FIELD = "mCategory";
    public static final String DATE_FIELD = "mDate";
    public static final int NUM_OF_DAY_STORE = 10;
    private Realm mRealm;

    public NewsRepository(Realm realm) {
        mRealm = realm;
    }

    public void insertNews(final News news, final NewsContract.OnInsertNewsListener onInsertNewsListenner) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(news);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onInsertNewsListenner != null)
                    onInsertNewsListenner.onSuccess();
            }
        });
    }

    public void insertNews(final List<News> newses, final NewsContract.OnInsertNewsListener onInsertNewsListenner) {
        final List<News> realmNews = new ArrayList<>();
        for (int i = 0; i < newses.size(); i++) {
            if (!isExists(newses.get(i)))
                realmNews.add(newses.get(i));
        }
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(realmNews);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onInsertNewsListenner != null)
                    onInsertNewsListenner.onSuccess();
            }
        });
    }

    public RealmResults<News> getAllNews() {
        return mRealm.where(News.class).findAll();
    }

    public void updateNewsToViewedNews(News news) {
        mRealm.beginTransaction();
        news.setIsViewed(true);
        mRealm.commitTransaction();
    }

    public News getNewsByTitle(String title) {
        return mRealm.where(News.class)
                .equalTo(TITLE_FIELD, title)
                .findFirst();
    }

    public RealmResults<News> getNewsByCategory(String category) {
        return mRealm.where(News.class)
                .equalTo(CATEGORY_FIELD, category)
                .findAll();
    }

    public void clearDB() {
        mRealm.beginTransaction();
        mRealm.delete(News.class);
        mRealm.commitTransaction();
    }

    public boolean isExists(News news) {
        return mRealm.where(News.class).equalTo(TITLE_FIELD, news.getTitle())
                .equalTo(CATEGORY_FIELD, news.getCategory()).count() != 0;

    }

    public void deleteOldData() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -NUM_OF_DAY_STORE);
        Date oldDate = calendar.getTime();
        final RealmResults<News> results = mRealm.where(News.class).lessThan(DATE_FIELD, oldDate).findAll();
        if (results == null)
            return;
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }
}
