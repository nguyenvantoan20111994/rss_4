package framgia.vn.voanews.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.IOException;

import framgia.vn.voanews.R;
import framgia.vn.voanews.constant.Constant;
import framgia.vn.voanews.utils.HtmlParser;

/**
 * Created by nghicv on 27/05/2016.
 */
public class SeeMoreNewsActivity extends AppCompatActivity {

    public static final String MINE_TYPE = "text/html";
    public static final String ENCODING = "UTF-8";
    private WebView mWebViewNews;
    private Toolbar mToolbar;
    private String mLink;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_news);
        Intent intent = getIntent();
        mLink = intent.getStringExtra(Constant.LINK_KEY);
        initViews();
        setupWebView();
        new LoadContentNews().execute(mLink);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebViewNews = (WebView) findViewById(R.id.webview_news);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    private void setupWebView() {
        mWebViewNews.getSettings().setJavaScriptEnabled(true);
        mWebViewNews.setWebChromeClient(new WebChromeClient());
    }

    public class LoadContentNews extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return HtmlParser.parser(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SeeMoreNewsActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Voanews.com");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null)
                mWebViewNews.loadData(s, MINE_TYPE, ENCODING);
            mProgressDialog.dismiss();
        }
    }
}
