package framgia.vn.voanews.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.IOException;

import framgia.vn.voanews.R;
import framgia.vn.voanews.constant.Constant;
import framgia.vn.voanews.utils.HtmlParser;

/**
 * Created by nghicv on 27/05/2016.
 */
public class SeeMoreNewsActivity extends AppCompatActivity {

    private WebView mWebViewNews;
    private Toolbar mToolbar;
    private String mLink;
    public static final String MINE_TYPE = "text/html";
    public static final String ENCODING = "UTF-8";

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null)
                mWebViewNews.loadData(s, MINE_TYPE, ENCODING);
        }
    }
}
