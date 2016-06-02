package framgia.vn.voanews.asyntask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import framgia.vn.voanews.data.model.News;
import framgia.vn.voanews.fragments.AllZonesFragment;
import framgia.vn.voanews.utils.XmlParser;

/**
 * Created by nguyen van toan on 5/22/2016.
 */
public class ReadRssAsyntask extends AsyncTask<String, Void, List<News>> {
    private Context mContext;
    private XmlParser mXmlParser = new XmlParser();
    private AsyncResponse mAsyncResponse;
    private ProgressDialog mProgressDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public ReadRssAsyntask(Context context,SwipeRefreshLayout swipeRefreshLayout, AsyncResponse asyncResponse) {
        this.mContext = context;
        this.mAsyncResponse = asyncResponse;
        this.mSwipeRefreshLayout=swipeRefreshLayout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mSwipeRefreshLayout.setRefreshing(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

    }

    @Override
    protected List<News> doInBackground(String... params) {
        List<News> newses = null;
        XmlPullParser myParser;
        try {
            myParser = mXmlParser.downLoadUrl(params[0]);
            newses = mXmlParser.parse(myParser, params[1]);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return newses;
    }

    @Override
    protected void onPostExecute(List<News> result) {
        super.onPostExecute(result);
        mAsyncResponse.processFinish(result);
        mSwipeRefreshLayout.setRefreshing(false);
        mProgressDialog.dismiss();
    }
}
