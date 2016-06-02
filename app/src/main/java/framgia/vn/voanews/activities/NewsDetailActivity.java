package framgia.vn.voanews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.itextpdf.text.DocumentException;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import framgia.vn.voanews.R;
import framgia.vn.voanews.data.model.News;
import framgia.vn.voanews.data.service.NewsRepository;
import framgia.vn.voanews.utils.CreatePdfUtil;
import framgia.vn.voanews.utils.TimeUtils;
import io.realm.Realm;

/**
 * Created by nghicv on 31/05/2016.
 */
public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageViewNews;
    private TextView mTextViewTitle;
    private TextView mTextViewDate;
    private ImageButton mButtonPrintPdf;
    private ImageButton mButtonShare;
    private TextView mTextViewSubContent;
    private Toolbar mToolbar;
    private News mNews;
    private Realm mRealm;
    private NewsRepository mNewsRepository;
    private String mTitle;
    private String mCategory;
    private CreatePdfUtil mCreatePdfUtil = new CreatePdfUtil();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initData();
        initViews();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImageViewNews = (ImageView) findViewById(R.id.iv_news_details);
        mTextViewTitle = (TextView) findViewById(R.id.tv_title_details);
        mTextViewDate = (TextView) findViewById(R.id.tv_date_details);
        mButtonPrintPdf = (ImageButton) findViewById(R.id.ib_print_pdf_details);
        mButtonShare = (ImageButton) findViewById(R.id.ib_shares_details);
        mTextViewSubContent = (TextView) findViewById(R.id.tv_short_content_details);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Picasso.with(this)
                .load(mNews.getEnclosure())
                .into(mImageViewNews);
        mTextViewTitle.setText(mTitle);
        mTextViewDate.setText(TimeUtils.toStringDate(mNews.getDate()));
        mTextViewSubContent.setText(mNews.getDescription());
        mButtonPrintPdf.setOnClickListener(this);
    }

    private void initData() {
        mRealm = Realm.getDefaultInstance();
        mNewsRepository = new NewsRepository(mRealm);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(NewsRepository.TITLE_FIELD);
        mCategory = intent.getStringExtra(NewsRepository.CATEGORY_FIELD);
        mNews = mNewsRepository.getNewsByTitle(mTitle, mCategory);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    public void print() {
        try {
            mCreatePdfUtil.printerPdf(mNews.getTitle(), String.valueOf(mNews.getDate())
                    , mNews.getEnclosure(), mNews.getDescription());
            mCreatePdfUtil.viewPdf(getApplication());
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ib_print_pdf_details:
                print();
            case R.id.ib_shares_details:
        }
    }
}
